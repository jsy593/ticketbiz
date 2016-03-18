package com.jsy.util.common;


/**
 * 关于字符的一些工具方法
 * 
 * @author 张强 2013年9月16日 22时19分
 * @version 1.0
 */
public class StringUtil {
	/**
	 * 将字符前后的空格去掉
	 * 
	 * @param str 需要处理的字符串
	 * @return 返回处理后的字符串
	 */
	public static String str2Trim(String str) {
		if (str != null || str != null && !"".equals(str)) {
			return str.trim();
		}
		return null;
	}
	
	/**
	 * 判断字符是否为空
	 * <br />
	 *  空返回TRUE
	 *  <br />
	 *  非空返回FALSE
	 *  
	 * @param str 需要处理的字符串
	 * @return
	 */
	public static Boolean isNull(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
}
