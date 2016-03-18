package com.jsy.business;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public interface IQuestionTypeManagerBusiness {

	/**
	 * jsy 根据部门uuid查询该部门是否有对应的问题分类 2015-9-20
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> selectQueTypeByDept(Map<String, Object> dataMap);

	/**
	 * yichuan 获取问题分类 list
	 * 
	 * @param dataMap
	 * @return
	 * */
	Map<String, Object> getQuestionTypeList(Map<String, Object> dataMap);

	/**
	 * 易川 添加问题分类
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addQuestionType(Map<String, Object> dataMap) throws Exception;

	/**
	 * yicahun 修改问题分类
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> updateQuestionType(Map<String, Object> dataMap);

	/**
	 * yichuan 获取问题分类 单条
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getQuestionTypeOne(Map<String, Object> dataMap);

	/**
	 * 获取问题分类对应关系 list
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getQuestionTypeForUserList(Map<String, Object> dataMap);

	/**
	 * 获取问题分类对应关系 单条
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getQuestionTypeForUserOne(Map<String, Object> dataMap);

	/**
	 * 修改问题分类对应关系
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> updateQuestionTypeForUser(Map<String, Object> dataMap);

	/**
	 * 添加问题分类对应关系
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addQuestionTypeForUser(Map<String, Object> dataMap);

}
