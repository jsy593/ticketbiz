package com.jsy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.IQuestionTypeManagerBusiness;
import com.jsy.controller.common.BaseController;
import com.jsy.util.common.JsonUtil;
import com.jsy.util.common.PageUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Controller
public class QuestionTypeManagerController extends BaseController {

	@Autowired
	IQuestionTypeManagerBusiness iQuestionTypeManagerBusiness;

	/**
	 * @author yichuan 获取问题分类list
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/getQuestionTypeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQuestionTypeList(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.getQuestionTypeList(dataMap);
		try {
			reMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(reMap.get("totalCount").toString()),
					Integer.parseInt(dataMap.get("pageSize").toString())));
		} catch (Exception e) {
			reMap.put("maxPage", 1);
		}
		return reMap;
	}

	/**
	 * @author yichuan 获取问题分类单条
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/getQuestionTypeOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getQuestionTypeone(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.getQuestionTypeOne(dataMap);

		return reMap;
	}

	/**
	 * @author yichuan 添加问题分类
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/addQuestionType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addQuestionType(@RequestParam Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.addQuestionType(dataMap);

		return reMap;
	}

	/**
	 * @author yichuan 修改问题分类
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/updateQuestionType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateQuestionType(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.updateQuestionType(dataMap);

		return reMap;
	}

	/**
	 * @author yichuan 获取问题分类对应关系 list
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/getQuestionTypeForUserList", method = RequestMethod.POST)
	@ResponseBody
	public String getQuestionTypeForUserList(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.getQuestionTypeForUserList(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(reMap);
		return jsonStr;
	}

	/**
	 * @author yichuan 获取问题分类对应关系单条
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/getQuestionTypeForUserOne", method = RequestMethod.POST)
	@ResponseBody
	public String getQuestionTypeForUserOne(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.getQuestionTypeForUserOne(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(reMap);
		return jsonStr;
	}

	/**
	 * @author yichuan 添加问题分类对应关系
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/addQuestionTypeForUser", method = RequestMethod.POST)
	@ResponseBody
	public String addQuestionTypeForUser(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.addQuestionTypeForUser(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(reMap);
		return jsonStr;
	}

	/**
	 * @author yichuan 修改问题分类对应关系
	 * @param model
	 * @return 0 系统错误 1 成功 2 失败
	 */
	@RequestMapping(value = "/updateQuestionTypeForUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateQuestionTypeForUser(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> reMap = iQuestionTypeManagerBusiness.updateQuestionTypeForUser(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(reMap);
		return jsonStr;
	}

}
