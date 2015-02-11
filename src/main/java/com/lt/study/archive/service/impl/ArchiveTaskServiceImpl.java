package com.lt.study.archive.service.impl;

import com.lt.study.archive.dao.ArchiveTaskDao;
import com.lt.study.archive.pojo.ArchiveTask;
import com.lt.study.archive.service.ArchiveTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("archiveTaskService")
public class ArchiveTaskServiceImpl implements ArchiveTaskService {
    @Autowired
    private ArchiveTaskDao archiveTaskDao;

    public static Map<Integer,Boolean> idToArchiveTaskIsEnable=new HashMap<Integer, Boolean>();

    @PostConstruct
    public void init(){
        loadCache();
    }
    private static final Logger logger = LoggerFactory.getLogger(ArchiveTaskServiceImpl.class);

    public ArchiveTask selectByPrimaryKey(Integer id) {
        return this.archiveTaskDao.selectByPrimaryKey(id);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.archiveTaskDao.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKey(ArchiveTask record) {
        return this.archiveTaskDao.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(ArchiveTask record){
        return this.archiveTaskDao.updateByPrimaryKeySelective(record);
    }

    public Integer insert(ArchiveTask record) {
        return this.archiveTaskDao.insert(record);
    }

    public Integer insertSelective(ArchiveTask record) {
        return this.archiveTaskDao.insertSelective(record);
    }

    @Override
    public List<ArchiveTask> selectEntryByMap(Map<String, Object> conditions) {
        return this.archiveTaskDao.selectEntryByMap(conditions);
    }

    private void loadCache(){
        Map<String,Object> conditions=new HashMap<String,Object>();
        conditions.put("isDeleted",0);
        List<ArchiveTask> archiveTaskList = this.selectEntryByMap(conditions);
        if (null==archiveTaskList){
            logger.info("archiveTaskList is null,no archive task is enable");
            return;
        }
        for (ArchiveTask archiveTask:archiveTaskList){
            idToArchiveTaskIsEnable.put(archiveTask.getId(),archiveTask.getIsEnable());
        }
    }

    public Boolean getIsEnable(Integer id){
        return idToArchiveTaskIsEnable.get(id)==null?false:idToArchiveTaskIsEnable.get(id);
    }

    public void setIsEnable(Integer id,Boolean isEnable) {
        idToArchiveTaskIsEnable.put(id,isEnable);
    }

}