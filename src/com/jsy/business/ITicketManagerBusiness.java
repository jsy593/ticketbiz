package com.jsy.business;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author yichuan
 *
 */
@Service
public interface ITicketManagerBusiness {

	/**
	 * 获取当前用户下的 工单list
	 * 
	 * @param dateMap
	 * @return
	 */
	@Deprecated
	Map<String, Object> getUserTicketList(Map<String, Object> dataMap);

	/**
	 * 获取当前用户下的 工单 单条
	 * 
	 * @param dateMap
	 * @return
	 */
	Map<String, Object> getUserTicketOne(Map<String, Object> dataMap);

	/**
	 * yichuan 获取工单回复
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getTicketReply(Map<String, Object> dataMap);

	/**
	 * yichuan 客户或客服 回复工单
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addTicketReply(Map<String, Object> dataMap);

	/**
	 * 易川 添加工单流转
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addTicketMove(Map<String, Object> dataMap);

	/**
	 * 易川 工单审核
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> updateTicketMove(Map<String, Object> dataMap) throws Exception;

	/**
	 * 查看工单流转
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getTicketMove(Map<String, Object> dataMap);

	/**
	 * 查看工单流转One
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getTicketMoveOne(Map<String, Object> dataMap);

	/**
	 * 快捷回复 添加
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addQuickReply(Map<String, Object> dataMap);

	/**
	 * 快捷回复 查看
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getQuickReply(Map<String, Object> dataMap);

	/**
	 * 快捷回复 查看 one
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getQuickReplyOne(Map<String, Object> dataMap);

	/**
	 * 快捷回复 修改
	 * 
	 * @param dataMap
	 */
	@Deprecated
	Map<String, Object> updateQuickReply(Map<String, Object> dataMap);

	/**
	 * 查询工单列表
	 * 
	 * @param dataMap
	 */
	Map<String, Object> selectTicketList(Map<String, Object> dataMap);

	/**
	 * 驳回工单流转 yc
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> rejectTicketMove(Map<String, Object> dataMap);

	/**
	 * 分配 工单流转 yc
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> distributionTicketMove(Map<String, Object> dataMap)  throws Exception ;

	/**
	 * 提交工单
	 * 
	 * @param dataMap
	 */
	Map<String, Object> addTicket(Map<String, Object> dataMap);

	/**
	 * 查询单条工单
	 * 
	 * @param dataMap
	 */
	Map<String, Object> selectTicket(Map<String, Object> dataMap);

	/**
	 * 工单修改
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> updateTicket(Map<String, Object> dataMap);

}
