package com.lt.study.archive.service;

import com.lt.study.archive.pojo.ArchiveTask;

import java.util.List;
import java.util.Map;

public interface ArchiveTaskService {
    ArchiveTask selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(ArchiveTask record);

    int updateByPrimaryKeySelective (ArchiveTask record);

    Integer insert(ArchiveTask record);

    Integer insertSelective(ArchiveTask record);

    public List<ArchiveTask> selectEntryByMap(Map<String, Object> conditions);

    public  Boolean getIsEnable(Integer id);

    public void setIsEnable(Integer id,Boolean isEnable);
}