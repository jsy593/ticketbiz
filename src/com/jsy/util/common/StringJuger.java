package com.jsy.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 颜海龙
 * 
 */
public class StringJuger {
	/**
	 * 判断一个字符串是否为数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {
		String regex = "[0-9]{1,}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	/**
	 * 判断一个字符串是否为小数
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isFloat(String s) {
		String regex = "[0-9.]{1,}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() < 0;
	}

	/**
	 * 将字符串的某个字符转为大写
	 * 
	 * @param index
	 *            转为大写的字符在该字符串中的索引位置，从0开始
	 * @param s
	 *            被转的字符串
	 * @return
	 */
	public static String charToUppercase(int index, String s) {
		return new StringBuilder(s.length())
				.append(Character.toUpperCase(s.charAt(index)))
				.append(s.substring(index + 1)).toString();
	}

	/**
	 * 将字符串的某个字符转为小写
	 * 
	 * @param index
	 *            转为大写的字符在该字符串中的索引位置，从0开始
	 * @param s
	 *            被转的字符串
	 * @return
	 */
	public static String charToLowercase(int index, String s) {
		return new StringBuilder(s.length())
				.append(Character.toLowerCase(s.charAt(index)))
				.append(s.substring(index + 1)).toString();
	}

}
