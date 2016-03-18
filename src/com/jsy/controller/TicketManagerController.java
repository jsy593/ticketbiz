package com.jsy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.IQuickReplyBusiness;
import com.jsy.business.ITicketManagerBusiness;
import com.jsy.business.IUserManagerBusiness;
import com.jsy.controller.common.BaseController;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.JsonUtil;
import com.jsy.util.common.PageUtil;

/**
 * @author yichuan
 *
 */
@Controller
public class TicketManagerController extends BaseController {

	@Autowired
	private ITicketManagerBusiness iTicketManagerBusiness;
	@Autowired
	private IQuickReplyBusiness iQuickReplyBusiness;
	@Autowired
	private IUserManagerBusiness iUserManagerBusiness;

	/**
	 * @author yichuan 查看当前用户下未处理的工单 list
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/getUserTicketList", method = RequestMethod.POST)
	@ResponseBody
	public String getUserTicketList(@RequestParam Map<String, Object> dataMap, HttpSession session) {

		// FIXME

		// Map<String, Object> userInfo = (Map<String, Object>)
		// session.getAttribute("userInfo");
		// Map<String, Object> user = (Map<String, Object>)
		// userInfo.get("userInfo");
		// dataMap.put("ownerId", user.get("uuid"));
		dataMap.put("ownerId", "fuiyhuh2oiuhf98hu2h3yr92y3h9f7h2");

		Map<String, Object> reMap = iTicketManagerBusiness.getUserTicketList(dataMap);
		String jsonStr = JsonUtil.ObjectToJson(reMap);
		return jsonStr;
	}

	/**
	 * @author yichuan 查看当前用户下未处理的工单 单条
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/getUserTicketOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserTicketOne(@RequestParam Map<String, Object> dataMap, HttpSession session) {

		// FIXME

		// Map<String, Object> userInfo = (Map<String, Object>)
		// session.getAttribute("userInfo");
		// Map<String, Object> user = (Map<String, Object>)
		// userInfo.get("userInfo");
		// dataMap.put("ownerId", user.get("uuid"));
		dataMap.put("ownerId", "fuiyhuh2oiuhf98hu2h3yr92y3h9f7h2");

		Map<String, Object> reMap = iTicketManagerBusiness.getUserTicketOne(dataMap);
		return reMap;
	}

	/**
	 * 查询工单列表
	 * 
	 * @author jsy
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectTicketList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectTicketList(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.selectTicketList(dataMap);
		try {
			reMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(reMap.get("totalCount").toString()),
					Integer.parseInt(dataMap.get("pageSize").toString())));
		} catch (Exception e) {
			reMap.put("maxPage", 1);
		}
		return reMap;
	}

	/**
	 * 查询工单详情
	 * 
	 * @author jsy
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ticketDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ticketDetails(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.selectTicket(dataMap);
		return reMap;
	}

	/**
	 * 查看工单回复
	 * 
	 * @author yichuan
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/getTicketReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTicketReply(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.getTicketReply(dataMap);
		return reMap;
	}

	/**
	 * 添加工单回复
	 * 
	 * @author jsy
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/addTicketReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTicketReply(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.addTicketReply(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 添加工单流转
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTicketMove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTicketMove(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		// 获取审核人的id
		userMap.put("departmentId", dataMap.get("departmentId"));
		dataMap.remove("departmentId");
		userMap.put("status", "1");
		Map<String, Object> usermap = iUserManagerBusiness.selectDeptAdmin(userMap);
		if (!CommonUtil.isEmpty(usermap)) {
			Map<String, Object> user = (Map<String, Object>) usermap.get("data");
			dataMap.put("audtId", user.get("uuid"));
		}
		Map<String, Object> reMap = iTicketManagerBusiness.addTicketMove(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 查看工单流转 list
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/getTicketMove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTicketMove(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.getTicketMove(dataMap);
		try {
			reMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(reMap.get("totalCount").toString()),
					Integer.parseInt(dataMap.get("pageSize").toString())));
		} catch (Exception e) {
			reMap.put("maxPage", 1);
		}
		return reMap;
	}

	/**
	 * @author yichuan 查看工单流转 one
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/getTicketMoveOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTicketMoveOne(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.getTicketMoveOne(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 工单审核
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/checkTicketMove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkTicketMove(@RequestParam Map<String, Object> dataMap) throws Exception {

		Map<String, Object> reMap = iTicketManagerBusiness.updateTicketMove(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 工单分配
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/ticketDistribution", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ticketDistribution(@RequestParam Map<String, Object> dataMap) throws Exception {

		Map<String, Object> reMap = iTicketManagerBusiness.updateTicket(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 工单驳回
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/rejectTicketMove", method = RequestMethod.POST)
	public Map<String, Object> rejectTicketMove(@RequestParam Map<String, Object> dataMap) {

		Map<String, Object> reMap = iTicketManagerBusiness.rejectTicketMove(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 流转工单分配
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 */
	@RequestMapping(value = "/distributionTicketMove", method = RequestMethod.POST)
	public Map<String, Object> distributionTicketMove(@RequestParam Map<String, Object> dataMap) throws Exception {

		Map<String, Object> reMap = iTicketManagerBusiness.distributionTicketMove(dataMap);
		return reMap;
	}

	/**
	 * @author yichuan 工单快捷回复 添加
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 *//*
		 * @RequestMapping(value = "/addQuickReply", method =
		 * RequestMethod.POST)
		 * 
		 * @ResponseBody public String addQuickReply(@RequestParam Map<String,
		 * Object> dataMap) throws Exception {
		 * 
		 * Map<String, Object> reMap =
		 * iTicketManagerBusiness.addQuickReply(dataMap); String jsonStr =
		 * JsonUtil.ObjectToJson(reMap); return jsonStr; }
		 */
	/**
	 * @author yichuan 工单快捷回复 查看
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 *//*
		 * @RequestMapping(value = "/getQuickReply", method =
		 * RequestMethod.POST)
		 * 
		 * @ResponseBody public Map<String, Object> getQuickReply(@RequestParam
		 * Map<String, Object> dataMap) {
		 * 
		 * Map<String, Object> reMap =
		 * iTicketManagerBusiness.getQuickReply(dataMap); return reMap; }
		 */

	/**
	 * @author yichuan 工单快捷回复 查看 one
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 *//*
		 * @RequestMapping(value = "/getQuickReplyOne", method =
		 * RequestMethod.POST)
		 * 
		 * @ResponseBody public Map<String, Object>
		 * getQuickReplyOne(@RequestParam Map<String, Object> dataMap) {
		 * 
		 * Map<String, Object> reMap =
		 * iTicketManagerBusiness.getQuickReplyOne(dataMap); return reMap; }
		 */

	/**
	 * @author yichuan 工单快捷回复 修改
	 * @param model
	 * @return 0 系统错误 1 成功 2 失敗
	 *//*
		 * @RequestMapping(value = "/updateQuickReply", method =
		 * RequestMethod.POST)
		 * 
		 * @ResponseBody public Map<String, Object>
		 * updateQuickReply(@RequestParam Map<String, Object> dataMap) throws
		 * Exception {
		 * 
		 * Map<String, Object> reMap =
		 * iTicketManagerBusiness.updateQuickReply(dataMap); return reMap; }
		 */

}
