package com.admin.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* 日期工具类
* @author hepeng
* @date 2017年6月29日上午11:42:57
*/
public class DateUtil {

	/** 获取当前时间yyyy-mm-dd-hh-mm-ss **/
	public static String getDateyMdHms() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	/** 获取当前时间yyyy-mm-dd-hh-mm-ss **/
	public static String getDateyyyyMmddHhmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 验证是否为指定的日期格式<caoyong>
	 * @param str 被验证日期
	 * @param format 指定的日期格式
	 * @return boolean
	 */
	public static boolean isValidDate(String str, String format) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			sdf.setLenient(false);
			sdf.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	
	/**
	 * 日期格式转换   由"yyyy-MM-dd"转换为"yyyyMMdd"
	 * @return
	 */
	public static String transFormDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
		return sdfs.format(date); 
	}
	
	
	/**
	 * 获取当前时间(时分秒)<yangzhi>
	 * @return HH:mm:ss
	 */
	public static String getDateHms() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	/** 获取当前时间HH:mm:ss.SSS **/
	public static String getDateHHmmssSSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	
	/** 获取当前日期yyyyMMdd **/
	public static String getDateYyyyMMdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	/** 获取当前日期yyyyMMdd **/
	public static String getYears() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	/** 获取当前日期yyyy-MM-dd **/
	public static String getDateYYYYMMdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	
	/** 获取当前年份 */
	public static String getDateYYYY() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	/** 获取当前月份 */
	public static String getMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
	/** 获取当前时间HHmmss **/
	public static String getDateHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date(System.currentTimeMillis()));
	}
		
	/**
	 * 获取两个日期相差的天数
	 * @param time1  开始日期yyyyMMdd
	 * @param time2  结束日期yyyyMMdd
	 * @return 天数
	 */
	public static long getDaty(String time1, String time2) {
		long daty = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date1 = df.parse(time1);
			Date date2 = df.parse(time2);
			daty = date1.getTime() - date2.getTime();
			daty = daty / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return daty;
	}
	
	
	 /**
	  * 获取指定年指定月份的最后一天
	 * @param year -年份
	 * @param month -月份
	 * @return  yyyyMMdd
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year); 								// 设置年份
		cal.set(Calendar.MONTH, month - 1); 						// 设置月份
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  // 获取某月最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay); 					// 设置日历中月份的最大天数
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 	// 格式化日期
		String lastDayOfMonth = sdf.format(cal.getTime()); 			// 当月最后一天  yyyyMMdd 类型字符串
		return lastDayOfMonth;
	}

	/**
	 * 获取当月最后一天日期
	 * @return yyyyMMdd
	 */
	public static String getLastDayOfCurrMonth() {
		int year = Integer.parseInt(getDateYYYY());
		int month = Integer.parseInt(getMonth());
		return getLastDayOfMonth(year, month);
	}
	
	/**
	 * 获取指定年份的最后一天
	 * @param year -年份
	 * @return yyyyMMdd
	 */
	public static String getLastDayOfYear(int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);  								//设置年份
        cal.set(Calendar.MONTH, 11);								//设置月份
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  //获取某月最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);					//设置日历中月份的最大天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");	//格式化日期
        String lastDayOfMonth = sdf.format(cal.getTime());			//当年最后一天 yyyyMMdd 字符串
        return lastDayOfMonth;
    }
	
	/**
	 * 获取传入日期前一天
	 * @param date
	 * @return String 格式为yyyyMMdd
	 */
	public static String getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String format = sdf.format(date);
		return format;
	}
	
	/**
	 * 对日期增加天数，需要减掉天数，传负数
	 * @param dt
	 * @param days
	 * @return
	 */
	public static String addDateTime(String dt,int days) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dt);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_YEAR,days);//日期加10天
		Date newDt=rightNow.getTime();
		return sdf.format(newDt);
	}

	/**
	 * 获取当前日期的上一天
	 * 
	 * @author hepeng
	 * @date 2018/5/10 17:03
	 */
	public static String getLastDay(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(c.DAY_OF_YEAR,-1);
		date = c.getTime();
		return sdf.format(date);
	}

	/**
	 * 获取当前日期的上一月最后一天日期
	 * 
	 * @author hepeng
	 * @date 2018/5/10 17:04
	 */
	public static String getEndDateOfLastM(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 0);
		date = c.getTime();
		return sdf.format(date);
	}

	/**
	 * 获取当前日期的上一季度最后一天日期
	 *
	 * @author hepeng
	 * @date 2018/5/10 17:04
	 */
	public static String getEndDateOfLastQ(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.MONTH);
		int n = m % 3;
		c.add(Calendar.MONTH, -n);
		c.set(Calendar.DAY_OF_MONTH, 0);
		date = c.getTime();
		return sdf.format(date);
	}

	/**
	 * 获取当前日期的上一年度最后一天日期
	 *
	 * @author hepeng
	 * @date 2018/5/10 17:04
	 */
	public static String getEndDateOfLastY(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_YEAR, 0);
		date = c.getTime();
		return sdf.format(date);
	}

	/**
	 * 	判断某天是否为月末
	 * 
	 * @author hepeng
	 * @date 2018/5/24 12:11
	 */
	public static boolean isLastDayOfMonth(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i  = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当天是否为季末
	 *
	 * @author hepeng
	 * @date 2018/5/10 17:04
	 */
	public static boolean isLastDayOfQuarter(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.MONTH);
		int n = m % 3;
		c.add(Calendar.MONTH, 3-n);
		c.set(Calendar.DAY_OF_MONTH, 0);
		date = c.getTime();
		String lastDayOfQ = sdf.format(date);
		if(dateStr !=null | lastDayOfQ != null) {
			return dateStr.equals(lastDayOfQ);
		}else
			return false;
	}

	/**
	 * 判断当天是否为年末
	 *
	 * @author hepeng
	 * @date 2018/5/10 17:04
	 */
	public static boolean isLastDayOfYear(String dateStr) throws ParseException {
		String lastDayOfY = getLastDayOfYear(dateStr);
		if(dateStr !=null | lastDayOfY != null) {
			return dateStr.equals(lastDayOfY);
		}else
			return false;
	}

	/**
	 * 	获取指定日期年度最后一天
	 * 
	 * @author hepeng
	 * @date 2018/5/31 17:18
	 */
	public static String getLastDayOfYear(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, 1);
		c.set(Calendar.DAY_OF_YEAR, 0);
		date = c.getTime();
		return sdf.format(date);
	}

	/**
	 * 	获取指定日期年度第一天
	 *
	 * @author hepeng
	 * @date 2018/5/31 17:18
	 */
	public static String getFirstDayOfYear(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(dateStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_YEAR, 0);
		c.add(Calendar.DAY_OF_YEAR, 1);
		date = c.getTime();
		return sdf.format(date);
	}

}
