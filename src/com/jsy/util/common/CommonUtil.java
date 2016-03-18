package com.jsy.util.common;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通用方法 -- 包含对象,集合(list,listmap,数组...)等一系列工具列
 * 
 * @author 张强 2013年9月16日 22时18分
 * @version 1.1
 */
public class CommonUtil {
	public static Log log = LogFactory.getLog(CommonUtil.class);
	
	/**
	 * 逗号 -- 分隔符
	 */
	public static final String SPLIT_COMMA = ",";
	
	/**
	 * 连字号(减叼) -- 分隔符
	 */
	public static final String SPLIT_HYPHEN = "-";
	
	/**
	 * minus　减号；负号 -- 分隔符
	 */
	public static final String SPLIT_PLUS = "-";
	
	/**
	 * 和，引用  -- 分隔符
	 */
	public static final String SPLIT_AND = "&";
	
	/**
	 * apostrophe　撇号 `
	 */
	public static final String SPLIT_APOSTROPHE = "`";
	
	/**
	 * pound　井号
	 */
	public static final String SPLIT_POUND = "#";
	
	/**
	 * 星号
	 */
	public static final String SPLIT_START = "*";
	
	/**
	 * 波浪符
	 */
	public static final String SPLIT_TILDE = "~";
	
	/**
	 * 冒号
	 */
	public static final String SPLIT_COLON = ":";
	
	/**
	 * 分号
	 */
	public static final String SPLIT_SEMICOLON = ";";
	
	/**
	 * 问号
	 */
	public static final String SPLIT_QUESTION = "?";
	
	/**
	 * AT符号 
	 */
	public static final String SPLIT_AT = "@";
	
	/**
	 * 判断对象是否为空,传入的对象可以是任何对象
	 * 
	 * @param o 需要判定的对象,当传入LIST时,可能会进入isEmpty(Object... o)方法 
	 * @return  当对象为空时为真(true),当对象不为空时为假(false)
	 *  
	 */
	public static Boolean isEmpty(Object o) {
		if (o == null) {
			log.debug("CommonUtils...isEmpty >>>> The object has no any reference, is an empty object");
			return true;
		} else if (o instanceof Collection<?>) {
			log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖Collection(" + o.getClass().getName() + ")〗 is empty");
			if (((Collection<?>)o).isEmpty()) {
				return true;
			}
		} else if (o instanceof String) {
			log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖String(" + o.getClass().getName() + ")〗 is empty");
			if (((String)o).trim().length() <= 0 || "undefined".equals(((String)o).trim())) {
				return true;
			}
		} else if (o instanceof Map<?, ?>) {
			log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖Map(" + o.getClass().getName() + ")〗 is empty");
			if (((Map<?, ?>)o).isEmpty()) {
				return true;
			}
		} else if (o.getClass().isArray()) {
			log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖Array(" + o.getClass().getName() + ")〗 is empty");
			if (Array.getLength(o) <= 0) {
				return true;
			}
		} 
		
		return false;
	}
	
