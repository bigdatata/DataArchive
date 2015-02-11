package com.lt.study.archive.worker;

import com.lt.study.archive.pojo.ArchiveTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**

 * User: luotao
 * Date: 13-12-26
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */

@Repository("archiveTaskExecutor")
public class ArchiveTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveTaskExecutor.class);

    private static Integer  ArchiveTaskQueueSize=50;
    /**
     * 线程池
     */
    public static final ThreadPoolExecutor archiveTaskExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(ArchiveTaskQueueSize), new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Event-Manager-Thread-" + count.getAndIncrement());
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.info("[ArchiveTaskExecutor]too many tasks have been received.queueSize:" + executor.getQueue().size());
        }
    });

    public static void addOneArchiveTask(ArchiveTask archiveTask){
        while (true){
            if(archiveTaskExecutor.getQueue().size()<ArchiveTaskQueueSize){
                archiveTaskExecutor.execute(new DoArchiveTaskWorker(archiveTask));
                return;
            }
            sleepForNextOperation(1000);
        }
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
}
