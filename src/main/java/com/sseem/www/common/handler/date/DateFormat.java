package com.sseem.www.common.handler.date;
 
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jaejin on 16. 2. 3..
 */
public class DateFormat{

	public static String getTodayCurrentTime(){
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatterDate.format(date);
	}

	public static String getCurrentTimeKorean(){
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		String dates = date.format(new Date(System.currentTimeMillis()));
		String year = dates.substring(0, 4), month = dates.substring(4, 6), day = dates.substring(6, 8);
		if(month.substring(0, 1).equals("0")){
			month = month.substring(1, 2);
		}else{
			month = dates.substring(0, 2);
		}
		if(day.substring(0, 1).equals("0")){
			day = day.substring(1, 2);
		}else{
			day = day.substring(0, 2);
		}
		dates = year + "년 " + month + "월 " + day + "일";
		return dates;
	}

	public static String getCurrentTime(){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String dates = date.format(new Date(System.currentTimeMillis()));
		return dates;
	}

    public static String getTodayDateUntil24Hour(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String dates = date.format(new Date(System.currentTimeMillis()));
        return dates;
    }


	public static String getCurrentWithTime(){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd.kk.mm");
		String dates = date.format(new Date(System.currentTimeMillis()));
		System.out.println(dates);
		return dates;
		
	}
    public static String getCurrentWithHour(){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd.kk");
        String dates = date.format(new Date(System.currentTimeMillis()));
        System.out.println(dates);
        return dates;

    }

	public static String getSepecificMonth(int nextOrBeforeMonth){

		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, nextOrBeforeMonth);
		String year = ""+cal.get ( cal.YEAR );
		String month = ""+ (cal.get ( cal.MONTH ) + 1);
		String day =  ""+cal.get ( cal.DATE );
		if(month.length() == 1) month ="0"+month;
		if(day.length() == 1) day = "0"+day;

		String date = year  +"-" + month +"-" +day;
		date = date + " 23:59:59";
		return date;
	}

	public static int getDayDiff(String fromDate, String toDate) {
		        
		if (fromDate.length() < 8)
			return -1;
		if (toDate.length() < 8)
			return -1;

		int year1 = Integer.parseInt(fromDate.substring(0, 4));
		int month1 = Integer.parseInt(fromDate.substring(4, 6)) - 1;
		int day1 = Integer.parseInt(fromDate.substring(6, 8));

		int year2 = Integer.parseInt(toDate.substring(0, 4));
		int month2 = Integer.parseInt(toDate.substring(4, 6)) - 1;
		int day2 = Integer.parseInt(toDate.substring(6, 8));

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.set(year1, month1, day1);
		c2.set(year2, month2, day2);

		long d1 = c1.getTime().getTime();
		long d2 = c2.getTime().getTime();
		int days = (int) ((d2 - d1) / (1000 * 60 * 60 * 24));

		return days;
	}

	public static int getLastDate(String year, String month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1, 1);

		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		return lastDay;
	}

    public static String getStringToDateFormat(String date) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(format.parse(date));
    }

    public static String getLocaleChange(String date) throws Exception{
        SimpleDateFormat enDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat korDate = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss", Locale.KOREA);
        return korDate.format(enDate.parse(date));
    }

    public static Date getStringChange(String date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA);
        return sdf.parse(date);
    }

    public static Map<String, String> getBetweenDate(int num, String type) throws Exception{
        Map<String, String> map = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Calendar cal = Calendar.getInstance();
        map.put("today", sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, num);
        map.put("before", sdf.format(cal.getTime()));
        return map;
    }

}
