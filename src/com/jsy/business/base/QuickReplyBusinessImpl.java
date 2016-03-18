package com.jsy.business.base;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsy.business.BaseBusiness;
import com.jsy.business.ISystem;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.UuidUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class QuickReplyBusinessImpl extends BaseBusiness {

	public Map<String, Object> selectOne(Map<String, Object> dataMap) throws Exception {
		StringBuffer sql = new StringBuffer("select * from " + QUICK_REQLY);

		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		handleSql(dataMap, sql, params, null);
		Map<String, Object> map = commonDao.selectForMap(sql.toString(), params.toArray());
		if (map != null) {
			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("data", map);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}

	public Map<String, Object> selectList(Map<String, Object> dataMap) throws Exception {
		StringBuffer sql = new StringBuffer(" select * from " + QUICK_REQLY);
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> userList = null;
			if(CommonUtil.isEmpty(dataMap.get("status"))){
				dataMap.remove("status");
			}
		Object content = dataMap.get("content");
		if (!CommonUtil.isEmpty(content)) {//有搜索条件
			dataMap.put("title", content);
			dataMap.remove("content");
			handleSql(dataMap, sql, params, "title");
		} else {//无搜索条件
			dataMap.remove("content");
			handleSql(dataMap, sql, params, null);
		}
		userList = commonDao.selectList(sql.toString(), params);

		if (userList != null && userList.size() > 0) {
			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("list", userList);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}

	public Map<String, Object> insert(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		dataMap.put("uuid", UuidUtil.generateUUID());
		dataMap.put("createTime", new Date());
		dataMap.put("status", 1);
			Integer insertManual = commonDao.insertManual(dataMap, QUICK_REQLY);
			if (insertManual == 1) {
				reMap.put(KEY_STATE, STATE_ONE);
			} else {
				reMap.put(KEY_STATE, STATE_FIFTEEN);
			}
		return reMap;
	}

	public Map<String, Object> update(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		dataMap.put("pk", "uuid");
		Integer updateOne = commonDao.updateOne(dataMap, QUICK_REQLY);
		if (updateOne == 1) {
			reMap.put(KEY_STATE, STATE_ONE);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
			}
		return reMap;
	}

	public Map<String, Object> delete(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		dataMap.put("pk", "uuid");
		Integer updateOne = commonDao.deleteOne(dataMap, QUICK_REQLY);
		if (updateOne == 1) {
			reMap.put(KEY_STATE, STATE_ONE);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}
}
