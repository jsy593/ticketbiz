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
import com.jsy.util.common.TableNameUtil;
import com.jsy.util.common.UuidUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class TicketMoveBusinessImpl extends BaseBusiness {

	public Map<String, Object> selectOne(Map<String, Object> dataMap) throws Exception {
		String systemIndex = dataMap.get("systemIndex").toString();
		dataMap.remove("systemIndex");
		StringBuffer sql = new StringBuffer("select * from " + TableNameUtil.getTableName(TICKET_MOVE, systemIndex));

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
		String systemIndex = dataMap.get("systemIndex").toString();
		dataMap.remove("systemIndex");
		StringBuffer sql = new StringBuffer(" select * from " + TableNameUtil.getTableName(TICKET_MOVE, systemIndex));

		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();

		handleSql(dataMap, sql, params, null);

		List<Map<String, Object>> TICKET_MOVEList = commonDao.selectList(sql.toString(), params);

		if (TICKET_MOVEList != null && TICKET_MOVEList.size() > 0) {
			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("list", TICKET_MOVEList);
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
		String systemIndex = dataMap.get("systemIndex").toString();
		dataMap.remove("systemIndex");
		Integer insertManual = commonDao.insertManual(dataMap, TableNameUtil.getTableName(TICKET_MOVE, systemIndex));
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
		String systemIndex = dataMap.get("systemIndex").toString();
		dataMap.remove("systemIndex");
		Integer updateOne = commonDao.updateOne(dataMap, TableNameUtil.getTableName(TICKET_MOVE, systemIndex));
		if (updateOne == 1) {
			reMap.put(KEY_STATE, STATE_ONE);
		} else {
			reMap.put(KEY_STATE, STATE_FIFTEEN);
		}
		return reMap;
	}
}
