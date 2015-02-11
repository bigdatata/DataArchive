package com.lt.study.archive.dao;

import java.util.List;
import java.util.Map;

public interface ArchiveDao {
    
    Integer delete(Map<String,Object> infoMap);
    
    Integer insert(Map<String,Object> infoMap);
    
    List select(Map<String,Object> infoMap);

    /***
     * 获取归档key的上界
     * @param infoMap
     * @return
     */
    Long selectKeyTop(Map<String, Object> infoMap);

    /***
     * 查询满足条件的记录条数
     * @param infoMap
     * @return
     */
    Long count(Map<String, Object> infoMap);

    /**
     * 查看数据源的所有表
     * @return
     */
    List<String> showTable();
}