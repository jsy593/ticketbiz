package com.jsy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsy.business.IUserManagerBusiness;
import com.jsy.controller.common.BaseController;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.JsonUtil;
import com.jsy.util.common.PageUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Controller
public class UserManagerController extends BaseController {

	@Autowired
	private IUserManagerBusiness iUserManagerBusiness;

	/**
	 * @author yichuan
	 * @param model
	 * @return 0 系统错误 1 成功 4 用户名不存在 5 密码错误 6 账号禁止登陆 7 账号异常
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam Map<String, Object> map, HttpSession session) {
		Map<String, Object> reMap = iUserManagerBusiness.login(map);

		return reMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @author yichuan
	 * @param model
	 * @return 1 成功 2 失败
	 */
	@RequestMapping(value = "/updataUserIngo", method = RequestMethod.POST)
	@ResponseBody
	public String updataUserIngo(@RequestParam Map<String, Object> map) {
		Map<String, Object> reMap = iUserManagerBusiness.updateUserOne(map);
		String jsonStr = JsonUtil.ObjectToJson(reMap);
		return jsonStr;
	}

	/**
	 * 注册
	 * 
	 * @author jsy
	 * @param map
	 * @return 0 服务器错误 1 成功 2 失败 4 用户名空 9 密码不存在 8 用户名已存在
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@ResponseBody
	public String regist(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.insertUserInfo(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(resultMap);
		return jsonStr;
	}

	/**
	 * 审核管理员
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败
	 */
	@RequestMapping(value = "/checkUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUser(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.checkUser(dataMap);

		return resultMap;
	}

