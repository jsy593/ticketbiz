package com.jsy.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsy.business.BaseBusiness;
import com.jsy.business.IQuickReplyBusiness;
import com.jsy.business.base.QuickReplyBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;
import com.jsy.util.common.CommonUtil;

@Service
public class QReplyManagerBusinessImpl extends BaseBusiness implements IQuickReplyBusiness {

	@Autowired
	private  QuickReplyBusinessImpl quickReplyBusinessImpl;
	@Autowired
	private UserBusinessImpl userBusinessImpl;
	@Override
	public Map<String, Object> addQuickReply(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = quickReplyBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getQuickReplyList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = quickReplyBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getQuickReplyOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("uuid",dataMap.get("userId"));
		try {
			reMap = quickReplyBusinessImpl.selectOne(dataMap);
			//查询该快捷回复username
			Map<String, Object> tempMap = userBusinessImpl.selectOne(userMap);
			if("1".equals(tempMap.get(KEY_STATE).toString())){
				Map<String, Object> temp = (Map<String, Object>) tempMap.get("data");
				Object username = temp.get("username");
				Map<String, Object> quickMap = (Map<String, Object>) reMap.get("data");
				quickMap.put("username", username);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> updateQuickReply(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = quickReplyBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}
	@Override
	public Map<String, Object> deleteQuickReply(Map<String, Object> dataMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = quickReplyBusinessImpl.delete(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

}
