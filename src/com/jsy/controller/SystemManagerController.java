package com.jsy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.BaseBusiness;
import com.jsy.business.ISystemBusiness;
import com.jsy.util.common.PageUtil;

@Controller
public class SystemManagerController extends BaseBusiness {
	@Autowired
	private ISystemBusiness iSystemBusiness;

	/**
	 * 添加接入系统
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 10 系统名为空 11 系统名数据库中已存在
	 */
	@RequestMapping(value = "/addSystem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSystem(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iSystemBusiness.insertSysInfo(dataMap);

		return resultMap;
	}

	/**
	 * 查询接入系统
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 10 系统名为空 11 系统名数据库中已存在
	 */
	@RequestMapping(value = "/selectSystemOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSystemOne(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iSystemBusiness.selectSystemOne(dataMap);

		return resultMap;
	}

	/**
	 * 审核接入系统
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败
	 */
	@RequestMapping(value = "/checkSystem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkSystem(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iSystemBusiness.checkSystem(dataMap);

		return resultMap;
	}

	/**
	 * 查询接入系统列表
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败
	 */
	@RequestMapping(value = "/selectSystemList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSystemList(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iSystemBusiness.selectSystem(dataMap);
		try {
			resultMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(resultMap.get("totalCount").toString()),
					Integer.parseInt(dataMap.get("pageSize").toString())));
		} catch (Exception e) {
			resultMap.put("maxPage", 1);
		}
		return resultMap;
	}

	/**
	 * 维护接入系统
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败
	 */
	@RequestMapping(value = "/manageSystem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> manageSystem(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iSystemBusiness.manageSystem(dataMap);

		return resultMap;
	}

	/**
	 * yc 修改系统状态
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败
	 */
	@RequestMapping(value = "/updateSystemStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSystemStatus(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iSystemBusiness.updateSystemStatus(dataMap);

		return resultMap;
	}
}
