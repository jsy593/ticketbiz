package com.jsy.business.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsy.business.BaseBusiness;
import com.jsy.business.ISystem;
import com.jsy.business.ISystemBusiness;
import com.jsy.business.base.AccessSystemBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;
import com.jsy.business.base.UserRoleBusinessImpl;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.MailUtil;
import com.jsy.util.common.SendMail;
import com.jsy.util.common.UuidUtil;
import com.sun.jmx.snmp.daemon.CommunicationException;

@Service
public class SystemBusinessImpl extends BaseBusiness implements ISystemBusiness {
	@Autowired
	private AccessSystemBusinessImpl systemBusinessImpl;

	@Autowired
	private ISystem systemImpl;
	@Autowired
	private UserBusinessImpl userBusinessImpl;
	
	@Autowired
	private UserRoleBusinessImpl userRoleBusinessImpl;
	/**
	 * 添加系统
	 * @author jsy
	 * @param systemMap
	 * @return
	 */
	
	@Override
	public Map<String, Object> insertSysInfo(Map<String, Object> systemMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> sysMap = new HashMap<String, Object>();
		if (!CommonUtil.isEmpty(systemMap.get("systemName"))) {
			sysMap.put("systemName", systemMap.get("systemName"));
			try {
				Map<String, Object> map = (Map<String, Object>) systemBusinessImpl.selectOne(sysMap).get("data");
				if(CommonUtil.isEmpty(map)){//数据库中不存在该系统名
					String uuid = UuidUtil.generateUUID();
					systemMap.put("uuid", uuid);
					systemMap.put("status", 0);
					systemMap.put("createTime", new Date());
					reMap = systemBusinessImpl.insert(systemMap);
				}else{
					reMap.put(KEY_STATE, STATE_ELEVEN);//数据库中已存在该系统名
				}
			} catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}
		}
		return reMap;
	}
/**
 * 查询系统列表
 * @author jsy
 * @param sysMap
 * @return
 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectSystem(Map<String, Object> sysMap) {
		Map<String,Object> userMap = new HashMap<String,Object>();
		Map<String,Object> roleMap = new HashMap<String,Object>();
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			if(sysMap.get("userId")!= null){
				userMap.put("uuid", sysMap.get("userId"));
				Map<String, Object> uMap = userBusinessImpl.selectOne(userMap);
				roleMap.put("code", "superAdmin");
				Map<String, Object> rMap = userRoleBusinessImpl.selectOne(roleMap);
				Map<String, Object> tuserMap =  (Map<String, Object>) uMap.get("data");
				Map<String, Object> troleMap =  (Map<String, Object>) rMap.get("data");
				if(tuserMap.get("roleId") != troleMap.get("uuid")){
					sysMap.put("status", "!=4");
				}
			}
			reMap = systemBusinessImpl.selectList(sysMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> selectSystemOne(Map<String, Object> sysMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = systemBusinessImpl.selectOne(sysMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	// 审核接入系统
		@SuppressWarnings("unchecked")
		@Override
		public Map<String, Object> checkSystem(Map<String, Object> sysMap) {
			Map<String, Object> reMap = new HashMap<String, Object>();
			String email = null;
			String userId = null;
					try {
						//查询userId
						Map<String, Object> tMap = new HashMap<String, Object>();
						tMap.put("uuid", sysMap.get("uuid"));
						Map<String, Object> systemMap = systemBusinessImpl.selectOne(tMap);
						if(!CommonUtil.isEmpty(systemMap.get("data"))){
							Map<String, Object> resultMap = (Map<String, Object>) systemMap.get("data");
							userId = resultMap.get("userId").toString();
						}
						//根据userId查询用户email
						
						Map<String, Object> userMap = new HashMap<String, Object>();
						userMap.put("uuid",userId);
						Map<String, Object> tempMap = userBusinessImpl.selectOne(userMap);
						if(!CommonUtil.isEmpty(tempMap.get("data"))){
							Map<String, Object> resultMap = (Map<String, Object>) tempMap.get("data");
							email = resultMap.get("email").toString();
						}
						//FIXME
						String appId = UuidUtil.generateUUID();
						String appKey = UuidUtil.generateUUID();
						sysMap.put("appId", appId);
						sysMap.put("appKey", appKey);
						if(!CommonUtil.isEmpty( sysMap.get("status"))){
							String status = sysMap.get("status").toString();
							if("1".equals(status)){
								Long systemIndex = systemImpl.createTableWithSysName(sysMap.get("systemName").toString());
								reMap.put("systemIndex", systemIndex);
								reMap = systemBusinessImpl.update(sysMap);
								if(!CommonUtil.isEmpty(email)){
									//发送审核成功邮件
									MailUtil.send(SendMail.SMTP,SendMail.FROM,email,SendMail.TITLE,
										"<h1>某某某公司,</h1><font>您申请接入的系统已审核成功，您的appId:"+appId+",appKey:"+appKey+"。请点击链接登录。</font><a href='http://http://localhost:8080/ticketview/'>登录</a>",SendMail.USERNAME,SendMail.PASSWORD);
								}
							}else if("3".equals(status)){
								reMap = systemBusinessImpl.update(sysMap);
								if(!CommonUtil.isEmpty(email)){
									//发送审核失败邮件
									MailUtil.send(SendMail.SMTP,SendMail.FROM,email,SendMail.TITLE,
										"<h1>某某某公司,</h1><font>您申请接入的系统审核失败，请点击登录链接重新申请。</font><a href='http://http://localhost:8080/ticketview/'>登录</a>",SendMail.USERNAME,SendMail.PASSWORD);
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

	// 维护接入系统
	@Override
	public Map<String, Object> manageSystem(Map<String, Object> sysMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			systemBusinessImpl.update(sysMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	// 删除接入系统
	@Override
	public Map<String, Object> deleteSystem(Map<String, Object> sysMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			systemBusinessImpl.delete(sysMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> updateSystemStatus(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = systemBusinessImpl.update(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}
	@Override
	public Map<String, Object> selectSystemIndex(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			reMap = systemBusinessImpl.selectOne(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

}
