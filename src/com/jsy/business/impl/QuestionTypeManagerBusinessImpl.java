package com.jsy.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsy.business.BaseBusiness;
import com.jsy.business.IQuestionTypeManagerBusiness;
import com.jsy.business.base.DepartmentBusinessImpl;
import com.jsy.business.base.QuestionTypeBusinessImpl;
import com.jsy.business.base.QuestionTypeUserMapBusinessImpl;
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

		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = questionTypeBusinessImpl.selectList(dataMap);
			if (STATE_ONE.equals(reMap.get("state"))) {
				List<Map<String, Object>> typeList = (List<Map<String, Object>>) reMap.get("list");
				for (Map<String, Object> map : typeList) {
					Map<String, Object> deptParamsMap = new HashMap<String, Object>();
					deptParamsMap.put("uuid", (String) map.get("departmentId"));
					Map<String, Object> deptMap = (Map<String, Object>) deptManagerBusinessImpl.selectOne(deptParamsMap)
							.get("data");
					if (deptMap != null) {
						map.put("deptName", deptMap.get("name"));
					}
				}
			}
		} catch (Exception e) {
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

}
