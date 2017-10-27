package com.wugao.vankeda.infrastructure.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String getDate(String format) {
		return getDate(Calendar.getInstance().getTime(), format);
	}

	public static String getDate() {
		return getDate(Calendar.getInstance().getTime(), "yyyy-MM-dd");
	}

	public static String getDateTime() {
		return getDateTime(Calendar.getInstance().getTime());
	}

	public static String getDateTime(Date date) {
		return getDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date parse(String dataString) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parse(String dataString, SimpleDateFormat sdf) {
		try {
			return sdf.parse(dataString);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date parseDateTime(String dateTime) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public static int daysBetween(Date begin, Date end) {
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(end);
		cReturnDate.setTime(begin);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToDays(intervalMs);
	}

	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs / (1000 * 86400));
	}

	public static Date addSeconds(Date date, int seconds) {
		Calendar cNow = Calendar.getInstance();
		cNow.setTime(date);
		cNow.add(Calendar.SECOND, seconds);
		return new Date(cNow.getTimeInMillis());
	}

	public static Date addDays(Date date, int days) {
		Calendar cNow = Calendar.getInstance();
		cNow.setTime(date);
		cNow.add(Calendar.DAY_OF_MONTH, days);
		return new Date(cNow.getTimeInMillis());
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取两个日期的间隔天�?
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static int getIntervalDays(Date startTime, Date endTime) {
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(startTime);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(endTime);
		if (cStart.after(cEnd)) {
			Calendar cal = cStart;
			cStart = cEnd;
			cEnd = cal;
		}
		long sl = cStart.getTimeInMillis();
		long el = cEnd.getTimeInMillis();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}
	
	/**
	 * 获取获取多天任务的真实结束时�?
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static Date getMultiDayPlanEndTime(Date startTime, Date endTime) {
		Calendar returnC = Calendar.getInstance();
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(startTime);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(endTime);
		if (cStart.after(cEnd)) {
			Calendar cal = cStart;
			cStart = cEnd;
			cEnd = cal;
		}
		long sl = cStart.getTimeInMillis();
		long el = cEnd.getTimeInMillis();
		long ei = el - sl;
		if(ei % (1000 * 60 * 60 * 24) == 0){
			returnC.set(endTime.getYear()+1900, endTime.getMonth(), endTime.getDate(), 23, 59, 59);
		}
		return returnC.getTime();
	}
	
	/** 
     * 得到本季度第�?天的日期 
     * @Methods Name getFirstDayOfQuarter 
     * @return Date 
     */  
    public static Date getFirstDayOfQuarter(Date date)   {     
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(date);  
        int curMonth = cDay.get(Calendar.MONTH);  
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){    
            cDay.set(Calendar.MONTH, Calendar.JANUARY);  
        }  
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){    
            cDay.set(Calendar.MONTH, Calendar.APRIL);  
        }  
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {    
            cDay.set(Calendar.MONTH, Calendar.JULY);  
        }  
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {    
            cDay.set(Calendar.MONTH, Calendar.OCTOBER);  
        }  
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));  
        System.out.println(cDay.getTime());  
        return cDay.getTime();     
    }  
    
    public static Date getFirstDayOfQuarterNew(Date date)   {     
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(date);  
        int curMonth = cDay.get(Calendar.MONTH) + 1;  
        if (curMonth >= 1 && curMonth <= 3){    
            cDay.set(Calendar.MONTH, 0);  
        }  
        if (curMonth >= 4 && curMonth <= 6){    
            cDay.set(Calendar.MONTH, 3);  
        }  
        if (curMonth >= 7 && curMonth <= 9) {    
            cDay.set(Calendar.MONTH, 6);  
        }  
        if (curMonth >= 10 && curMonth <= 12) {    
            cDay.set(Calendar.MONTH, 9);  
        }  
        cDay.set(Calendar.DATE, 1);	
//        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));  
        System.out.println(cDay.getTime());  
        return cDay.getTime();     
    }
    /** 
     * 得到本季度最后一天的日期 
     * @Methods Name getLastDayOfQuarter 
     * @return Date 
     */  
    public static Date getLastDayOfQuarter(Date date)   {     
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(date);  
        int curMonth = cDay.get(Calendar.MONTH);  
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){    
            cDay.set(Calendar.MONTH, Calendar.MARCH);  
        }  
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){    
            cDay.set(Calendar.MONTH, Calendar.JUNE);  
        }  
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {    
            cDay.set(Calendar.MONTH, Calendar.AUGUST);  
        }  
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {    
            cDay.set(Calendar.MONTH, Calendar.DECEMBER);  
        }  
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));  
        System.out.println(cDay.getTime());  
        return cDay.getTime();     
    }
    
    public static Date getLastDayOfQuarterNew(Date date)   {     
        Calendar cDay = Calendar.getInstance();     
        cDay.setTime(date);  
        int curMonth = cDay.get(Calendar.MONTH) + 1;  
        if (curMonth >= 1 && curMonth <= 3){    
            cDay.set(Calendar.MONTH, 2);
            cDay.set(Calendar.DATE, 31);
        }  
        if (curMonth >= 4 && curMonth <= 6){    
        	cDay.set(Calendar.MONTH, 5);
            cDay.set(Calendar.DATE, 30); 
        }  
        if (curMonth >= 7 && curMonth <= 9) {    
        	cDay.set(Calendar.MONTH, 8);
            cDay.set(Calendar.DATE, 30);  
        }  
        if (curMonth >= 10 && curMonth <= 12) {    
        	cDay.set(Calendar.MONTH, 11);
            cDay.set(Calendar.DATE, 31);
        }  
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));  
        System.out.println(cDay.getTime());  
        return cDay.getTime();     
    }
}
