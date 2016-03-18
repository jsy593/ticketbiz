package com.jsy.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsy.business.BaseBusiness;
import com.jsy.business.ISystem;
import com.jsy.business.IUserManagerBusiness;
import com.jsy.business.base.AccessSystemBusinessImpl;
import com.jsy.business.base.UserBusinessImpl;
import com.jsy.business.base.UserRoleBusinessImpl;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.MD5Util;
import com.jsy.util.common.MailUtil;
import com.jsy.util.common.SendMail;
import com.jsy.util.common.SendSmsUtils;
import com.jsy.util.common.Sms;
import com.jsy.util.common.StringUtil;
import com.jsy.util.common.UuidUtil;

/**
 * 
 * @author zhangyu
 *
 */
@Service
public class UserManagerBusinessImpl extends BaseBusiness implements IUserManagerBusiness {

	@Autowired
	private ISystem systemImpl;

	@Autowired
	private AccessSystemBusinessImpl accessSystemBusinessImpl;

	@Autowired
	private UserRoleBusinessImpl userRoleBusinessImpl;

	@Autowired
	private UserBusinessImpl userBusinessImpl;

	// @Autowired
	// private UserBusinessImpl userBusinessImpl;
	// @Autowired
	// private UserRoleBusinessImpl userRoleBusinessImpl;
	//
	// /**
	// * 查询用户信息(包含所属角色，用户信息)
	// * @param dataMap
	// * uuid=664eb55058f511e5b3218c89a5e173c7
	// * @return
	// */
	// public Map<String, Object> getUserInfo(Map<String, Object> dataMap) {
	// Map<String, Object> resultMap = new HashMap<>();
	// Map<String, Object> roleDataMap = new HashMap<>();
	//
	// dataMap.put("uuid", "664eb55058f511e5b3218c89a5e173c7");
	// Map<String, Object> selectUserOne =
	// userBusinessImpl.selectUserOne(dataMap);
	//
	// if(BaseBusiness.STATE_ONE.equals(selectUserOne.get("state").toString())){
	// roleDataMap.put("uuid", selectUserOne.get("roleId"));
	// Map<String, Object> userRole =
	// userRoleBusinessImpl.selectUserOne(roleDataMap);
	// selectUserOne.put("roleName", userRole.get("roleName"));
	// }
	//
	// resultMap.put("state", resultMap);
	// resultMap.put("data", selectUserOne);
	//
	// for (String key : resultMap.keySet()) {
	// System.out.println(key + " " +resultMap.get(key));
	// }
	// return resultMap;
	// }

	// 注册
	@Override
	public Map<String, Object> insertUserInfo(Map<String, Object> user) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		if (CommonUtil.isEmpty(user.get("username"))) {
			reMap.put(KEY_STATE, STATE_FOUR);
			return reMap;
		}
		if (CommonUtil.isEmpty(user.get("password"))) {
			reMap.put(KEY_STATE, STATE_NINE);
			return reMap;
		}

		Map<String, Object> usMap = new HashMap<String, Object>();
		usMap.put("username", user.get("username"));
		Map<String, Object> userMap = this.selectUserList(usMap);

