package com.jsy.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsy.business.BaseBusiness;
import com.jsy.business.IQuestionTypeManagerBusiness;
import com.jsy.business.base.AccessSystemBusinessImpl;
import com.jsy.business.base.DepartmentBusinessImpl;
import com.jsy.business.base.QuestionTypeBusinessImpl;
import com.jsy.business.base.QuestionTypeUserMapBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.UuidUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class QuestionTypeManagerBusinessImpl extends BaseBusiness implements IQuestionTypeManagerBusiness {

	@Autowired
	private QuestionTypeBusinessImpl questionTypeBusinessImpl;
	@Autowired
	private QuestionTypeUserMapBusinessImpl questionTypeUserMapBusinessImpl;
	@Autowired
	private DepartmentBusinessImpl deptManagerBusinessImpl;
	@Autowired
	private AccessSystemBusinessImpl accessSystemBusinessImpl;

	// 查询该部门是否有对应的问题分类
	@Override
	public Map<String, Object> selectQueTypeByDept(Map<String, Object> deptMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> queMap = new HashMap<String, Object>();
		if (!CommonUtil.isEmpty(deptMap.get("uuid"))) {
			queMap.put("departmentId", deptMap.get("uuid"));

			try {
				// 查询该部门是否有对应的问题分类
				queMap = questionTypeBusinessImpl.selectOne(queMap);
			} catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}

		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getQuestionTypeList(Map<String, Object> dataMap) {
		//begin
		dataMap.put("orderBy", "createTime desc");
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			
			if(!dataMap.containsKey("systemIndex")){//如果没有选择系统，则 查询该公司的所有的系统
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("userId", dataMap.get("userId"));
				userMap.put("status", 1);
				dataMap.remove("userId");
				Map<String, Object> sysMap = accessSystemBusinessImpl.selectList(userMap);//dataMap应该包含管理员的id
				if(sysMap.get("list") != null && !sysMap.get("list").equals("")){//如果有系统
					List<Map<String, Object>> sysList = (List<Map<String, Object>>) sysMap.get("list");
					Map<String,Object> deptParamMap = new HashMap<String, Object>();
					first:for(Map<String, Object> tmap :sysList){//循环该管理员下面的所有系统，获取每个系统下面的部门信息
						if(tmap.get("systemIndex") != null){
							deptParamMap.put("systemIndex", tmap.get("systemIndex"));
							deptParamMap.put("status", "!=-1");
							Map<String, Object> deptMap = deptManagerBusinessImpl.selectList(deptParamMap);//获取该系统下的所有部门信息
							if (!"1".equals(deptMap.get("state").toString())) {
								break;
							}
							List<Map<String, Object>> deptMapList = (List<Map<String, Object>>) deptMap.get("list");//该系统下的所有部门信息
							if (deptMapList != null && deptMapList.size() > 0) {
								for (Map<String, Object> map : deptMapList) {//循环部门
									
									Map<String, Object> newMap = new HashMap<String, Object>();
									newMap.put("departmentId", map.get("uuid"));
									newMap.put("status", "!=-1");
									newMap.put("pageSize", dataMap.get("pageSize"));
									newMap.put("pageIndex", dataMap.get("pageIndex"));
									Map<String, Object> questionTypeMap = questionTypeBusinessImpl.selectList(newMap);
									if ("1".equals(questionTypeMap.get("state").toString())) {
										List<Map<String, Object>> questionTypeList = (List<Map<String, Object>>) questionTypeMap.get("list");
										for (Map<String, Object> questionType : questionTypeList) {
											Map<String, Object> deptParamsMap = new HashMap<String, Object>();
											deptParamsMap.put("uuid", (String) questionType.get("departmentId"));
											Map<String, Object> dept = (Map<String, Object>) deptManagerBusinessImpl.selectOne(deptParamsMap).get("data");
											if (dept != null) {
												questionType.put("deptName", dept.get("name"));
											}
										}
										reMap.put("questionTypeList", questionTypeList);//分类列表
										reMap.put("totalCount",questionTypeMap.get("totalCount"));//分类的总条数
									}else if ("15".equals(questionTypeMap.get("state").toString())){
										reMap.put("questionTypeList", null);//分类列表
										reMap.put("totalCount",0);//分类的总条数
									}
									reMap.put("systemIndex", tmap.get("systemIndex"));//第一个系统的值返回，使下拉框选中
									reMap.put("departmentId", map.get("uuid"));//第一个部门的值返回，使下拉框选中
									reMap.put("departmentList", deptMapList);//返回所有的部门信息，赋给下拉框
									break first;//查询到一个部门下的分类就跳出循环
								}
							} 
							
						}
					}
					reMap.put(KEY_STATE, STATE_ONE);
					reMap.put("systemList", sysList);//返回所有的系统信息，赋给下拉框
					return reMap;
				}else{
					reMap.put(KEY_STATE, STATE_FIFTEEN);
					return reMap;
				}
			
			}
		}catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		
		
			
		try{
			//如果选择部门，则 查询这个部门下的分类
			//查询下框系统的列表
			Map<String, Object> sysMap2 = new HashMap<String, Object>();
			sysMap2.put("userId", dataMap.get("userId"));
			sysMap2.put("status", 1);//查询正常的系统
			dataMap.remove("userId");
			Map<String, Object> resultSysMap = accessSystemBusinessImpl.selectList(sysMap2);
			if(resultSysMap.get("list") != null && !resultSysMap.get("list").equals("")){//如果有系统
				reMap.put("systemList", resultSysMap.get("list"));//返回所有的系统信息，赋给下拉框
			}
			reMap.put("systemIndex", dataMap.get("systemIndex"));//第一个系统的值返回，使下拉框选中
			//查询下框部门的列表
			Map<String, Object> deptMap2 = new HashMap<String, Object>();
			deptMap2.put("systemIndex", dataMap.get("systemIndex"));
			deptMap2.put("status", "!=-1");//查询没有删除的部门
			dataMap.remove("systemIndex");
			Map<String, Object> resultDeptMap = deptManagerBusinessImpl.selectList(deptMap2);//获取该系统下的所有部门信息
			if(resultDeptMap.get("list") != null && !resultDeptMap.get("list").equals("")){//如果有系统
				reMap.put("departmentList", resultDeptMap.get("list"));//返回所有的部门信息，赋给下拉框
			}
			reMap.put("departmentId", dataMap.get("departmentId"));//第一个部门的值返回，使下拉框选中
			Map<String, Object> questionTypeMap = questionTypeBusinessImpl.selectList(dataMap);
			if (STATE_ONE.equals(questionTypeMap.get("state"))) {
				List<Map<String, Object>> typeList = (List<Map<String, Object>>) questionTypeMap.get("list");
				for (Map<String, Object> map : typeList) {
					Map<String, Object> deptParamsMap = new HashMap<String, Object>();
					deptParamsMap.put("uuid", (String) map.get("departmentId"));
					Map<String, Object> deptMap = (Map<String, Object>) deptManagerBusinessImpl.selectOne(deptParamsMap).get("data");
					if (deptMap != null) {
						map.put("deptName", deptMap.get("name"));
					}
				}
				reMap.put("totalCount",questionTypeMap.get("totalCount"));//分类的总条数
				reMap.put("questionTypeList", typeList);//分类列表
				reMap.put(KEY_STATE, STATE_ONE);
				System.out.println(reMap);
			}else{
				reMap.put(KEY_STATE, STATE_FIFTEEN);
				return reMap;
			}
		}catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}
			return reMap;
		}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getQuestionTypeOne(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = questionTypeBusinessImpl.selectOne(dataMap);
			if (STATE_ONE.equals(reMap.get("state"))) {
				Map<String, Object> map = (Map<String, Object>) reMap.get("data");
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("uuid", map.get("departmentId"));
				Map<String, Object> deptMap = (Map<String, Object>) deptManagerBusinessImpl.selectOne(paramsMap)
						.get("data");
				map.put("deptName", deptMap.get("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	@Transactional
	public Map<String, Object> addQuestionType(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		String questionTypeUuid = UuidUtil.generateUUID();
		dataMap.put("uuid", questionTypeUuid);
		dataMap.put("createTime", new Date());
			String userId = (String) dataMap.get("userId");
			dataMap.remove("userId");
			if (!"0".equals(userId)) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("userId", userId);
				paramsMap.put("questionTypeId", questionTypeUuid);
				paramsMap.put("systemIndex", dataMap.get("systemIndex"));
				questionTypeUserMapBusinessImpl.insert(paramsMap);
			} 
			reMap = questionTypeBusinessImpl.insert(dataMap);
		return reMap;
	}

	@Override
	public Map<String, Object> updateQuestionType(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = questionTypeBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}

		return reMap;
	}

	@Override
	@Deprecated
	public Map<String, Object> getQuestionTypeForUserList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = questionTypeUserMapBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	@Deprecated
	public Map<String, Object> getQuestionTypeForUserOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = questionTypeUserMapBusinessImpl.selectOne(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	@Deprecated
	public Map<String, Object> updateQuestionTypeForUser(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = questionTypeUserMapBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}

		return reMap;
	}

	@Override
	@Deprecated
	public Map<String, Object> addQuestionTypeForUser(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = questionTypeUserMapBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> deleteQuestionType(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = questionTypeBusinessImpl.delete(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}

		return reMap;
	}

}
