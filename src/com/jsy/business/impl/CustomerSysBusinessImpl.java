package com.jsy.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsy.business.BaseBusiness;
import com.jsy.business.ICustomerSysBusiness;
import com.jsy.business.ITicketManagerBusiness;
import com.jsy.business.IUserManagerBusiness;
import com.jsy.business.base.AccessSystemBusinessImpl;
import com.jsy.business.base.TicketBusinessImpl;

/**
 * 
 * @author yichuan
 *
 */
@Service
public class CustomerSysBusinessImpl extends BaseBusiness implements ICustomerSysBusiness {

	@Autowired
	IUserManagerBusiness iUserManagerBusiness;
	@Autowired
	private TicketBusinessImpl ticketBusinessImpl;
	@Autowired
	private AccessSystemBusinessImpl accessSystemBusinessImpl;

	@Override
	public Map<String, Object> getCustomerTicketList(Map<String, Object> dataMap) {

		Map<String, Object> sysParamsMap = new HashMap<String, Object>();
		sysParamsMap.put("appId", dataMap.get("appId"));
		sysParamsMap.put("appKey", dataMap.get("appKey"));
		dataMap.remove("appId");
		dataMap.remove("appKey");

		Map<String, Object> reMap = new HashMap<String, Object>();

		Map<String, Object> accSysMap;
		try {
			accSysMap = (Map<String, Object>) accessSystemBusinessImpl.selectOne(sysParamsMap).get("data");

			if (accSysMap == null) {
				reMap.put(KEY_STATE, STATE_SIX);
			} else {
				dataMap.put("systemIndex", accSysMap.get("systemIndex"));
				reMap = ticketBusinessImpl.selectList(dataMap);
				reMap.put("systemIndex", accSysMap.get("systemIndex"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reMap;
	}

	@Override
	@Deprecated
	public Map<String, Object> selectTicketListByMap(Map<String, Object> dataMap, String tableName) {
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

	@Override
	public Map<String, Object> closeTicket(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		dataMap.put("status", 3);
		try {
			reMap = ticketBusinessImpl.update(dataMap);
		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;

	}

	@Override
	public Map<String, Object> addTicket(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = ticketBusinessImpl.insert(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

}
