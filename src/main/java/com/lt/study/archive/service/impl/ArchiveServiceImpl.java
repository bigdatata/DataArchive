package com.lt.study.archive.service.impl;

import com.lt.study.archive.CustomerContextHolder;
import com.lt.study.archive.dao.ArchiveDao;
import com.lt.study.archive.pojo.ArchiveTask;
import com.lt.study.archive.service.ArchiveService;
import com.lt.study.archive.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: luotao
 * Date: 13-12-26
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */

@Service("archiveService")
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveDao archiveDao;

    @Override
    public Integer delete(ArchiveTask archiveTask,Long startBottom, Integer deleteCount) {
        CustomerContextHolder.setCustomerType(archiveTask.getSourceDatabase());
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("sourceTable", archiveTask.getSourceTable());
        StringBuilder where = getWhere(archiveTask,startBottom).append(" limit ").append(deleteCount);
        infoMap.put("where", where.toString());
        return archiveDao.delete(infoMap);
    }

    @Override
    public Integer insert(ArchiveTask archiveTask, List list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        String archiveColumns = archiveTask.getArchiveColumns();
        if (archiveColumns == null) {
            return 0;
        }
        CustomerContextHolder.setCustomerType(archiveTask.getTargetDatabase());
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("targetTable", archiveTask.getTargetTable());
        //处理选择所有列时
        if (archiveColumns.equals("*")) {
            StringBuilder archiveColumnsBuilder = new StringBuilder();
            for (Object object : ((Map) list.get(0)).keySet()) {
                archiveColumnsBuilder.append(object).append(",");
            }
            archiveColumnsBuilder.replace(archiveColumnsBuilder.lastIndexOf(","), archiveColumnsBuilder.length(), "");
            archiveColumns = archiveColumnsBuilder.toString();
        }
        infoMap.put("archiveColumns",archiveColumns);
        String[] columns = archiveColumns.split(",");
        infoMap.put("values", getValue(columns, list));
        return archiveDao.insert(infoMap);
    }

    @Override
    public List select(ArchiveTask archiveTask,Long startBottom) {
        CustomerContextHolder.setCustomerType(archiveTask.getSourceDatabase());
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("sourceTable", archiveTask.getSourceTable());
        infoMap.put("archiveColumns", archiveTask.getArchiveColumns());
        StringBuilder where = getWhere(archiveTask,startBottom).append(" limit ").append(archiveTask.getPerSaveNum());
        infoMap.put("where", where.toString());
        return archiveDao.select(infoMap);
    }

    @Override
    public Long selectKeyTop(ArchiveTask archiveTask) {
        CustomerContextHolder.setCustomerType(archiveTask.getSourceDatabase());
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("sourceTable", archiveTask.getSourceTable());
        infoMap.put("key",archiveTask.getKeyColumn());
        StringBuilder where = getWhere(archiveTask,null);
        //设置时间为大于条件时间
        where.replace(where.indexOf("<"),where.indexOf("<")+1,">=");
        infoMap.put("where", where.toString());
        return archiveDao.selectKeyTop(infoMap);
    }

    @Override
    public Long count(ArchiveTask archiveTask) {
        CustomerContextHolder.setCustomerType(archiveTask.getSourceDatabase());
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("sourceTable", archiveTask.getSourceTable());
        StringBuilder where = getWhere(archiveTask,null);
        infoMap.put("where", where.toString());
        return archiveDao.count(infoMap);
    }

    @Override
    public List<String> showTable(String dataSource){
        CustomerContextHolder.setCustomerType(dataSource);
        return archiveDao.showTable();
    }

    private StringBuilder getWhere(ArchiveTask archiveTask,Long startBottom) {
        StringBuilder where = new StringBuilder(" where ");
        where.append(archiveTask.getDateColumn()).append(" < \"").append(DateUtil.getNDayBeforeMorningStr(archiveTask.getDayNumber()));
        where.append("\" ");
        if (null!=startBottom){
            where.append(" and ").append(archiveTask.getKeyColumn()).append(" > ").append(startBottom).append(" ");
        }
        if (null != archiveTask.getConditionSql()) {
            where.append(archiveTask.getConditionSql());
        }
        where.append(" order by ").append(archiveTask.getKeyColumn())
                .append(" asc ")
                ;
        return where;
    }

    private String getValue(String[] columns, List list) {
        StringBuilder value = new StringBuilder();
        for (Object item : list) {
            Map map = (Map) item;
            value.append("(");
            for (int i = 0; i < columns.length; i++) {
                value.append("\"").append(map.get(columns[i])).append("\"");
                if (i < columns.length - 1) {
                    value.append(",");
                }
            }
            value.append("),");
        }
        value.replace(value.lastIndexOf(","), value.length(), "");
        return value.toString();
    }

    public static void main(String[] args) {
        String archiveColumns = "id,name,age,type";
        Random random = new Random();
        String[] columns = archiveColumns.split(",");
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, String> item = new HashMap<String, String>();
            for (String col : columns) {
                item.put(col, random.nextInt(10) + "");
            }
            list.add(item);
        }
        StringBuilder value = new StringBuilder();
        for (Object item : list) {
            Map map = (Map) item;
            value.append("(");
            for (int i = 0; i < columns.length; i++) {
                value.append("\"").append(map.get(columns[i])).append("\"");
                if (i < columns.length - 1) {
                    value.append(",");
                }
            }
            value.append("),");
        }
        value.replace(value.lastIndexOf(","), value.length(), "");
        System.out.println(value.toString());
    }
}
