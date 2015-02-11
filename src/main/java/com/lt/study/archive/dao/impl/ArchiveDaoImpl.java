package com.lt.study.archive.dao.impl;


import com.ibatis.sqlmap.client.SqlMapClient;
import com.lt.study.archive.dao.ArchiveDao;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("archiveDaoImpl")
public class ArchiveDaoImpl extends SqlMapClientTemplate implements ArchiveDao {

    public ArchiveDaoImpl() {
        super();
    }

    @Resource
    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    @Override
    public Integer delete(Map<String, Object> infoMap) {
        return this.delete("archive.delete",infoMap);
    }

    @Override
    public Integer insert(Map<String, Object> infoMap) {
        return (Integer)this.insert("archive.insert",infoMap);
    }

    @Override
    public List select(Map<String, Object> infoMap) {
        try{
            return this.queryForList("archive.select",infoMap);
        }catch (Exception e){
            logger.info("select"+e);
        }
        return null;
    }

    @Override
    public Long selectKeyTop(Map<String, Object> infoMap) {
        try{
            return (Long)this.queryForObject("archive.selectKeyTop",infoMap);
        }catch (Exception e){
            logger.info("selectKeyTop"+e);
        }
        return -1L;
    }

    @Override
    public Long count(Map<String, Object> infoMap) {
        try{
            return (Long)this.queryForObject("archive.count",infoMap);
        }catch (Exception e){
            logger.info("count"+e);
        }
        return -1L;
    }

    public List<String> showTable(){
        try{
            return queryForList("archive.showTable");
        }catch (Exception e){
            logger.info("showTable"+e);
        }
        return new ArrayList<String>();
    }
}