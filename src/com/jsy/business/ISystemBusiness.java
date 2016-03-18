package com.jsy.business;

import java.util.Map;

public interface ISystemBusiness {
	/**
	 * 添加系统
	 * 
	 * @author jsy
	 * @return
	 */
	public Map<String, Object> insertSysInfo(Map<String, Object> systemMap);

	/**
	 * 审核接入系统
	 * 
	 * @author jsy
	 * @param 
	 * @return
	 */
	public Map<String, Object> checkSystem(Map<String, Object> sysMap);

	/**
	 * 查询接入系统
	 * 
	 * @author jsy
	 * @param User
	 * @return
	 */
	public Map<String, Object> selectSystem(Map<String, Object> sysMap);

	/**
	 * 维护接入系统
	 * 
	 * @author jsy
	 * @param 
	 * @return
	 */
	Map<String, Object> manageSystem(Map<String, Object> sysMap);

	/**
	 * 查询接入系统
	 * @author jsy
	 * @param 
	 * @return
	 */
	Map<String, Object> selectSystemOne(Map<String, Object> sysMap);

	/**
	 * 删除接入系统
	 * 
	 * @author jsy
	 * @param 
	 * @return
	 */
	Map<String, Object> deleteSystem(Map<String, Object> sysMap);
	
	/**
	 * 查询systemIndex
	 * 
	 * @author jsy
	 * @param 
	 * @return
	 */
	Map<String, Object> selectSystemIndex(Map<String, Object> sysMap);

	/**
	 * yc 修改系统状态
	 * 
	 * @param dataMap
	 * @return
	 */
	public Map<String, Object> updateSystemStatus(Map<String, Object> dataMap);

}
