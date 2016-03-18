package com.jsy.util.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 关于日期类型转换,获取数据库服务器的时间等的一些通用方法;
 * <ul>
 * <li>1.1</li>
 * <li>增加常用的日期格式变量</li>
 * <li>增加日志记录,级别为debug</li>
 * <li>优化部分代码</li>
 * </ul>
 * 
 * @author 张强 2013年9月16日 22时18分
 * @version 1.1
 */
public class DateUtil {
	static Log log = LogFactory.getLog(DateUtil.class);

	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:sss";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY = "yyyy";
	public static final String HH_MM_SS_SSS = "HH:mm:ss:sss";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String HH_MM = "HH:mm";
	public static final String HH = "HH";
	
	public static final String PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_yyyyMM = "yyyyMM";
	public static final String PATTERN_yyyy = "yyyy";

	/**
	 * 获取整型时间
	 * @param date 传入当前时间
	 * @return 返回LONG型的时间
	 */
	public synchronized static Long dateToFullLong(Date date) {
		String date2String = null;
		try {
			if (date == null){
				return null;
			}
			
			date2String = date2String(date, PATTERN_yyyyMMddHHmmssSSS);
		} catch (Exception e) {
			log.debug("获取整形时间出错!", e);
		}
		return Long.parseLong(date2String);
	}

	/**
	 * 获取当前时间的INT型数据
	 * @param date 当前时间
	 * @return 返回INT型的时间
	 */
	public synchronized static Integer dateToyyyyMMInteger(Date date) {
		String date2String = null;
		try {
			if (date == null){
				return null;
			}
			
			date2String = date2String(date, PATTERN_yyyyMM);
		} catch (Exception e) {
			log.debug("获取当前时间的INT型数据出错!", e);
		}
		return Integer.parseInt(date2String);
	}

	/**
	 * 获取当前年度 INT型 
	 * @param date 当前时间
	 * @return 返回年度INT
	 */
	public synchronized static Integer dateToyyyyInteger(Date date) {
		String date2String = null;
		try {
			if (date == null){
				return null;
			}
			
			date2String = date2String(date, PATTERN_yyyy);
		} catch (Exception e) {
			log.debug("获取当前年度 INT型 出错!", e);
		}
		return Integer.parseInt(date2String);
	}

