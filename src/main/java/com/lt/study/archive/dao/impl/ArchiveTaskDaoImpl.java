package com.lt.study.archive.dao.impl;


import com.ibatis.sqlmap.client.SqlMapClient;
import com.lt.study.archive.dao.ArchiveTaskDao;
import com.lt.study.archive.pojo.ArchiveTask;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("archiveTaskDaoImpl")
public class ArchiveTaskDaoImpl extends SqlMapClientTemplate implements ArchiveTaskDao {

    public ArchiveTaskDaoImpl() {
        super();
    }

    @Resource
    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    public int deleteByPrimaryKey(Integer id) {
        ArchiveTask _key = new ArchiveTask();
        _key.setId(id);
        int rows = this.delete("archive_task.deleteByPrimaryKey", _key);
        return rows;
    }

    public Integer insert(ArchiveTask record) {
        Object newKey = this.insert("archive_task.insert", record);
        return (Integer) newKey;
    }

    public Integer insertSelective(ArchiveTask record) {
        Object newKey = this.insert("archive_task.insertSelective", record);
        return (Integer) newKey;
    }

    public ArchiveTask selectByPrimaryKey(Integer id) {
        ArchiveTask _key = new ArchiveTask();
        _key.setId(id);
        ArchiveTask record = (ArchiveTask) this.queryForObject("archive_task.selectByPrimaryKey", _key);
        return record;
    }

    public int updateByPrimaryKeySelective(ArchiveTask record) {
        int rows = this.update("archive_task.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ArchiveTask record) {
        int rows = this.update("archive_task.updateByPrimaryKey", record);
        return rows;
    }

    @Override
    public List<ArchiveTask> selectEntryByMap(Map<String, Object> conditions) {
        return this.queryForList("archive_task.selectEntryByMap",conditions);
    }
}