		if (!CommonUtil.isEmpty(userMap)) {
			if (userMap.get(KEY_STATE).equals(STATE_ONE)) {
				reMap.put(KEY_STATE, STATE_EIGHT);
				return reMap;
			} else if (userMap.get(KEY_STATE).equals(STATE_ZERO)) {
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			} else {
				try {
					Map<String, Object> temp = selectRoleIdByCode(CODE_SYSTEM_ADMIN);
					user.put("roleId", temp.get("roleId"));
					reMap = userBusinessImpl.insert(user);
				} catch (Exception e) {
					e.printStackTrace();
					reMap.put(KEY_STATE, STATE_ZERO);
					return reMap;
				}
			}
		} else {
			reMap.put(KEY_STATE, STATE_TWO);
		}
		return reMap;
	}

	public Map<String, Object> insertSysInfo(Map<String, Object> systemMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (systemMap.get("systemName") != null) {
			Map<String, Object> sysMap = selectSysOne(systemMap);
			if (sysMap == null) {
				String uuid = UuidUtil.generateUUID();
				String appId = UuidUtil.generateUUID();
				String appKey = UuidUtil.generateUUID();
				systemMap.put("uuid", uuid);
				systemMap.put("appId", appId);
				systemMap.put("appKey", appKey);
				systemMap.put("uuid", uuid);
				systemMap.put("status", 0);
				systemMap.put("createTime", new Date());
				try {
					commonDao.insertManual(systemMap, ACCESS_SYSTEM);
					reMap.put(KEY_STATE, STATE_ONE);
				} catch (Exception e) {
					log.error("添加数据失败", e);
					reMap.put(KEY_STATE, STATE_TWO);
				}
			} else {
				reMap.put(KEY_STATE, STATE_ELEVEN);
			}
		} else {
			reMap.put(KEY_STATE, STATE_TEN);

			reMap.put(KEY_STATE, STATE_TWO);
		}
		return reMap;
	}

	@Override
	@Deprecated
	public Map<String, Object> insertDeptInfo(Map<String, Object> deptMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> dptMap = new HashMap<String, Object>();
		if (CommonUtil.isEmpty(deptMap.get("name"))) {
			resultMap.put(KEY_STATE, STATE_TEN);
		} else {
			dptMap.put("name", deptMap.get("name"));
			if (setDepartmentOne(dptMap) != null) {
				resultMap.put(KEY_STATE, STATE_ELEVEN);
			} else {
				try {
					commonDao.insertManual(dptMap, DEPARTMENT);
					resultMap.put(KEY_STATE, STATE_ONE);
				} catch (Exception e) {
					resultMap.put(KEY_STATE, STATE_TWO);
					log.error("添加数据失败", e);
				}
			}
		}
		return resultMap;
	}

	// 维护部门
	/*
	 * @Override public Map<String, Object> manageDept(Map<String, Object>
	 * deptMap) { Map<String, Object> reMap = new HashMap<String, Object>();
	 * Map<String, Object> queMap = new HashMap<String, Object>(); if
	 * (deptMap.get("name") != null && deptMap.get("status") != null &&
	 * deptMap.get("uuid") != null) { queMap.put("departmentId",
	 * deptMap.get("uuid")); if (selectQuestionTypeOne(queMap) != null &&
	 * deptMap.get("status").equals(0)) { deptMap.put("pk", "uuid"); try {
	 * commonDao.updateOne(deptMap, DEPARTMENT); reMap.put(KEY_STATE,
	 * STATE_THIRTEEN); } catch (Exception e) { log.error("添加数据失败", e);
	 * reMap.put(KEY_STATE, STATE_TWO); } } else { try {
	 * commonDao.updateOne(deptMap, DEPARTMENT); reMap.put(KEY_STATE,
	 * STATE_ONE); } catch (Exception e) { log.error("添加数据失败", e);
	 * reMap.put(KEY_STATE, STATE_TWO); } } }
	 * 
	 * return reMap; }
	 */

	/*
	 * public Map<String, Object> checkDeptAndClassify(Map<String, Object>
	 * deptMap) { Map<String, Object> reMap = new HashMap<String, Object>();
	 * Map<String, Object> queMap = new HashMap<String, Object>(); if
	 * (deptMap.get("uuid") != null) { queMap.put("departmentId",
	 * deptMap.get("uuid")); if (selectQuestionTypeOne(queMap) != null &&
	 * deptMap.get("status").equals(0)) { reMap.put(KEY_STATE, STATE_TEWELVE); }
	 * else { reMap.put(KEY_STATE, STATE_ONE); } } else { reMap.put(KEY_STATE,
	 * STATE_TEN); }
	 * 
	 * return reMap;
	 * 
	 * }
	 */

	@Override
	public Map<String, Object> selectSysOne(Map<String, Object> dataMap) {
		StringBuffer sql = new StringBuffer(
				"select uuid, systemName, appId, appKey, userId, status, remark, createTime from t_access_system");

		Map<String, Object> reMap = null;
		List<Object> params = new ArrayList<Object>();
		try {
			handleSql(dataMap, sql, params, null);
			reMap = commonDao.selectForMap(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reMap;
	}

	@Override
	public Map<String, Object> selectUserOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = userBusinessImpl.selectOne(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> selectUserList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = userBusinessImpl.selectList(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;

	}

	@Override
	public Map<String, Object> selectRoleOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {
			reMap = userRoleBusinessImpl.selectOne(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	public List<Map<String, Object>> selectRoleList(Map<String, Object> dataMap) {
		StringBuffer sql = new StringBuffer(" select * from  " + USER);

		List<Map<String, Object>> reList = null;
		List<Object> params = new ArrayList<Object>();
		try {
			handleSql(dataMap, sql, params, null);
			reList = commonDao.selectList(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}

	// 查询admin
	public Map<String, Object> selectAdminList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> rMap = new HashMap<String, Object>();
		rMap.put("code", CODE_SYSTEM_ADMIN);
		try {
			Map<String, Object> roleMap = userRoleBusinessImpl.selectOne(rMap);
			if (STATE_ONE.equals(roleMap.get(KEY_STATE))) {
				@SuppressWarnings("unchecked")
				Map<String, Object> resultMap = (Map<String, Object>) roleMap.get("data");
				Object roleId = resultMap.get("uuid");
				dataMap.put("roleId", roleId);
				reMap = userBusinessImpl.selectList(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	// 查询admin
	public Map<String, Object> selectDeptAdminList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> rMap = new HashMap<String, Object>();
		rMap.put("code", CODE_DEPARTMENT_ADMIN);
		try {
			Map<String, Object> roleMap = userRoleBusinessImpl.selectOne(rMap);
			if (STATE_ONE.equals(roleMap.get(KEY_STATE))) {
				@SuppressWarnings("unchecked")
				Map<String, Object> resultMap = (Map<String, Object>) roleMap.get("data");
				Object roleId = resultMap.get("uuid");
				dataMap.put("roleId", roleId);
				reMap = userBusinessImpl.selectList(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	// 查询admin
	@Override
	public Map<String, Object> selectStaffList(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> rMap = new HashMap<String, Object>();
		rMap.put("code", CODE_STAFF);
		try {
			Map<String, Object> roleMap = userRoleBusinessImpl.selectOne(rMap);
			if (STATE_ONE.equals(roleMap.get(KEY_STATE))) {
				@SuppressWarnings("unchecked")
				Map<String, Object> resultMap = (Map<String, Object>) roleMap.get("data");
				Object roleId = resultMap.get("uuid");
				dataMap.put("roleId", roleId);
				reMap = userBusinessImpl.selectList(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> login(Map<String, Object> map) {
		// 0 系统错误 1 成功 4 用户名不存在 5 密码错误 6 账号禁止登陆 7 账号异常
		String userName = map.get("username").toString();
		String password = MD5Util.string2MD5(map.get("password").toString());
		
		Map<String, Object> usernameMap = new HashMap<String, Object>();
		usernameMap.put("username", userName);
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("username", userName);
		paramsMap.put("password", password);

		// 单个用户
		Map<String, Object> userMap = this.selectUserList(usernameMap);
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {

			if (userMap != null) {
				String state = userMap.get("state").toString();

				if ("1".equals(state)) {
					// 用户存在
					List<Map<String, Object>> userList = (List<Map<String, Object>>) userMap.get("list");
					if (userList != null && userList.size() > 0 && userList.size() < 2) {
						Map<String, Object> userMapOne = (Map<String, Object>) this.selectUserOne(paramsMap).get("data");

						if (userMapOne != null) {

							if (userMapOne.containsKey("status")) {
								if ("1".equals(userMapOne.get("status").toString())) {

									// 角色
									Map<String, Object> roleParamsMap = new HashMap<String, Object>();
									roleParamsMap.put("uuid", userMapOne.get("roleId"));
									Map<String, Object> roleMap = (Map<String, Object>) this
											.selectRoleOne(roleParamsMap).get("data");
									if (CODE_SUPER_ADMIN.equals(roleMap.get("code"))
											|| CODE_SYSTEM_ADMIN.equals(roleMap.get("code"))) {
										reMap.put("state", STATE_ONE);
										reMap.put("userInfo", userMapOne);
										reMap.put("roleInfo", roleMap);
										return reMap;
									}

									// 系统
									Map<String, Object> sysParamsMap = new HashMap<String, Object>();
									sysParamsMap.put("systemIndex", userMapOne.get("systemIndex"));
									Map<String, Object> sysMap = accessSystemBusinessImpl.selectList(sysParamsMap);

									if (null != sysMap && STATE_ONE.equals(sysMap.get("state"))) {
									} else {
										reMap.put("state", STATE_SIX);
										return reMap;
									}

									// 部门
									Map<String, Object> departmentParamsMap = new HashMap<String, Object>();
									departmentParamsMap.put("uuid", userMapOne.get("departmentId"));
									Map<String, Object> departmentMap = this.setDepartmentOne(departmentParamsMap);

									if (departmentMap != null && "1".equals(departmentMap.get("status").toString())) {
										reMap.put("state", STATE_ONE);
										reMap.put("userInfo", userMapOne);
										reMap.put("roleInfo", roleMap);
										reMap.put("departmentInfo", departmentMap);
										reMap.put("sysInfo", sysMap);
									} else {
										reMap.put("state", STATE_SIX);
									}

								} else {
									reMap.put("state", STATE_SIX);
								}
							}
						} else {
							reMap.put("state", STATE_FIVE);
						}
					} else if (userList != null && userList.size() > 1) {
						reMap.put("state", STATE_SEVEN);
					} else {
						reMap.put("state", STATE_FOUR);
					}
				} else {
					reMap.put("state", STATE_FOUR);
				}
			}
		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> selectAccessSystemOne(Map<String, Object> dataMap) {
		StringBuffer sql = new StringBuffer(" select * from " + ACCESS_SYSTEM);

		Map<String, Object> reMap = null;
		List<Object> params = new ArrayList<Object>();
		try {

			handleSql(dataMap, sql, params, null);

			reMap = commonDao.selectForMap(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getAccessSysStatus(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		String appId = dataMap.get("appId").toString();
		String appKey = dataMap.get("appKey").toString();

		if (StringUtil.isNull(appId) || StringUtil.isNull(appKey)) {
			reMap.put(KEY_STATE, STATE_SIX);
			return reMap;
		}

		Map<String, Object> sysParamsMap = new HashMap<String, Object>();
		Map<String, Object> sysMap = new HashMap<String, Object>();
		sysParamsMap.put("appId", appId);
		sysParamsMap.put("appKey", appKey);

		sysMap = this.selectAccessSystemOne(sysParamsMap);
		if (sysMap == null || !"1".equals(sysMap.get("status").toString())) {
			reMap.put(KEY_STATE, STATE_SIX);
			return reMap;
		}

		reMap.put(KEY_STATE, STATE_ONE);
		reMap.put("accessSys", sysMap);
		return reMap;
	}

	@Override
	public Map<String, Object> updateUserOne(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		// 账户status为正常状态
		if (!CommonUtil.isEmpty(dataMap.get("uuid")) && "1".equals(dataMap.get("status").toString())) {
			try {
				dataMap.remove("status");
				reMap = userBusinessImpl.update(dataMap);
			} catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}
		} else {// 部门管理员异常
			reMap.put(KEY_STATE, STATE_SEVEN);
		}
		return reMap;
	}

	@Override
	public Map<String, Object> setDepartmentOne(Map<String, Object> dataMap) {
		StringBuffer sql = new StringBuffer(" select * from t_department ");

		Map<String, Object> reMap = null;
		List<Object> params = new ArrayList<Object>();
		try {

			handleSql(dataMap, sql, params, null);

			reMap = commonDao.selectForMap(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reMap;
	}

	public Map<String, Object> selectQuestionTypeOne(Map<String, Object> dataMap) {
		StringBuffer sql = new StringBuffer(" select * from " + QUESTION_TYPE);

		Map<String, Object> reMap = null;
		List<Object> params = new ArrayList<Object>();
		try {

			handleSql(dataMap, sql, params, null);

			reMap = commonDao.selectForMap(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reMap;
	}

	// 审核管理员
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> checkUser(Map<String, Object> userMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (userMap.get("status") != null && userMap.get("uuid") != null) {
			try {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("uuid", userMap.get("uuid"));
				Map<String, Object> map = userBusinessImpl.selectOne(tempMap);
				
				reMap = userBusinessImpl.update(userMap);//审核管理员
				
				//查询该管理员是不是第一次接受审核
				if(!CommonUtil.isEmpty(map.get("data"))){
					Map<String, Object> temp = (Map<String, Object>) map.get("data");
					String status = temp.get("status").toString();
					String email = temp.get("email").toString();
					//第一次审核
					if("0".equals(status)){
					//判断审核是否通过
						Map<String, Object> resultMap = userBusinessImpl.selectOne(tempMap);	
						if(!CommonUtil.isEmpty(resultMap.get("data"))){
							Map<String, Object> result = (Map<String, Object>) resultMap.get("data");
							String resultStatus = result.get("status").toString();
							if ("1".equals(resultStatus)) {
								// 发送审核成功邮件
								MailUtil.send(SendMail.SMTP, SendMail.FROM, email, SendMail.TITLE,
										"<h1>百居易电子商务有限公司,</h1><font>您的账号已审核成功，请点击登录链接。</font><a href='http://http://localhost:8080/jsyticketview/'>登录</a>",
										SendMail.USERNAME, SendMail.PASSWORD);
							} else if ("2".equals(resultStatus)) {
								// 发送审核失败邮件
								MailUtil.send(SendMail.SMTP, SendMail.FROM, email, SendMail.TITLE,
										"<h1>百居易电子商务有限公司,</h1><font>对不起您的账号审核失败了，请点击链接重新注册。</font><a href='http://http://localhost:8080/jsyticketview/toRegist'>注册</a>",
										SendMail.USERNAME, SendMail.PASSWORD);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}
		}

		return reMap;
	}

	// 审核接入系统
	@Override
	public Map<String, Object> checkSystem(Map<String, Object> sysMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (sysMap.get("systemName") != null && sysMap.get("status") != null && sysMap.get("appId") != null) {
			try {
				sysMap.put("pk", "appId");
				commonDao.updateOne(sysMap, ACCESS_SYSTEM);
				systemImpl.createTableWithSysName(sysMap.get("systemName").toString());
				reMap.put(KEY_STATE, STATE_ONE);
			} catch (Exception e) {
				log.error("添加数据失败", e);
				reMap.put(KEY_STATE, STATE_TWO);
			}
		}

		return reMap;
	}

	// 维护接入系统
	@Override
	public Map<String, Object> manageSys(Map<String, Object> sysMap) {

		Map<String, Object> reMap = new HashMap<String, Object>();
		if (sysMap.get("systemName") != null && sysMap.get("status") != null && sysMap.get("appId") != null) {
			try {
				sysMap.put("pk", "appId");
				commonDao.updateOne(sysMap, ACCESS_SYSTEM);
				reMap.put(KEY_STATE, STATE_ONE);
			} catch (Exception e) {
				log.error("添加数据失败", e);
				reMap.put(KEY_STATE, STATE_TWO);
			}
		}

		return reMap;
	}

	@Override
	public Map<String, Object> addDeptAdminForDept(Map<String, Object> deptMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Integer status = Integer.parseInt(deptMap.get("status").toString());
		if (!CommonUtil.isEmpty(deptMap.get("uuid")) && status == 1) {

		}

		return reMap;
	}

	@Override
	public Map<String, Object> addUser(Map<String, Object> userMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> roleMap = new HashMap<String, Object>();
		Object username = userMap.get("username");

		try {
			if (username != null) {
				Map<String, Object> usMap = new HashMap<String, Object>();
				usMap.put("username", username);
				Map<String, Object> useMap = this.selectUserList(usMap);

				// 判断添加用户角色
				if (userMap.get("code") != null) {
					roleMap.put("code", userMap.get("code"));
					roleMap = (Map<String, Object>) userRoleBusinessImpl.selectOne(roleMap).get("data");
					if (roleMap != null) {
						userMap.put("roleId", roleMap.get("uuid"));
						userMap.remove("code");
					} else {
						resultMap.put(KEY_STATE, STATE_SIXTEEN);// 角色不存在
						return resultMap;
					}
				}

				if (useMap != null) {
					if (useMap.get(KEY_STATE).equals(STATE_ONE)) {// 该用户存在
						resultMap.put(KEY_STATE, STATE_EIGHT);
					} else {
						//加密
						String password = MD5Util.string2MD5(userMap.get("password").toString());
						userMap.put("password", password);
						
						String uuid = UuidUtil.generateUUID();
						userMap.put("uuid", uuid);
						userMap.put("status", 1);
						userMap.put("createtime", new Date());
						userMap.put("workerNo", systemImpl.getNextKey("workerNo"));
						commonDao.insertManual(userMap, USER);
						resultMap.put(KEY_STATE, STATE_ONE);

					}
				}

			} else {
				resultMap.put(KEY_STATE, STATE_TEN);
			}
		} catch (Exception e) {
			log.error("添加数据失败", e);
			resultMap.put(KEY_STATE, STATE_TWO);
		}
		return resultMap;
	}

	// 将status置为删除状态
	@Override
	public Map<String, Object> delDeptAdmin(Map<String, Object> userMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (!CommonUtil.isEmpty(userMap.get("uuid"))) {
			userMap.put("status", 2);
			try {
				reMap = userBusinessImpl.update(userMap);
			} catch (Exception e) {
				e.printStackTrace();
				reMap.put(KEY_STATE, STATE_ZERO);
				return reMap;
			}

		}
		return reMap;
	}

	@Override
	@Transactional
	public Map<String, Object> becomeDeptAdmin(Map<String, Object> userMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (userMap.get("uuid") != null) {
			userMap.put("status", 1);

			StringBuffer sql = new StringBuffer(" select uuid from " + USER);
			List<Object> params = new ArrayList<Object>();
			try {
				// 得到部门管理员的roleId
				Map<String, Object> deptMap = new HashMap<String, Object>();
				deptMap = selectRoleIdByCode(CODE_DEPARTMENT_ADMIN);
				Object deptRoleId = deptMap.get("roleId");

				// 得到普通职员的roleId
				Map<String, Object> staffMap = new HashMap<String, Object>();
				staffMap = selectRoleIdByCode(CODE_STAFF);
				Object staffRoleId = staffMap.get("roleId");

				// 查询当前部门的管理者
				Map<String, Object> handleMap = new HashMap<String, Object>();
				handleMap.put("roleId", deptRoleId);// roleId为部门管理员的id
				handleMap.put("departmentId", userMap.get("departmentId"));
				handleMap.put("status", 1);
				handleSql(handleMap, sql, params, null);

				Map<String, Object> deptAdminMap = new HashMap<String, Object>();
				deptAdminMap = commonDao.selectForMap(sql.toString(), params.toArray());
				// 得到当前部门管理者的uuid
				String uuid = deptAdminMap.get("uuid").toString();
				handleMap.clear();
				// 将当前部门的管理者降为普通职员
				handleMap.put("roleId", staffRoleId);
				handleMap.put("uuid", uuid);
				handleMap.put("pk", "uuid");

				commonDao.updateOne(handleMap, USER);
				// 将当前职员或者删除了的部门管理者变成部门管理者
				userMap.put("pk", "uuid");
				userMap.put("status", 1);
				userMap.put("roleId", deptRoleId);
				commonDao.updateOne(userMap, USER);
			} catch (Exception e) {
				resultMap.put(KEY_STATE, STATE_TWO);
				e.printStackTrace();
			}
			resultMap.put(KEY_STATE, STATE_ONE);

		}
		return resultMap;
	}

	@Override
	public Map<String, Object> selectRoleIdByCode(String code) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		roleMap.put("code", code);

		StringBuffer Sql = new StringBuffer(" select uuid from " + USER_ROLE);
		List<Object> Params = new ArrayList<Object>();
		Map<String, Object> roleIdMap = null;

		try {
			handleSql(roleMap, Sql, Params, null);
			roleIdMap = commonDao.selectForMap(Sql.toString(), Params.toArray());
			resultMap.put("roleId", roleIdMap.get("uuid"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> selectUserByQuestionType(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();

		try {

			// 通过问题分类id 查询 部门 id
			Map<String, Object> questionParamsMap = new HashMap<String, Object>();
			questionParamsMap.put("uuid", dataMap.get("questionTypeId"));

			StringBuffer questionSql = new StringBuffer(" select parentId,departmentId from " + QUESTION_TYPE);
			List<Object> questionParams = new ArrayList<Object>();

			handleSql(questionParamsMap, questionSql, questionParams, null);

			Map<String, Object> questionIdMap = commonDao.selectForMap(questionSql.toString(),
					questionParams.toArray());
			// 判断该问题分类是否是子分类
			if (!"".equals(questionIdMap.get("parentId")) || null != questionIdMap.get("parentId")) {
				questionParamsMap.clear();
				questionParamsMap.put("uuid", questionIdMap.get("parentId"));
				questionParams = new ArrayList<Object>();
				handleSql(questionParamsMap, new StringBuffer(), questionParams, null);
				questionIdMap = commonDao.selectForMap(questionSql.toString(), questionParams.toArray());
			}

			// 查询管理员的角色 id
			StringBuffer roleSql = new StringBuffer(" select uuid from " + USER_ROLE);

			List<Object> roleParams = new ArrayList<Object>();
			Map<String, Object> roleParamsMap = new HashMap<String, Object>();
			roleParamsMap.put("level", 20);

			handleSql(roleParamsMap, roleSql, roleParams, null);
			Map<String, Object> roleIdMap = commonDao.selectForMap(roleSql.toString(), roleParams.toArray());

			// 通过 部门Id 及 角色Id 查询 用户
			StringBuffer userSql = new StringBuffer(" select uuid from " + USER);

			List<Object> userParams = new ArrayList<Object>();
			Map<String, Object> userParamsMap = new HashMap<String, Object>();
			userParamsMap.put("roleId", roleIdMap.get("uuid"));
			userParamsMap.put("departmentId", questionIdMap.get("departmentId"));

			handleSql(userParamsMap, userSql, userParams, null);
			Map<String, Object> userIdMap = commonDao.selectForMap(userSql.toString(), userParams.toArray());

			reMap.put(KEY_STATE, STATE_ONE);
			reMap.put("userId", userIdMap.get("uuid"));

		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_TWO);
			return reMap;
		}

		return reMap;
	}

	@Override
	public Map<String, Object> selectRoleByUser(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap = selectUserOne(dataMap);
		Object roleId = userMap.get("roleId");
		userMap.clear();
		userMap.put("uuid", roleId);
		reMap = selectRoleOne(userMap);
		return reMap;
	}

	@Override
	public Map<String, Object> selectDeptByUser(Map<String, Object> dataMap) {
		StringBuffer sql = new StringBuffer("select * from" + DEPARTMENT);
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, Object> userMap = new HashMap<String, Object>();

		// 查询出部门的uuid
		userMap = selectUserOne(dataMap);
		Object departmentId = userMap.get("departmentId");
		userMap.clear();
		userMap.put("uuid", departmentId);
		// 查询部门表
		List<Object> params = new ArrayList<Object>();
		try {
			handleSql(userMap, sql, params, null);
			reMap = commonDao.selectForMap(sql.toString(), params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reMap;
	}

	/*
	 * @Override public List<Map<String, Object>> selectUserBydept(Map<String,
	 * Object> dataMap) { List<Map<String, Object>> reMap = new
	 * ArrayList<Map<String, Object>>(); StringBuffer deptSql = new
	 * StringBuffer("select * from" + DEPARTMENT); StringBuffer userSql = new
	 * StringBuffer("select * from" + USER); Map<String, Object> deptMap = new
	 * HashMap<String, Object>(); List<Object> deptParams = new
	 * ArrayList<Object>(); List<Object> UserParams = new ArrayList<Object>();
	 * try { //查询部门表,得到部门的uuid handleSql(dataMap, deptSql, deptParams, null);
	 * deptMap= commonDao.selectForMap(deptSql.toString(),
	 * deptParams.toArray()); Object departmentId = deptMap.get("uuid");
	 * deptMap.clear(); deptMap.put("departmentId", departmentId);
	 * //根据departmentId查询user表 handleSql(deptMap, userSql, UserParams, null);
	 * reMap= commonDao.selectList(userSql.toString(), UserParams); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * return reMap; }
	 */
	@Override
	public Map<String, Object> selectUserBydept(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select * from " + USER);
		List<Object> params = new ArrayList<Object>();
		try {
			// 根据departmentId和systemId查询user表
			handleSql(dataMap, sql, params, null);
			List<Map<String, Object>> list = commonDao.selectList(sql.toString(), params);
			if (list != null && list.size() > 0) {
				reMap.put("list", list);
				reMap.put(KEY_STATE, STATE_ONE);
			} else {
				reMap.put(KEY_STATE, STATE_FIFTEEN);
			}

		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_TWO);
			return reMap;
		}

		return reMap;
	}

	@Override
	public Map<String, Object> recoverStaff(Map<String, Object> dataMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (dataMap.get("uuid") != null) {
			dataMap.put("status", 1);
			try {
				dataMap.put("pk", "uuid");
				commonDao.updateOne(dataMap, USER);
				resultMap.put(KEY_STATE, STATE_ONE);
			} catch (Exception e) {
				resultMap.put(KEY_STATE, STATE_TWO);
				log.error("添加数据失败", e);
			}

		}
		return resultMap;
	}

	@Override
	public Map<String, Object> selectDept(Map<String, Object> dataMap) {
		dataMap.put("orderBy", "createTime desc");
		StringBuffer sql = new StringBuffer(" select * from " + DEPARTMENT);

		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();

		try {
			handleSql(dataMap, sql, params, null);

			List<Map<String, Object>> List = commonDao.selectList(sql.toString(), params);
			if (List != null && List.size() > 0) {
				for (Map<String, Object> map : List) {
					Map<String, Object> newMap = new HashMap<String, Object>();
					newMap.put("departmentId", map.get("uuid"));
					Map<String, Object> selectDeptAdmin = selectDeptAdmin(newMap);
					if ("1".equals(selectDeptAdmin.get("state").toString())) {
						Map<String, Object> object = (Map<String, Object>) selectDeptAdmin.get("data");
						map.put("adminUser", object.get("realName"));
					}
					// Map<String, Object> userMap = (Map<String, Object>)
					// selectDeptAdmin
					// .get("data");
					// if (!CommonUtil.isEmpty(userMap)) {
					// map.put("adminUser", userMap.get("realName"));
					// }
				}
				reMap.put(KEY_STATE, STATE_ONE);
				reMap.put("list", List);
			} else {
				reMap.put(KEY_STATE, STATE_FIFTEEN);
			}
		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}
		return reMap;
	}

	@Override
	public Map<String, Object> selectDeptOne(Map<String, Object> dataMap) {

		StringBuffer sql = new StringBuffer("select * from " + DEPARTMENT);
		Map<String, Object> reMap = new HashMap<String, Object>();

		// 查询部门表
		List<Object> params = new ArrayList<Object>();
		try {

			handleSql(dataMap, sql, params, null);
			Map<String, Object> map = commonDao.selectForMap(sql.toString(), params.toArray());
			reMap.put("data", map);
			if (!CommonUtil.isEmpty(reMap)) {
				reMap.put(KEY_STATE, STATE_ONE);
			} else {
				reMap.put(KEY_STATE, STATE_FIFTEEN);
			}

		} catch (Exception e) {

			reMap.put(KEY_STATE, STATE_ZERO);
			return reMap;
		}

		return reMap;
	}

	@Override
	public Map<String, Object> selectDeptAdmin(Map<String, Object> dataMap) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			// 查询部门管理员roleId
			StringBuffer deptSql = new StringBuffer(" select * from " + USER_ROLE + " where code=? ");
			Map<String, Object> deptMap = commonDao.selectForMap(deptSql.toString(), CODE_DEPARTMENT_ADMIN);

			// 查询部门管理员信息
			StringBuffer userSql = new StringBuffer(" select * from " + USER);
			dataMap.put("roleId", deptMap.get("uuid"));
			List<Object> paramsList = new ArrayList<Object>();
			handleSql(dataMap, userSql, paramsList, null);

			Map<String, Object> user = commonDao.selectForMap(userSql.toString(), paramsList.toArray());

			if (user != null) {
				reMap.put("data", user);
				reMap.put(KEY_STATE, STATE_ONE);
			} else {
				reMap.put(KEY_STATE, STATE_FIFTEEN);
			}
		} catch (Exception e) {
			reMap.put(KEY_STATE, STATE_TWO);
			return reMap;
		}

		return reMap;
	}

	@Override
	public Map<String, Object> updateManageDept(Map<String, Object> dataMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			dataMap.put("pk", "uuid");
			commonDao.updateOne(dataMap, DEPARTMENT);
			resultMap.put(KEY_STATE, STATE_ONE);
		} catch (Exception e) {
			resultMap.put(KEY_STATE, STATE_ZERO);
			return resultMap;
		}

		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectDeptUser(Map<String, Object> dataMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> userMap = new HashMap<String, Object>();
		// 根据uuid查询当前人的信息
		userMap = selectUserOne(dataMap);
		if ("1".equals(userMap.get(KEY_STATE))) {
			Map<String, Object> tempMap = (Map<String, Object>) userMap.get("data");
			Object departmentId = tempMap.get("departmentId");
			tempMap.clear();
			// 查询状态正常的人，才能处理工单
			tempMap.put("departmentId", departmentId);
			tempMap.put("status", 1);
			resultMap = selectUserList(tempMap);
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> sendCode(Map<String, Object> userMap) {
		Map<String,Object> reMap = new HashMap<String, Object>();
		Map<String, Object> uMap;
		try {
			uMap = userBusinessImpl.selectOne(userMap);
			if(!CommonUtil.isEmpty(uMap.get("data"))){
				//发送短信
				reMap = (Map<String, Object>) uMap.get("data");
				String phone = reMap.get("phone").toString();
				String code = UuidUtil.generateUUID().substring(0, 6);
				Sms sms = new Sms();

				String tim = "yyyy-MM-dd HH:mm:ss";
				String content = "[百居易电子商务有限公司]验证码:"+code+",请勿向任何人提供验证码，谨防咋骗";
				sms.setTim(tim);
				sms.setPhone(phone);
				sms.setContent(content);
				SendSmsUtils.sendSms(sms);
				reMap.put("code", code);
			}else{
				reMap.put(KEY_STATE, STATE_TWO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMap.put(KEY_STATE, STATE_ZERO);
		}
		return reMap;
	}

}
