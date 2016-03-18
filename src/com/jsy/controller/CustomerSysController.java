package com.jsy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.ICustomerSysBusiness;
import com.jsy.business.IUserManagerBusiness;
import com.jsy.controller.common.BaseController;
import com.jsy.util.common.JsonUtil;

/**
 * @author yichuan
 *
 */
@Controller
public class CustomerSysController extends BaseController {

	@Autowired
	private ICustomerSysBusiness iCustomerSysBusiness;
	@Autowired
	IUserManagerBusiness iUserManagerBusiness;

	/**
	 * yichuan 客户系统提交工单
	 * 
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/addTicket", method = RequestMethod.POST)
	@ResponseBody
	public String addTicket(@RequestParam Map<String, Object> dataMap) {
		dataMap.put("ownerId", iUserManagerBusiness.selectUserByQuestionType(dataMap).get("userId"));
		Map<String, Object> result = iCustomerSysBusiness.addTicket(dataMap);
		String jsonStr = JsonUtil.ObjectToJson(result);

		return jsonStr;
	}

	/**
	 * yichuan 客户系统查看工单 分页
	 * 
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/getTicket", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTicket(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> customerTicketList = iCustomerSysBusiness.getCustomerTicketList(dataMap);

		return customerTicketList;
	}

	/**
	 * yichuan 客户系统关闭工单
	 * 
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/closeTicket", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> closeTicket(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> result = iCustomerSysBusiness.closeTicket(dataMap);

		return result;
	}
}
