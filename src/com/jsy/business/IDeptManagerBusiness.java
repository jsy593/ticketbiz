package com.jsy.business;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public interface IDeptManagerBusiness {

	/**
	 * yc 添加部门
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addDept(Map<String, Object> dataMap) throws Exception;
	/**
	 * jsy 维护部门(开启或者关闭该部门)
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> manageDept(Map<String, Object> dataMap);

	/**
	 * jsy 修改部门
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> changeDept(Map<String, Object> dataMap);
	
}
