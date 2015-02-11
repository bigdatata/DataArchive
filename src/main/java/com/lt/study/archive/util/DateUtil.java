package com.lt.study.archive.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Hu Feng
 * @version 1.0, May 15, 2012
 */
public class DateUtil {
	
	/** 
     * 获取某个时间所在月份的第一天凌晨 
     * @param date  
     * @return 
     */  
    public static Date getMonthStart(Date date){  
        if(date == null){ return null; }
        
        Calendar start = Calendar.getInstance();  
        start.setTime(date);  
        start.set(Calendar.DAY_OF_MONTH, 1);  
        start.set(Calendar.HOUR, 0);  
        start.set(Calendar.MINUTE, 0);  
        start.set(Calendar.SECOND, 0);  
        return start.getTime();       
    }
    /**
     * 获取当前时间指定分钟以前的时间
     * @param min
     * @return
     */
    public static Date getTimeBefore(int min)
    {
    	 return getNDayBeforeMorning(new Date(), min);
    }
    

    public static Date getNDayBeforeMorning(int dayBefore) {
        return getNDayBeforeMorning(new Date(),dayBefore);
    }

    public static Date getNDayBeforeMorning(Date date,int dayBefore) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,-dayBefore);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    public static String formatDate(Date date){
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:SSS");
        return format.format(date);
    }

    public static String getNDayBeforeMorningStr(int dayBefore){
        return formatDate(getNDayBeforeMorning(dayBefore));
    }

}
