package com.jsy.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsy.business.BaseBusiness;
import com.jsy.business.IKnowledgeBaseBusiness;
import com.jsy.business.base.KnowledgeBusinessImpl;
import com.jsy.business.base.QuestionTypeBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;

/**
 * 
 * @author yichuan
 *
 */
@Service
public class KnowledgeBaseBusinessImpl extends BaseBusiness implements IKnowledgeBaseBusiness {

	@Autowired
	private  KnowledgeBusinessImpl knowledgeBusinessImpl;
	
	@Autowired
	private UserBusinessImpl userBusinessImpl;
	@Autowired
	private QuestionTypeBusinessImpl questionTypeBusinessImpl;
	
	@Override
	public Map<String, Object> addKnowledgeBase(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = knowledgeBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getKnowledgeBaseList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = knowledgeBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}
	@Deprecated
	public Map<String, Object> selectKnowledgeBaseList(Map<String, Object> dataMap, String tableName) {
		StringBuffer sql = new StringBuffer(" select * from " + tableName);

		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();

		try {

			handleSql(dataMap, sql, params, null);

			List<Map<String, Object>> selectList = commonDao.selectList(sql.toString(), params);
			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("list", selectList);

		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_TWO);
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getKnowledgeBaseOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = knowledgeBusinessImpl.selectOne(dataMap);
			if("1".equals(reMap.get(KEY_STATE).toString())){
				
				Map<String, Object> tempMap = new HashMap<String, Object>();
				Map<String, Object> map =(Map<String, Object>) reMap.get("data");
				tempMap.put("uuid",  map.get("userId"));
				//查询提交人名字
				tempMap= userBusinessImpl.selectOne(tempMap);
				if("1".equals(tempMap.get(KEY_STATE).toString())){
					Map<String, Object> temp = (Map<String, Object>)tempMap.get("data");
					map.put("username",temp.get("username"));
				}
				Map<String, Object> temp =new HashMap<String, Object>();
				temp.put("uuid",  map.get("questionTypeId"));
				//查询问题分类
				temp = questionTypeBusinessImpl.selectOne(temp);
				if("1".equals(temp.get(KEY_STATE).toString())){
					Map<String, Object> data = (Map<String, Object>)temp.get("data");
					map.put("typeName",data.get("typeName"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> updateKnowledgeBase(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = knowledgeBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

}
