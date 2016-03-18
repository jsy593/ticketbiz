package com.jsy.business;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author yichuan
 *
 */
@Service
public interface IKnowledgeBaseBusiness {

	/**
	 * yichuan 添加知识库
	 * 
	 * @param dataMap
	 * @param user
	 * @return
	 */
	Map<String, Object> addKnowledgeBase(Map<String, Object> dataMap);

	/**
	 * 易川 查询知识库 分页
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getKnowledgeBaseList(Map<String, Object> dataMap);

	/**
	 * 易川 查询知识库 单条
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getKnowledgeBaseOne(Map<String, Object> dataMap);

	/**
	 * 易川 修改知识库
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> updateKnowledgeBase(Map<String, Object> dataMap);


}
