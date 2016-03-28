package com.jsy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.IDeptManagerBusiness;
import com.jsy.util.common.JsonUtil;

@Controller
public class DeptManagerController {
	@Autowired
	private IDeptManagerBusiness iDeptManagerBusiness;
	/**
	 * @author yichuan 添加部门
	 * @param model
	 * @return 0 系统错误 1 成功 4 用户名不存在 5 密码错误 6 账号禁止登陆 7 账号异常
	 */
	@RequestMapping(value = "/addDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDept(@RequestParam Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = iDeptManagerBusiness.addDept(dataMap);

		return reMap;
	}
	/**
	 * 维护部门
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 13 提示管理员部门有对应分类
	 */
	@RequestMapping(value = "/manageDept", method = RequestMethod.POST)
	@ResponseBody
	public String manageDept(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iDeptManagerBusiness.manageDept(dataMap);
		String jsonStr = JsonUtil.ObjectToJson(resultMap);
		return jsonStr;
	}
	
	/**
	 * 修改部门
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 13 提示管理员部门有对应分类
	 */
	@RequestMapping(value = "/updateManageDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateManageDept(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iDeptManagerBusiness.changeDept(dataMap);
		
		return resultMap;
	}
	
	/**
	 * 查询部门
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 13 提示管理员部门有对应分类
	 */
	@RequestMapping(value = "/selectDeptByMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectDeptBySystemIndex(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iDeptManagerBusiness.selectDept(dataMap);
		
		return resultMap;
	}
}
