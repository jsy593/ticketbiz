package com.jsy.business;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author yichuan
 *
 */
@Service
public interface ICustomerSysBusiness {

	/**
	 * 易川
	 * 
	 * 获取用户自己提交的工单 分页
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> getCustomerTicketList(Map<String, Object> dataMap);

	/**
	 * 易川 客户系统关闭工单
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> closeTicket(Map<String, Object> dataMap);

	/**
	 * 易川 客户系统添加工单
	 * 
	 * @param dataMap
	 * @return
	 */
	Map<String, Object> addTicket(Map<String, Object> dataMap);

	/**
	 * 易川 客户系统工单分页查询
	 * 
	 * @param dataMap
	 * @return
	 */
	@Deprecated
	Map<String, Object> selectTicketListByMap(Map<String, Object> dataMap, String tableName);

}
