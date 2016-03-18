package com.jsy.util.common;

/** 
 * 创 建 人： zhangyu
 * 日     期： 2015年7月29日下午4:25:24
 * 描     述：错误代码库
 * --------------------------------------
 * 修 改 人： 
 * 日     期： 
 * 描     述：  扼要说明修改原因，修改详细请注明到方法级
 * --------------------------------------
 */
public class ErrorCodeUtil {
	
	/**
	 * 未知异常
	 */
	public static Integer UNKNOWN_EXCEPTION = 1;
	
	//-----------------------系统异常-------------------------
	/**
	 * 数据库语句执行异常
	 * 
	 */
	public static Integer SQL_EXECUTE_EXCEPTION = 1000;
	
	/**
	 * 空指针异常
	 * 
	 */
	public static Integer LANG_NULLPOINTEREXCEPTION = 2000;
	
	/**
	 * 指定的类不存在
	 * 
	 */
	public static Integer LANG_CLASSNOTFOUNDEXCEPTION = 2001;
	
	/**
	 * 运算异常
	 */
	public static Integer LANG_ARITHMETICEXCEPTION = 2002;

	/**
	 * 下标越界
	 */
	public static Integer LANG_ARRAYINDEXOUTOFBOUNDSEXCEPTION = 2003;
	
	/**
	 * 方法参数错误
	 */
	public static Integer LANG_ILLEGALARGUMENTEXCEPTION = 2004;
	
	/**
	 * 字符串转换为数字异常
	 */
	public static Integer LANG_NUMBERFORMATEXCEPTION = 2005; 
	
	/**
	 * 内存溢出
	 * 
	 */
	public static Integer LANG_OUTOFMEMORYERROR = 2006;
	
	/**
	 * 方法缺少参数错误
	 */
	public static Integer LANG_MISSARGUMENTEXCEPTION = 2007;
	
	
	/**
	 * 文件没有找到的异常
	 */
	public static Integer IO_FILENOFOUNDEXCEPTION = 3000 ;
	
	
	//-------------------业务异常----------------------
	public static Integer LOGIN_PWDERROREXCEPTION = 4000;
}