	/**
	 * 判断对象是否为空,传入的对象可以是任何对象
	 * 
	 * @param o  需要判定的对象,当传入LIST时,可能会进入这个方法
	 * @return 当对象为空时为真(true),当对象不为空时为假(false)
	 *  
	 */
	public static Boolean isEmpty(Object... o) {
		if (o == null || o != null && o.length <= 0) {
			return true;
		}
		
		for (int i = 0; i < o.length; i++) {
			if (o[i] == null) {
				log.debug("CommonUtils...isEmpty >>>> The object has no any reference, is an empty object");
				return true;
			} else if (o[i] instanceof Collection<?>) {
				log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖Collection(" + o[i].getClass().getName() + ")〗 is empty");
				if (((Collection<?>)o[i]).isEmpty()) {
					return true;
				}
			} else if (o[i] instanceof String) {
				log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖String(" + o[i].getClass().getName() + ")〗 is empty");
				if (((String)o[i]).trim().length() <= 0 ) {
					return true;
				}
			} else if (o[i] instanceof Map<?, ?>) {
				log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖Map(" + o[i].getClass().getName() + ")〗 is empty");
				if (((Map<?, ?>)o[i]).isEmpty()) {
					return true;
				}
			} else if (o[i].getClass().isArray()) {
				log.debug("CommonUtils...isEmpty >>>> Is testing whether the 〖Array(" + o[i].getClass().getName() + ")〗 is empty");
				if (Array.getLength(o[i]) <= 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 将指定位置的资源文件读取到MAP中,位置从classpath位置开始
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object>  readToMapString(String filePath) throws Exception {
        // define Properties property = new Properties();  
        Properties property = new Properties();  
        InputStream inputFile = null;  
        Iterator<Entry<Object, Object>> iter = null;
		Entry<Object, Object> entry = null;
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	// classloader can be use this class, um, rt: ABC.class.ClassLoader
//        	inputFile = ClassLoader.getSystemResourceAsStream(filePath);
        	inputFile = CommonUtil.class.getResourceAsStream(filePath);
            // 装载配置文件  
            property.load(inputFile);
            iter = property.entrySet().iterator();
			while(iter.hasNext()){
				entry = iter.next();
				map.put(entry.getKey().toString().toLowerCase().trim(), entry.getValue().toString().toLowerCase().trim());
			}
        } finally {  
            // 关闭输入流 
            if (inputFile != null) {  
                inputFile.close();  
            }
        }  
        return map;
	}
	
	/**
	 * 将指定位置的资源文件读取到MAP中,位置从classpath位置开始
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Map<Object, Object>  readToMapLower(String filePath) throws Exception {
        // define Properties property = new Properties();  
        Properties property = new Properties();  
        InputStream inputFile = null;  
        Iterator<Entry<Object, Object>> iter = null;
		Entry<Object, Object> entry = null;
		Map<Object, Object> map = new HashMap<Object, Object>();
        try {
        	// classloader can be use this class, um, rt: ABC.class.ClassLoader
//        	inputFile = ClassLoader.getSystemResourceAsStream(filePath);
        	inputFile = CommonUtil.class.getResourceAsStream(filePath);
            // 装载配置文件  
            property.load(inputFile);
            iter = property.entrySet().iterator();
			while(iter.hasNext()){
				entry = iter.next();
				map.put(entry.getKey().toString(), entry.getValue().toString().toLowerCase());
			}
        } finally {  
            // 关闭输入流 
            if (inputFile != null) {  
                inputFile.close();  
            }
        }  
        return map;
	}
	
	/**
	 * 将指定位置的资源文件读取到MAP中,位置从classpath位置开始
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Map<Object, Object>  readToMap(String filePath) throws Exception {
        // define Properties property = new Properties();  
        Properties property = new Properties();  
        InputStream inputFile = null;  
        try {
        	// classloader can be use this class, um, rt: ABC.class.ClassLoader
//        	inputFile = ClassLoader.getSystemResourceAsStream(filePath);
        	inputFile = CommonUtil.class.getResourceAsStream(filePath);
            // 装载配置文件  
            property.load(inputFile);
        } finally {  
            // 关闭输入流 
            if (inputFile != null) {  
                inputFile.close();  
            }
        }  
        return property;
	}
	
	/**
	 * 
	 * 把byte数组转换为16进制数
	 * @param source
	 * @return
	 */
	public static String toHex(byte[] source){
		StringBuffer b = new StringBuffer();
		
		for (int i = 0; i < source.length; i++) {
			b.append(Integer.toHexString(source[i]));
		}
		return  b.toString();
	}
	
	/**
	 * 将对象清空, 断开引用, 集合断开前,会清空集合中的数据,慎用
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Boolean destroyObject(Object obj) throws Exception {
		if (isEmpty(obj)) {
			return false;
		}
		if (obj instanceof Map<?, ?>) {
			((Map<?, ?>)obj).clear();
			obj = null;
			return true;
		} else if (obj instanceof Collection<?>) {
			((Collection<?>)obj).clear();
			obj = null;
			return true;
		} else {
			obj = null;
			return true;
		}
	}
	
	/**
	 * 将对象清空, 断开引用, 集合断开前,会清空集合中的数据,慎用
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static Boolean destroyObject(Object... obj) throws Exception {
		int length = obj.length;
		for (int i = 0; i < length; i++) {
			if (isEmpty(obj[i])) {
				return false;
			}
			if (obj[i] instanceof Map<?, ?>) {
				((Map<?, ?>)obj[i]).clear();
				obj[i] = null;
				return true;
			} else if (obj[i] instanceof Collection<?>) {
				((Collection<?>)obj[i]).clear();
				obj[i] = null;
				return true;
			} else {
				obj[i] = null;
				return true;
			}
		}
		return null;
	}
	
	/**
	 * 断开对象的引用,不清空数据
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Boolean offObject(Object obj) throws Exception {
		if (isEmpty(obj)) {
			return false;
		} else {
			obj = null;
			return true;
		}
	}
	
	/**
	 * 断开对象的引用,不清空数据
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Boolean offObject(Object... obj) throws Exception {
		if (isEmpty(obj)) {
			return false;
		} else {
			obj = null;
			return true;
		}
	}
	
	/**
	 * 随机生成6位数字
	 * @return
	 * @throws Exception
	 */
	public static String getDynPassword() throws Exception {
		double random = Math.random();
		String dynStr = String.valueOf(random).substring(3, 9);
		return dynStr;
	}
	
	/**
	 * 将大数据类型(BLOB) Byte 转换为字符串
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static Object getBlobToString(Object value) throws Exception {
		if(!(value instanceof byte[])){
			if(value == null){
				value = "";
			}else{
				value = value.toString();
			}
			return value;
		}else{
			if (value == null) {
				value = "";
			} else {
				value = new String((byte[]) value);
			}
			return value;
		}
	}
	
	/**  
     * 分转换为元.  
     *   
     * @param fen  
     *            分  
     * @return 元  
     */  
    public static String fromFenToYuan(final String fen) {  
        String yuan = "";  
        final int MULTIPLIER = 100;  
        Pattern pattern = Pattern.compile("^[0-9][0-9]*{1}");  
        Matcher matcher = pattern.matcher(fen);  
        if (matcher.matches()) {  
            yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();  
        } else {  
            System.out.println("参数格式不正确!");  
        }  
        return yuan; 
    }  
  
    /**  
     * 元转换为分.  
     *   
     * @param yuan  
     *            元  
     * @return 分  
     */  
    public static String fromYuanToFen(final String yuan) {  
        String fen = "";  
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?{1}");  
        Matcher matcher = pattern.matcher(yuan);  
        if (matcher.matches()) {  
            try { 
                NumberFormat format = NumberFormat.getInstance();  
                Number number = format.parse(yuan);
                double temp = number.doubleValue() * 100.0;  
                // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012  
                format.setGroupingUsed(false);  
                // 设置返回数的小数部分所允许的最大位数  
                format.setMaximumFractionDigits(0);  
                fen = format.format(temp);  
            } catch (ParseException e) {  
                  
            }
        }else{  
            System.out.println("参数格式不正确!");  
        }  
        return fen;  
    } 
    

	/**
	 * 生成订单号
	 * <br />
	 * 订单号需要指定订单类型
	 * 
	 * @param payType	支付类型, 用BaseBusiness的静态变量
	 * @return
	 * @throws Exception
	 */
	public static String getOrderNumber(String payType) throws Exception {
		StringBuffer reOrder = null;
		String date2String = null;
		try {
			reOrder = new StringBuffer(payType);
			date2String = DateUtil.date2String(new Date(), "yyyyMMddHHmmss");
			reOrder.append(date2String);
		} catch (Exception e) {
			
//			log.debug("生成订单号出错!", e);
			throw e;
		}
		return reOrder.toString();
	}
	
	/**
	 * 生成新的文件名
	 */
   public static String getNewFilename(String filename){
	  String filetype = filename.substring(filename.lastIndexOf(".")+1);
	  return System.currentTimeMillis()+"."+filetype;
   }
   
   /**
    * 返回原文件名
    */
   public static String getOrginalFilename(String filename){
	   String purename = filename.substring(0, filename.lastIndexOf("."));
	   String filetype = filename.substring(filename.lastIndexOf(".")+1);
	   if(filename.indexOf("-") != -1){
		   return purename.substring(0, filename.lastIndexOf("-"))+"."+filetype;
	   }
	   return filename;
   }
}
