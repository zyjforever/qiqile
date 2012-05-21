package com.zyj.qiqile.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
	/** 格式为 xxxx-xx-xx xx:xx的日期格式 */
	public static final SimpleDateFormat basicSimpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static final SimpleDateFormat basicSimpleBirthDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	/** 默认格式 yyyy-MM-dd HH:mm */
	public static String getDateString(Date date) {
		return getDateString(basicSimpleDateFormat, date);
	}

	/** 根据指定格式返回日期 */
	public static String getDateString(SimpleDateFormat simpleDateFormat,
			Date date) {
		String result = null;
		if (date != null) {
			result = simpleDateFormat.format(date);
		}
		return result;
	}

	public static Date getSqlDateFromString(String s) {
		if (s != null) {
			try {
				return basicSimpleBirthDateFormat.parse(s);
			} catch (ParseException e) {
				return null;
			}
		} else
			return null;
	}
	
	public static Date getDateFromString(String s) {
		if (s != null) {
			try {
				return basicSimpleDateFormat.parse(s);
			} catch (ParseException e) {
				return null;
			}
		} else
			return null;
	}

	public static Date getCurrentTime(Integer hour) {
		return getCurrentTime(hour, 0);
	}

	public static Date getCurrentTime(Integer hour, Integer minutes) {
		return getCurrentTime(hour, minutes, 0);
	}

	public static Date getCurrentTime(Integer hour, Integer minutes,
			Integer second) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, second);
		return c.getTime();
	}

	public static Date getPreviewDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}
	
	public static Date getNextDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c.getTime();
	}

	public static int compare(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		return c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);
	}
	
	
}
