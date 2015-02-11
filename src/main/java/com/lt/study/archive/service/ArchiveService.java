package com.lt.study.archive.service;


import com.lt.study.archive.pojo.ArchiveTask;

import java.util.List;

public interface ArchiveService {
    
    Integer delete(ArchiveTask archiveTask,Long startBottom,Integer deleteCount);


    Integer insert(ArchiveTask archiveTask,List list);
    
    List select(ArchiveTask archiveTask,Long startBottom);

    /***
     * 获取该归档任务的归档key的上线，不包括结果key
     * @param archiveTask
     * @return
     */
    Long selectKeyTop(ArchiveTask archiveTask);

    /**
     * 查询归档任务要归档的记录数
     * @param archiveTask
     * @return
     */
    Long count(ArchiveTask archiveTask);

    List<String> showTable(String dataSource);
}