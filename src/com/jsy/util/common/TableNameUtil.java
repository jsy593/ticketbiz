package com.jsy.util.common;

public class TableNameUtil {

	/**
	 * 根据系统索引Id来获取表名
	 * @param t_name
	 * @param systemIndex
	 * @return
	 * @date 2015年9月16日下午2:58:08
	 */
	public static String getTableName(String t_name,String systemIndex){
		if(StringUtil.isNull(t_name)){
			return null;
		}
		StringBuilder sbStr = new StringBuilder();
		sbStr.append("t_sys");
		sbStr.append(systemIndex);
		sbStr.append(t_name.substring(1, t_name.length()));
		return sbStr.toString();
	}
	
	public static void main(String[] args) {
		String tableName = TableNameUtil.getTableName("t_knowledge","8");
		System.out.println(tableName);
	}
}
