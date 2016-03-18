package com.jsy.business.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jsy.business.BaseBusiness;
import com.jsy.util.common.CommonUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class AccessSystemBusinessImpl extends BaseBusiness {

	public Map<String, Object> selectOne(Map<String, Object> dataMap) throws Exception {
		StringBuffer sql = new StringBuffer("select * from " + ACCESS_SYSTEM);

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
		StringBuffer sql = new StringBuffer(" select * from " + ACCESS_SYSTEM);
		List<Map<String, Object>> ACCESS_SYSTEMList = null;
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		
		if (CommonUtil.isEmpty(dataMap.get("status"))) {
			dataMap.remove("status");
		}
		if (!CommonUtil.isEmpty(dataMap.get("content"))) {
			dataMap.put("systemName", dataMap.get("content"));
			dataMap.remove("content");
			handleSql(dataMap, sql, params, "systemName");
		} else {
			dataMap.remove("content");
			handleSql(dataMap, sql, params, null);
		}
		ACCESS_SYSTEMList = commonDao.selectList(sql.toString(), params);

		if (ACCESS_SYSTEMList != null && ACCESS_SYSTEMList.size() > 0) {
			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("list", ACCESS_SYSTEMList);
			reMap.put("totalCount", commonDao.selectTotalCount(sql.toString(), params));
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}

	public Map<String, Object> insert(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Integer insertManual = commonDao.insertManual(dataMap, ACCESS_SYSTEM);
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
		Integer updateOne = commonDao.updateOne(dataMap, ACCESS_SYSTEM);
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
		Integer updateOne = commonDao.deleteOne(dataMap, ACCESS_SYSTEM);
		if (updateOne == 1) {
			reMap.put(KEY_STATE, STATE_ONE);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}
}
