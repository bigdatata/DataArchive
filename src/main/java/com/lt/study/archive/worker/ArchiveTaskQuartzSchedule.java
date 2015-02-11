package com.lt.study.archive.worker;

import com.lt.study.archive.pojo.ArchiveTask;
import com.lt.study.archive.service.ArchiveTaskService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: luotao
 * Date: 13-12-26
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
@Repository("archiveTaskQuartzSchedule")
public class ArchiveTaskQuartzSchedule {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveTaskQuartzSchedule.class);

    @Autowired
    protected ArchiveTaskService archiveTaskService;

    private static Scheduler scheduler = getScheduler();

    /**
     * 创建一个调度对象
     *
     * @return
     * @throws SchedulerException
     */
    private static Scheduler getScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }

    @PostConstruct
    public void init() {
        //启动Quartz
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("quartzSchedule.start error:" + e);
        }

        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("isDeleted", 0);
        List<ArchiveTask> archiveTaskList = archiveTaskService.selectEntryByMap(conditions);
        if (null == archiveTaskList) {
            logger.info("archiveTaskList is null,no archive task");
            return;
        }
        for (ArchiveTask archiveTask : archiveTaskList) {
            addArchiveJob(archiveTask);
        }
    }

    /***
     * 添加一个归档Quartz任务
     * @param archiveTask
     */
    public void addArchiveJob(ArchiveTask archiveTask) {
        String indentity = "indentity" + archiveTask.getId();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(indentity, Scheduler.DEFAULT_GROUP)
                .withSchedule(cronSchedule(archiveTask.getCronExpression()))
                .build();
        JobDetail jobDetail = JobBuilder.newJob(ArchiveTaskJob.class)
                .withIdentity(indentity, Scheduler.DEFAULT_GROUP)
                .build();
        jobDetail.getJobDataMap().put("archiveTask", archiveTask);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("scheduler.scheduleJob error:" + archiveTask + e);
        }
    }

    /***
     * 更新一个归档Quartz任务
     * @param archiveTask
     */
    public void updateArchiveJob(ArchiveTask archiveTask) {
        String indentity = "indentity" + archiveTask.getId();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(indentity, Scheduler.DEFAULT_GROUP)
                .withSchedule(cronSchedule(archiveTask.getCronExpression()))
                .build();
        try {
            //更新 归档任务 传给worker的数据更新
            scheduler.getJobDetail(new JobKey(indentity)).getJobDataMap().put("archiveTask", archiveTask);
        } catch (SchedulerException e) {
            logger.info("scheduler.getJobDetail error:" + archiveTask + e);
        }
        try {
            //更新归档触发时间
            scheduler.rescheduleJob(new TriggerKey(indentity), trigger);
        } catch (SchedulerException e) {
            logger.info("scheduler.rescheduleJob error:" + archiveTask + e);
        }
    }

    /***
     * 删除一个归档Quartz任务
     * @param archiveTask
     */
    public void deleteArchiveJob(ArchiveTask archiveTask) {
        String indentity = "indentity" + archiveTask.getId();
        try {
            scheduler.deleteJob(new JobKey(indentity));
        } catch (SchedulerException e) {
            logger.info("scheduler.deleteJob error:" + archiveTask + e);
        }
    }
}
