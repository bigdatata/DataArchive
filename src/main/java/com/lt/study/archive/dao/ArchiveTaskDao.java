package com.lt.study.archive.dao;


import com.lt.study.archive.pojo.ArchiveTask;

import java.util.List;
import java.util.Map;

public interface ArchiveTaskDao {
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Integer insert(ArchiveTask record);

    /**
     * 保存属性不为空的记录
     */
    Integer insertSelective(ArchiveTask record);

    /**
     * 根据主键查询记录
     */
    ArchiveTask selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(ArchiveTask record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ArchiveTask record);

    List<ArchiveTask> selectEntryByMap(Map<String,Object> conditions);
}