	/**
	 * 添加部门
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 10 部门名为空 11 部门名数据库中已存在
	 */
	@Deprecated
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	@ResponseBody
	public String addDepartment(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.insertDeptInfo(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(resultMap);
		return jsonStr;
	}

	/**
	 * 维护部门
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 2 失败 13 提示管理员部门有对应分类
	 */
	/*
	 * @RequestMapping(value = "/manageDept", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public String manageDept(@RequestParam Map<String, Object>
	 * dataMap) { Map<String, Object> resultMap =
	 * iUserManagerBusiness.manageDept(dataMap);
	 * 
	 * String jsonStr = JsonUtil.ObjectToJson(resultMap); return jsonStr; }
	 */

	/**
	 * 查询部门和分类是否有对应关系
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功 10 部门uuid为空 12 部门和分类有对应关系
	 */
	/*
	 * @RequestMapping(value = "/checkDeptAndClassify", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String CheckDeptAndClassify(@RequestParam
	 * Map<String, Object> dataMap) { Map<String, Object> resultMap =
	 * iUserManagerBusiness.checkDeptAndClassify(dataMap);
	 * 
	 * String jsonStr = JsonUtil.ObjectToJson(resultMap); return jsonStr; }
	 */

	/**
	 * 为部门添加部门管理者（需传入departmentId，uuid, status=1(账户正常)）
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功
	 */
	@RequestMapping(value = "/addDeptAdminForDept", method = RequestMethod.POST)
	@ResponseBody
	@Deprecated
	public Map<String, Object> addDeptAdminForDept(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.updateUserOne(dataMap);
		return resultMap;
	}

	/**
	 * 删除部门管理者
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功
	 */
	@RequestMapping(value = "/delDeptAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String delDeptAdmin(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.delDeptAdmin(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(resultMap);
		return jsonStr;
	}

	/**
	 * 新增部门管理者或者普通职员
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.addUser(dataMap);

		String jsonStr = JsonUtil.ObjectToJson(resultMap);
		return jsonStr;
	}

	/**
	 * 替换或恢复部门管理者
	 * 
	 * @author jsy
	 * @param dataMap
	 * @return 1 成功
	 */
	@RequestMapping(value = "/becomeDeptAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> becomeDeptAdmin(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.becomeDeptAdmin(dataMap);

		return resultMap;
	}

	/**
	 * 查询所有的部门管理者（根据roleId查询）
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return json字符串（list集合）
	 */
	@RequestMapping(value = "/SelectDeptAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String SelectDeptAdmin(@RequestParam Map<String, Object> userMap) {
		List<Map<String, Object>> resultList = iUserManagerBusiness.selectRoleList(userMap);

		String jsonStr = JsonUtil.ObjectToJson(resultList);
		return jsonStr;
	}

	/**
	 * 恢复职员
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/recoverStaff", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> recoverStaff(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.recoverStaff(userMap);

		return resultMap;
	}

	/**
	 * 根据用户uuid或者username查询部门
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectDeptByUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectDeptByUser(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.selectDeptByUser(userMap);

		return resultMap;
	}

	/**
	 * 查询部门 list
	 * 
	 * @author yc
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectDeptr(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.selectDept(dataMap);
		try {
			resultMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(resultMap.get("totalCount").toString()),
					Integer.parseInt(dataMap.get("pageSize").toString())));
		} catch (Exception e) {
			resultMap.put("maxPage", 1);
		}
		return resultMap;
	}

	/**
	 * 查询部门 one
	 * 
	 * @author yc
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectDeptOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectDeptOne(@RequestParam Map<String, Object> dataMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.selectDeptOne(dataMap);

		return resultMap;
	}

	/**
	 * 根据用户uuid或者username查询部门
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectUserBydept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectUserBydept(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> result = iUserManagerBusiness.selectUserBydept(userMap);

		return result;
	}

	/**
	 * 查询admin列表（可传入status,搜索字段前台名字为content）
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectAdminList")
	@ResponseBody
	public Map<String, Object> selectAdminList(@RequestParam Map<String, Object> userMap) {

		if (CommonUtil.isEmpty(userMap.get("content"))) {
			userMap.remove("content");
		}

		Map<String, Object> resultMap = iUserManagerBusiness.selectAdminList(userMap);
		try {
			resultMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(resultMap.get("totalCount").toString()),
					Integer.parseInt(userMap.get("pageSize").toString())));
		} catch (Exception e) {
			resultMap.put("maxPage", 1);
		}
		return resultMap;
	}

	/**
	 * 查询admin列表（可传入status,搜索字段前台名字为content）
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectDeptAdminList")
	@ResponseBody
	public Map<String, Object> selectDeptAdminList(@RequestParam Map<String, Object> userMap) {

		if (CommonUtil.isEmpty(userMap.get("content"))) {
			userMap.remove("content");
		}

		Map<String, Object> resultMap = iUserManagerBusiness.selectDeptAdminList(userMap);

		return resultMap;
	}

	/**
	 * 查询用户列表（可传入status,搜索字段前台名字为content）
	 * 
	 * @author yc
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectStaffList")
	@ResponseBody
	public Map<String, Object> selectUserList(@RequestParam Map<String, Object> userMap) {

		if (CommonUtil.isEmpty(userMap.get("content"))) {
			userMap.remove("content");
		}

		Map<String, Object> resultMap = iUserManagerBusiness.selectStaffList(userMap);
		try {
			resultMap.put("maxPage", PageUtil.getAllPage(Integer.parseInt(resultMap.get("totalCount").toString()),
					Integer.parseInt(userMap.get("pageSize").toString())));
		} catch (Exception e) {
			resultMap.put("maxPage", 1);
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectUserOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectAdminOne(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.selectUserOne(userMap);

		return resultMap;
	}

	/**
	 * 查询deptadmin列表
	 * 
	 * @author yc
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectDeptAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectDeptAdmin(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.selectDeptAdmin(userMap);

		return resultMap;
	}

	/**
	 * 查询该部门下的所有人员
	 * 
	 * @author yc
	 * @param roleMap
	 * @return json字符串
	 */
	@RequestMapping(value = "/selectDeptUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectDeptUser(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.selectDeptUser(userMap);

		return resultMap;
	}
	/**
	 * 修改密码
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return 
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.updateUserOne(userMap);
		
		return resultMap;
	}
	/**
	 * 发送验证码
	 * 
	 * @author jsy
	 * @param roleMap
	 * @return 
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendCode(@RequestParam Map<String, Object> userMap) {
		Map<String, Object> resultMap = iUserManagerBusiness.sendCode(userMap);
		
		return resultMap;
	}

}
