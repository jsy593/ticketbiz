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
import com.jsy.util.common.UuidUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class QuestionTypeBusinessImpl extends BaseBusiness {

	public Map<String, Object> selectOne(Map<String, Object> dataMap) throws Exception {
		StringBuffer sql = new StringBuffer("select * from " + QUESTION_TYPE);

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
		dataMap.put("orderBy", "createTime desc");
		
		StringBuffer sql = new StringBuffer(" select * from " + QUESTION_TYPE);

		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		if(dataMap.get("content")==null || dataMap.get("content").equals("")){
			dataMap.remove("content");
			handleSql(dataMap, sql, params, null);
		}else{
			dataMap.put("typeName",dataMap.get("content"));
			dataMap.remove("content");
			handleSql(dataMap, sql, params, "typeName");
		}

		List<Map<String, Object>> QUESTION_TYPEList = commonDao.selectList(sql.toString(), params);

		if (QUESTION_TYPEList != null && QUESTION_TYPEList.size() > 0) {
			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("list", QUESTION_TYPEList);
			reMap.put("totalCount", commonDao.selectTotalCount(sql.toString(), params));
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
		Integer insertManual = commonDao.insertManual(dataMap, QUESTION_TYPE);
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
		Integer updateOne = commonDao.updateOne(dataMap, QUESTION_TYPE);
		if (updateOne == 1) {
			reMap.put(KEY_STATE, STATE_ONE);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}
	
	public Map<String, Object> delete(Map<String, Object> dataMap) throws Exception {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Integer updateOne = commonDao.deleteOne(dataMap, QUESTION_TYPE);
		if (updateOne == 1) {
			reMap.put(KEY_STATE, STATE_ONE);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}
}
