package com.lt.study.archive.controller;


import com.alibaba.fastjson.JSON;
import com.lt.study.archive.DynamicDataSource;
import com.lt.study.archive.constant.ArchiveType;
import com.lt.study.archive.pojo.ArchiveTask;
import com.lt.study.archive.service.ArchiveService;
import com.lt.study.archive.service.ArchiveTaskService;
import com.lt.study.archive.worker.ArchiveTaskExecutor;
import com.lt.study.archive.worker.ArchiveTaskQuartzSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("archive")
@Controller
public class ArchiveTaskController {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveTaskController.class);

    @Autowired
    protected ArchiveTaskService archiveTaskService;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    ArchiveTaskQuartzSchedule archiveTaskQuartzSchedule;

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.GET)
    public String addOrUpdate(Integer id,HttpServletResponse response, ModelMap model) {
        if(null!=id){
            ArchiveTask task = archiveTaskService.selectByPrimaryKey(id);
            logger.info(getUser()+" :start to edit task:"+task);
            model.put("entity",task);
        }
        model.put("archiveTypeInfo", ArchiveType.values());
        model.put("dataSourceInfo", DynamicDataSource.getDataSourcesKey());
        return "addOrUpdate";
    }

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public String doAddOrUpdate(ArchiveTask archiveTask,HttpServletResponse response, ModelMap model) {
        if (null==archiveTask.getId()){
            archiveTask.setIsDeleted(false);
            archiveTask.setIsEnable(false);
            Integer id=archiveTaskService.insert(archiveTask);
            //添加定时任务
            archiveTask=archiveTaskService.selectByPrimaryKey(id);
            archiveTaskQuartzSchedule.addArchiveJob(archiveTask);
            logger.info(getUser()+" :add a task:"+archiveTask);
        } else {
            archiveTaskService.updateByPrimaryKeySelective(archiveTask);
            //修改定时任务
            archiveTask=archiveTaskService.selectByPrimaryKey(archiveTask.getId());
            logger.info(getUser()+" :update task to :"+archiveTask);
            archiveTaskQuartzSchedule.updateArchiveJob(archiveTask);
        }
        return "redirect:/archive/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletResponse response, ModelMap model) {
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("isDeleted", 0);
        List<ArchiveTask> archiveTaskList = archiveTaskService.selectEntryByMap(conditions);
        model.put("archiveTaskList", archiveTaskList);
        return "list";
    }

    /***
     * 允许执行归档，允许后才能执行归档任务
     */
    @RequestMapping("enable")
    public String enable(Integer id,HttpServletResponse response, ModelMap model){
        setArchiveEnable(true,id);
        logger.info(getUser()+" :enable task id is :"+id);
        return "redirect:/archive/list";
    }

    @RequestMapping("disEnable")
    public String disEnable(Integer id,HttpServletResponse response, ModelMap model){
        setArchiveEnable(false,id);
        logger.info(getUser()+" :disEnable task id is :"+id);
        return "redirect:/archive/list";
    }

    /**
     * 手动开启一个归档任务
     * @param id
     * @return
     */
    @RequestMapping("start")
    public String start(Integer id) {
        ArchiveTask task = archiveTaskService.selectByPrimaryKey(id);
        ArchiveTaskExecutor.addOneArchiveTask(task);
        logger.info(getUser()+" :start task id is :"+id);
        return "redirect:/archive/list";
    }

    /**
     * 删除一个归档任务
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String delete(Integer id) {
        ArchiveTask task = new ArchiveTask();
        task.setIsDeleted(Boolean.TRUE);
        task.setId(id);
        archiveTaskService.updateByPrimaryKeySelective(task);
        //删除定时任务
        task=archiveTaskService.selectByPrimaryKey(id);
        archiveTaskQuartzSchedule.deleteArchiveJob(task);
        logger.info(getUser()+" :delete task id is :"+id);
        return "redirect:/archive/list";
    }

    /**
     * 获取一个数据源的所有表，可能需要权限
     * @param dataSource
     * @param request
     * @param response
     */
    @RequestMapping("getTable")
    public void getTable(String dataSource,HttpServletRequest request,HttpServletResponse response) {
        if (null==dataSource){
            return;
        }
        List<String> tableList=archiveService.showTable(dataSource);
        write(response,JSON.toJSONString(tableList));
    }


    /**
     * 测试选择
     * @param id
     */
    @RequestMapping("select")
    public void testSelect(Integer id) {
        ArchiveTask task = archiveTaskService.selectByPrimaryKey(id);
        List list = archiveService.select(task,-1L);
        System.out.println(list.size());
    }

    /**
     * 测试选择插入
     * @param id
     */
    @RequestMapping("selectInsert")
    public void testSelectInsert(Integer id){
        long start=System.currentTimeMillis();
        ArchiveTask task = archiveTaskService.selectByPrimaryKey(id);
        logger.info("archiveTaskService.selectByPrimaryKey cost:"+(System.currentTimeMillis()-start));
        start=System.currentTimeMillis();
        List list = archiveService.select(task,-1L);
        logger.info("archiveService.select cost:"+(System.currentTimeMillis()-start));
        start=System.currentTimeMillis();
        archiveService.insert(task,list);
        logger.info("archiveService.insert cost:"+(System.currentTimeMillis()-start));
    }

    /**
     * 获取归档任务Key值上界
     * @param id
     */
    @RequestMapping("getKeyTop")
    public void getKeyTop(Integer id){
        long start=System.currentTimeMillis();
        ArchiveTask task = archiveTaskService.selectByPrimaryKey(id);
        logger.info("archiveTaskService.selectByPrimaryKey cost:"+(System.currentTimeMillis()-start));
        start=System.currentTimeMillis();
        System.out.println(archiveService.selectKeyTop(task));
        logger.info("archiveService.selectKeyTop cost:"+(System.currentTimeMillis()-start));
    }

    /**
     * 获取归档任务 需要归档的记录条数
     * @param id
     */
    @RequestMapping("getCount")
    public void getCount(Integer id){
        long start=System.currentTimeMillis();
        ArchiveTask task = archiveTaskService.selectByPrimaryKey(id);
        logger.info("archiveTaskService.selectByPrimaryKey cost:"+(System.currentTimeMillis()-start));
        start=System.currentTimeMillis();
        System.out.println(archiveService.count(task));
        logger.info("archiveService.count cost:"+(System.currentTimeMillis()-start));
    }

    private void setArchiveEnable(boolean isEnable,Integer archiveId){
        if (archiveId==null){
            return;
        }
        ArchiveTask archiveTask = new ArchiveTask();
        archiveTask.setId(archiveId);
        archiveTask.setIsEnable(isEnable);
        archiveTaskService.updateByPrimaryKeySelective(archiveTask);
        //更新缓存
        archiveTaskService.setIsEnable(archiveId, isEnable);
    }


    private void write(HttpServletResponse response, Object obj) {
        try {
            response.getWriter().print(obj);
        } catch (IOException e) {
            logger.error("write error" + e);
        }
    }

    private String getUser(){
        return "need todo";
    }
}
