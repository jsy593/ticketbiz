package com.jsy.util.common;


/**
 * @Description 操作消息
 * @author schoff [2015年7月21日]
 */
public final class StateInfo {
	
	/*
	 * 格式：
	 * 系统管理 10
	 * 		一级子集 01
	 * 			 底部子集 001
	 * 通用	00
	 * 		一级子集 01
	 * 			 底部子集 001
	 * 用户	20
 * 			一级子集 01
	 * 			 底部子集 001
	 * */
	
	public static final String SUCCESS 						= "0"; // 成功
	public static final String FAILURE 						= "-1"; // 失败
	public static final String COMM_PARAM_ERROR 			= "0001001"; // 参数错误
	public static final String COMM_RETURN_PK 				= "0001002"; // 不支持返回主键
	public static final String COMM_RETURN_ROWS				= "0001003"; // 不支持返回影响行数
	public static final String COMM_ARRAY_INDEXOUT 			= "0001004"; // 数组下标越界
	public static final String COMM_UNKNOWN 				= "0001005"; // 数组下标越界
	public static final String USER_NAME_EXIST 				= "2001003"; // 用户名不存在
	
	
}
