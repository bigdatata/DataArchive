package com.lt.study.archive.pojo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

public class ArchiveTask implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 源数据库
     */
    private String sourceDatabase;

    /**
     * 源数据库表
     */
    private String sourceTable;

    /**
     * 目标数据库
     */
    private String targetDatabase;

    /**
     * 目标数据库表
     */
    private String targetTable;

    /**
     * 归档类型
     */
    private Byte archiveType;

    /**
     * 主键列
     */
    private String keyColumn;

    /**
     * 归档多少天以前的数据
     */
    private Integer dayNumber;

    /**
     * 比较的时间列
     */
    private String dateColumn;

    /**
     * 所有归档的列
     */
    private String archiveColumns;

    /**
     * 归档sql条件
     */
    private String conditionSql;

    /**
     * 每次保存数量
     */
    private Integer perSaveNum;

    /**
     * 每次删除数量
     */
    private Integer perDeleteNum;

    /**
     * 归档执行时间cronExpression表达式
     */
    private String cronExpression;

    /**
     * 是否已启动任务
     */
    private Boolean isEnable;

    /**
     * 是否已删除
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    private static final long serialVersionUID = 1L;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 源数据库
     */
    public String getSourceDatabase() {
        return sourceDatabase;
    }

    /**
     * @param sourceDatabase 
	 *            源数据库
     */
    public void setSourceDatabase(String sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    /**
     * @return 源数据库表
     */
    public String getSourceTable() {
        return sourceTable;
    }

    /**
     * @param sourceTable 
	 *            源数据库表
     */
    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    /**
     * @return 目标数据库
     */
    public String getTargetDatabase() {
        return targetDatabase;
    }

    /**
     * @param targetDatabase 
	 *            目标数据库
     */
    public void setTargetDatabase(String targetDatabase) {
        this.targetDatabase = targetDatabase;
    }

    /**
     * @return 目标数据库表
     */
    public String getTargetTable() {
        return targetTable;
    }

    /**
     * @param targetTable 
	 *            目标数据库表
     */
    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    /**
     * @return 归档类型
     */
    public Byte getArchiveType() {
        return archiveType;
    }

    /**
     * @param archiveType 
	 *            归档类型
     */
    public void setArchiveType(Byte archiveType) {
        this.archiveType = archiveType;
    }

    /**
     * @return 主键列
     */
    public String getKeyColumn() {
        return keyColumn;
    }

    /**
     * @param keyColumn 
	 *            主键列
     */
    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    /**
     * @return 归档多少天以前的数据
     */
    public Integer getDayNumber() {
        return dayNumber;
    }

    /**
     * @param dayNumber 
	 *            归档多少天以前的数据
     */
    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    /**
     * @return 比较的时间列
     */
    public String getDateColumn() {
        return dateColumn;
    }

    /**
     * @param dateColumn 
	 *            比较的时间列
     */
    public void setDateColumn(String dateColumn) {
        this.dateColumn = dateColumn;
    }

    /**
     * @return 所有归档的列
     */
    public String getArchiveColumns() {
        return archiveColumns;
    }

    /**
     * @param archiveColumns 
	 *            所有归档的列
     */
    public void setArchiveColumns(String archiveColumns) {
        this.archiveColumns = archiveColumns;
    }

    /**
     * @return 归档sql条件
     */
    public String getConditionSql() {
        return conditionSql;
    }

    /**
     * @param conditionSql 
	 *            归档sql条件
     */
    public void setConditionSql(String conditionSql) {
        this.conditionSql = conditionSql;
    }

    /**
     * @return 每次保存数量
     */
    public Integer getPerSaveNum() {
        return perSaveNum;
    }

    /**
     * @param perSaveNum 
	 *            每次保存数量
     */
    public void setPerSaveNum(Integer perSaveNum) {
        this.perSaveNum = perSaveNum;
    }

    /**
     * @return 每次删除数量
     */
    public Integer getPerDeleteNum() {
        return perDeleteNum;
    }

    /**
     * @param perDeleteNum 
	 *            每次删除数量
     */
    public void setPerDeleteNum(Integer perDeleteNum) {
        this.perDeleteNum = perDeleteNum;
    }

    /**
     * @return 归档执行时间cronExpression表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression 
	 *            归档执行时间cronExpression表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * @return 是否已启动任务
     */
    public Boolean getIsEnable() {
        return isEnable;
    }

    /**
     * @param isEnable 
	 *            是否已启动任务
     */
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * @return 是否已删除
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted 
	 *            是否已删除
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created 
	 *            创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return 修改时间
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated 
	 *            修改时间
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString(){
        StringBuilder result=new StringBuilder();
        Field[] fieldList = this.getClass().getDeclaredFields();
        result.append("[");
        for (Field field:fieldList){
            try {
                result.append("\"").append(field.getName()).append("\":").append("\"").append(field.get(this)).append("\",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.replace(result.lastIndexOf(","),result.length(),"");
        result.append("]");
        return result.toString();
    }
}