package com.jsy.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsy.business.BaseBusiness;
import com.jsy.business.IDeptManagerBusiness;
import com.jsy.business.IQuestionTypeManagerBusiness;
import com.jsy.business.base.DepartmentBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;
import com.jsy.business.base.UserRoleBusinessImpl;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.UuidUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class DeptManagerBusinessImpl extends BaseBusiness implements IDeptManagerBusiness {
	@Autowired
	private IQuestionTypeManagerBusiness iQuestionTypeManagerBusiness;
	@Autowired
	private UserBusinessImpl userBusinessImpl;
	@Autowired
	private UserRoleBusinessImpl userRoleBusinessImpl;
	@Autowired
	private DepartmentBusinessImpl departmentBusinessImpl;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> addDept(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> userMap = new HashMap<String, Object>();
		Map<String, Object> userRoleMap = new HashMap<String, Object>();
		Map<String, Object> deptMap = new HashMap<String, Object>();

		// 添加部门
		String deptUuid = UuidUtil.generateUUID();
		deptMap.put("uuid", deptUuid);
		deptMap.put("name", dataMap.get("deptName"));
		deptMap.put("systemIndex", dataMap.get("systemIndex"));
		departmentBusinessImpl.insert(deptMap);

		// 查询部门管理员 uuid
		String roleId = null;
		userRoleMap.put("code", CODE_DEPARTMENT_ADMIN);
		Map<String, Object> roleOne = userRoleBusinessImpl.selectOne(userRoleMap);
		Map<String, Object> roleData = (Map<String, Object>) roleOne.get("data");
		roleId = (String) roleData.get("uuid");

		// 添加管理员
		userMap.put("roleId", roleId);
		userMap.put("departmentId", deptUuid);
		userMap.put("username", dataMap.get("code"));
		userMap.put("password", dataMap.get("password"));
		userMap.put("realName", dataMap.get("adminName"));
		userMap.put("systemIndex", dataMap.get("systemIndex"));
		userBusinessImpl.insert(userMap);

		return reMap;
	}
	// 维护部门
	@Override
	public Map<String, Object> manageDept(Map<String, Object> deptMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> queMap = new HashMap<String, Object>();
		if (!CommonUtil.isEmpty(deptMap.get("status")) && !CommonUtil.isEmpty(deptMap.get("uuid"))) {
			try {
				//查询该部门是否有对应的问题分类
				queMap = iQuestionTypeManagerBusiness.selectQueTypeByDept(deptMap);
				if (!CommonUtil.isEmpty(queMap) && deptMap.get("status").equals(0)) {//有：关闭部门时,提醒管理员
					reMap = departmentBusinessImpl.update(deptMap);
					reMap.put(KEY_STATE, STATE_TEWELVE);//提示管理员该部门有对应分类
					
				} else {//无
					reMap = departmentBusinessImpl.update(deptMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}
			
		}

		return reMap;
	}

	@Override
	public Map<String, Object> changeDept(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = departmentBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

}
