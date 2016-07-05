package com.lt.study.archive.constant;

/**
 * User: luotao
 * Date: 13-12-27
 * Time: 下午3:15
 * 归档任务类型
 */
public enum ArchiveType {
    Copy_Delete(0, "拷贝删除"),
    Copy(1, "只拷贝"),
    Delete(2, "直接删除"),
    Init_Archive(3, "初次归档(拷贝删除)");;
    private int code;
    private String desc;

    ArchiveType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ArchiveType getFlagByCode(int code) {
        ArchiveType[] values = values();
        for (ArchiveType value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return Copy_Delete;
    }
}
