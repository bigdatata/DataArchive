package com.lt.study.archive.worker;

import com.lt.study.archive.constant.ArchiveType;
import com.lt.study.archive.pojo.ArchiveTask;
import com.lt.study.archive.service.ArchiveService;
import com.lt.study.archive.service.ArchiveTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: luotao
 * Date: 13-12-26
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
@Repository("doArchiveTaskWorker")
public class DoArchiveTaskWorker implements Runnable {
    ArchiveTask archiveTask;

    @Autowired
    private static ArchiveTaskService archiveTaskService;

    @Autowired
    private static ArchiveService archiveService;

    private static final Logger logger = LoggerFactory.getLogger(DoArchiveTaskWorker.class);


    private static Integer  PerInsertDeleteQueueSize=200;
    
    private final static Map<Integer,ArchiveTask> runningArchiveTaskMap=new ConcurrentHashMap<Integer, ArchiveTask>();
    /**
     * 线程池
     */
    public static final ThreadPoolExecutor perInsertDeleteExecutor = new ThreadPoolExecutor(5, 20, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(PerInsertDeleteQueueSize), new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Event-Manager-Thread-" + count.getAndIncrement());
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.info("[perInsertOrDeleteExecutor]too many tasks have been received.queueSize:" + executor.getQueue().size());
        }
    });


    @Autowired(required = true)
    public void setArchiveTaskService(ArchiveTaskService archiveTaskService) {
        DoArchiveTaskWorker.archiveTaskService = archiveTaskService;
    }

    @Autowired(required = true)
    public void setArchiveService(ArchiveService archiveService) {
        DoArchiveTaskWorker.archiveService = archiveService;
    }

    public DoArchiveTaskWorker(ArchiveTask archiveTask) {
        this.archiveTask = archiveTask;
    }

    public DoArchiveTaskWorker() {

    }

    @Override
    public void run() {
        //放入正在运行的map中
        synchronized (runningArchiveTaskMap){
            boolean  isThisTaskInRunning=runningArchiveTaskMap.get(archiveTask.getId())!=null;
            if (isThisTaskInRunning){
                logger.info("Task "+archiveTask.getId() +"is running,can't start,return");
                return;
            }
            runningArchiveTaskMap.put(archiveTask.getId(),archiveTask);
        }
        Long start=System.currentTimeMillis();
        logger.info(archiveTask.getId() + "---start");
        //检查是否允许
        boolean isEnable = archiveTaskService.getIsEnable(archiveTask.getId());
        if (!isEnable) {
            logger.info("isEnable is false, return");
        }
        Long archiveTotalCount=archiveService.count(archiveTask);
        logger.info("the number of archive is " + archiveTotalCount);
        Long startBottom = -1L;
        while (isEnable) {
            if (isEnable) {
                //归档 数据
                List list = archiveService.select(archiveTask, startBottom);
                if (list == null || list.size() == 0) {
                    break;
                }
                Map map = (Map) list.get(list.size() - 1);
                Long newStartBottom=-1L;
                try{
                    newStartBottom = Long.valueOf((String.valueOf( map.get(archiveTask.getKeyColumn()))));
                } catch (Exception e){
                    logger.error("set newStartBottom error,check keyColumn can be cast to number"+archiveTask);
                    return;
                }
                ArchiveType archiveType=ArchiveType.getFlagByCode(archiveTask.getArchiveType());
                if (archiveType==ArchiveType.Delete){
                    addOneDeleteTask(archiveTask,startBottom,archiveTask.getPerDeleteNum());
                } else {
                    addOneInsertDeleteTask(archiveTask,list,startBottom);
                }
                startBottom=newStartBottom;
            } else {
                logger.info("isEnable is set to false, break");
                break;
            }
            isEnable = archiveTaskService.getIsEnable(archiveTask.getId());
        }

        logger.info("Task:"+archiveTask.getId()+" Count:"+archiveTotalCount+"　Cost:"+(System.currentTimeMillis()-start));
        //todo
        runningArchiveTaskMap.remove(archiveTask.getId());
        logger.info(archiveTask.getId() + "---finished");
    }

    /**
     * 睡眠以等待下次操作
     *
     * @param sleepTime
     */
    private static void sleepForNextOperation(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            logger.error("Thread wait exception", e);
        }
        logger.info("Thread sleep finished,sleepTime:"+sleepTime );
    }

    /***
     * 加入一个拷贝删除任务
     * @param archiveTask   归档任务
     * @param list       用于插入的数据 list<map>
     * @param startBottom  开始的下界，用于删除
     */
    public synchronized static void addOneInsertDeleteTask(final ArchiveTask archiveTask,final List list ,final long startBottom){
        while (true){
            boolean canAdd=perInsertDeleteExecutor.getPoolSize()<perInsertDeleteExecutor.getMaximumPoolSize();
            if(canAdd){
                perInsertDeleteExecutor.execute(new Thread(){
                    @Override
                    public void run(){
                        try{
                            logger.info("archiveTaskId"+archiveTask.getId()+" start to insert StartBottom"+startBottom);
                            archiveService.insert(archiveTask, list);
                            for (Object item : list) {
                                Map itemMap = (Map) item;
                                itemMap.clear();
                            }
                            list.clear();
                            logger.info("archiveTaskId"+archiveTask.getId()+" insert success StartBottom"+startBottom);
                            ArchiveType archiveType=ArchiveType.getFlagByCode(archiveTask.getArchiveType());
                            //保存删除类型
                            if (archiveType==ArchiveType.Copy_Delete){
                                addOneDeleteTask(archiveTask,startBottom,archiveTask.getPerSaveNum());
                            }
                        }catch (Exception e){
                            logger.info("archiveService.insert archiveTask id:"+archiveTask.getId()+"startBottom"+e);
                        }
                    }
                });
                return;
            }
            sleepForNextOperation(1000);
        }
    }

    /**
     * 添加一个删除任务
     * @param archiveTask 归档任务
     * @param startBottom 删除开始下界
     * @param perCount     每次删除的条数
     */
    public synchronized static void addOneDeleteTask(final ArchiveTask archiveTask,final long startBottom,final Integer perCount){
        while (true){
            boolean canAdd=perInsertDeleteExecutor.getPoolSize()<perInsertDeleteExecutor.getMaximumPoolSize();
            if(canAdd){
                perInsertDeleteExecutor.execute(new Thread(){
                    @Override
                    public void run(){
                        try{
                            logger.info("archiveTaskId"+archiveTask.getId()+" start to delete StartBottom"+startBottom);
                            Integer per=perCount;
                            if (per==null){
                                per=archiveTask.getPerSaveNum();
                            }
                            archiveService.delete(archiveTask, startBottom, per);
                            logger.info("archiveTaskId"+archiveTask.getId()+" finished delete StartBottom"+startBottom);
                        }catch (Exception e){
                            logger.info("archiveService.delete archiveTask id:"+archiveTask.getId()+"startBottom"+e);
                        }
                        
                    }
                });
                return;
            }
            sleepForNextOperation(1000);
        }
    }

}