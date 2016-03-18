package com.jsy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.IQuickReplyBusiness;

@Controller
public class QuickReplyController {

	@Autowired
	private IQuickReplyBusiness iQuickReplyBusiness;

	/**
	 * 添加快捷回复
	 * @author jsy
	 * @param map
	 * @return 0 系统错误 1 成功
	 */
	@RequestMapping(value = "/addQuickReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addQuickReply(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> result = iQuickReplyBusiness.addQuickReply(dataMap);
		
		return 	result;
	}

	/**
	 *  查询快捷回复列表
	 * @author jsy
	 * 
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败 list
	 */
	@RequestMapping(value = "/quickReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQuickReply(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> result = iQuickReplyBusiness.getQuickReplyList(dataMap);

		return result;
	}
	
	/**
	 *	查询单条快捷回复
	 * @author jsy
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/getQuickReplyOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQuickReplyOne(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> result = iQuickReplyBusiness.getQuickReplyOne(dataMap);
		
		return result;
	}

	/**
	 * 修改快捷回复
	 * @author jsy
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/updateQuickReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateQuickReply(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> result = iQuickReplyBusiness.updateQuickReply(dataMap);

		return result;
	}
	
	/**
	 * 删除快捷回复
	 * @author jsy
	 * @param map
	 * @param session
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/deleteQuickReply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteQuickReply(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> result = iQuickReplyBusiness.deleteQuickReply(dataMap);

		return result;
	}
}
