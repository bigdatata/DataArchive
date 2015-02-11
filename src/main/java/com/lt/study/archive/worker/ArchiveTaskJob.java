package com.lt.study.archive.worker;

import com.lt.study.archive.pojo.ArchiveTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: luotao
 * Date: 13-12-26
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveTaskJob implements  Job{

    private static final Logger logger = LoggerFactory.getLogger(ArchiveTaskJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ArchiveTask archiveTask= (ArchiveTask) jobExecutionContext.getJobDetail().getJobDataMap().get("archiveTask");
        ArchiveTaskExecutor.addOneArchiveTask(archiveTask);
        logger.info("addOneArchiveTask success!");
    }
}