	/**
	 * 将特定的字符串日期转化成日期类型 <br />
	 * 1.需要格式化的字符型日期str <br />
	 * 2.可判断的格式为：yyyy-MM-dd和yyyy-MM-dd HH:mm:ss; <br />
	 * 得到日期型数据
	 * 
	 * @param str
	 *            需要处理的日期型字符串
	 * @return 返回一个日期类型的对象
	 * @see Date
	 * @see SimpleDateFormat
	 * @see StringUtil
	 * @see ParseException
	 * 
	 */
	public static Date str2Date(String str) {
		str=str.replace(".0", "");
		String formatStr = "";
		String reg1 = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
		String reg2 = "^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$";
		String reg3 = "^\\d{4}-\\d{1,2}";
		if(str.matches(reg1)){
			formatStr = YYYY_MM_DD;
		}
		if(str.matches(reg2)){
			formatStr = YYYY_MM_DD_HH_MM_SS;
		}
		if(str.matches(reg3)){
			formatStr = YYYY_MM;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		
		try {
			if (!StringUtil.isNull(str)) {
				str = StringUtil.str2Trim(str);
				Date date = sdf.parse(str);
				return date;
			}
		} catch (ParseException e) {
			log.debug("字符串日期转化成日期类型时出错!", e);
		} finally {
			sdf = null;
		}
		return null;
	}
	
	/**
	 * 将特定的字符串日期转化成日期类型 <br />
	 * 1.需要格式化的字符型日期str <br />
	 * 2.可判断的格式为：yyyy-MM-dd和yyyy-MM-dd HH:mm:ss; <br />
	 * 得到日期型数据
	 * 
	 * @param str
	 *            需要处理的日期型字符串
	 * @param formatStr
	 *            需要处理成什么格式
	 * @return 返回一个日期类型的对象
	 * @see Date
	 * @see SimpleDateFormat
	 * @see StringUtil
	 * @see ParseException
	 * 
	 */
	public static Date str2Date(String str, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		
		try {
			if (!StringUtil.isNull(str)) {
				str = StringUtil.str2Trim(str);
				Date date = sdf.parse(str);
				return date;
			}
		} catch (ParseException e) {
			log.error("字符串日期转化成日期类型时出错!", e);
		} finally {
			sdf = null;
		}
		return null;
	}

	/**
	 * 将日期型转化为特定格式的字符型日期 <br />
	 * 1.需要的日期date <br />
	 * 2.需要格式化成什么样的字符串formatStr--(yyyy-MM-dd HH:mm:ss); <br />
	 * 得到需要的字符串型日期
	 * 
	 * @param date
	 *            需要处理的日期
	 * @param formatStr
	 *            需要处理成什么样的格式
	 * @return 返回一个日期型的日期类型
	 * @see SimpleDateFormat
	 * @see Date
	 * @see Exception
	 */
	public static String date2String(Date date, String formatStr) {
		SimpleDateFormat sdf = null;
		String formatDate = null;
		try {
			if (date != null) {
				sdf = new SimpleDateFormat(formatStr);
				formatDate = sdf.format(date);
				return formatDate;
			}
		} catch (Exception e) {
			log.debug("日期型转化为特定格式的字符型时出错!!", e);
		} finally {
			sdf = null;
			formatDate = null;
		}
		return null;
	}
	
	/**
	 * 获取日期中的年
	 * 
	 * @param date 需要提取的日期
	 * @return 返回年
	 * @throws Exception
	 */
	public static Integer getYear(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		 
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	/**
	 * 获取日期中的月
	 * 
	 * @param date 需要提取的日期
	 * @return 返回月
	 * @throws Exception
	 */
	public static Integer getMonth(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		
		int re = cal.get(Calendar.MONTH);
		return re;
	}
	
	/**
	 * 获取日期中的日
	 * 
	 * @param date 需要提取的日期
	 * @return 返回日
	 * @throws Exception
	 */
	public static Integer getDate(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		
		int re = cal.get(Calendar.DATE);
		return re;
	}
	
	/**
	 * 获取日期中的时
	 * 
	 * @param date 需要提取的日期
	 * @return 返回时
	 * @throws Exception
	 */
	public static Integer getHour(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		
		int re = cal.get(Calendar.HOUR);
		return re;
	}
	
	/**
	 * 获取日期中的时
	 * 
	 * @param date 需要提取的日期
	 * @return 返回时
	 * @throws Exception
	 */
	public static Integer getMinutes(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		
		int re = cal.get(Calendar.MINUTE);
		return re;
	}
	
	/**
	 * 获取日期中的秒
	 * 
	 * @param date 需要提取的日期
	 * @return 返回秒
	 * @throws Exception
	 */
	public static Integer getSeconds(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		
		int re = cal.get(Calendar.SECOND);
		return re;
	}
	
	/**
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 4a47afdd5f01f4073694ce98bdfaa77bb208acd7
	 * 格式化时间毫秒值
	 * @param dateLon
	 * @param String
	 */
	public static String getDateString(Object dateLon, String formatStr) {
		if (dateLon != null) {
			String lonstr = dateLon.toString();
			
			Long lon = Long.parseLong(lonstr);
			Date date = new Date();
			date.setTime(lon);
			String datestr = date2String(date, formatStr);
			
			return datestr;
		} else {
			return null;
		}
				
	}
	
	/**
<<<<<<< HEAD
=======
=======
>>>>>>> 2badd6fbd23327bcca107faefe29ef3bc35b3e82
>>>>>>> 4a47afdd5f01f4073694ce98bdfaa77bb208acd7
	 * 获取日期中的星期
	 * 
	 * @param date 需要提取的日期
	 * @return 返回星期
	 * @throws Exception
	 */
	public static Integer getWeek(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);
		
		int re = cal.get(Calendar.WEEK_OF_MONTH);
		return re;
	}

	/**
	 * 创 建 人：  zhangyu
	 * 日     期：  2015年8月12日上午10:28:40
	 * 描     述：  类型转换
	 * @param obj
	 * @return
	 * <br>-----------------------------<br>
	 * 修 改 人： 
	 * 日     期： 
	 * 描     述： (注明修改原因) 
	 * <br>-----------------------------<br>
	 */
	public static BigDecimal bigObjConvert(Object obj) {
		if (!CommonUtil.isEmpty(obj)) {
			Double priceLon = Double.valueOf(obj.toString());
			BigDecimal big = BigDecimal.valueOf(priceLon);
			return big;
		} else {
			return BigDecimal.valueOf(0);
		}
	}

}
