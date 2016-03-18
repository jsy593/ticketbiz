package com.jsy.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsy.dao.common.ICommonDao;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.DateUtil;
import com.jsy.util.common.ListMapUtil;
import com.jsy.util.common.PageUtil;

@Service
public class BaseBusiness {

	@Autowired
	protected ICommonDao commonDao;

	// t_user_role表中的code的四个属性，对应不同的角色id
	/**
	 * 超级管理员的code
	 */
	public static final String CODE_SUPER_ADMIN = "superAdmin";
	/**
	 * 系统管理员的code
	 */
	public static final String CODE_SYSTEM_ADMIN = "systemAdmin";
	/**
	 * 部门管理员的code
	 */
	public static final String CODE_DEPARTMENT_ADMIN = "departmentAdmin";
	/**
	 * 普通职员的code
	 */
	public static final String CODE_STAFF = "staff";
	// 用户表
	public static final String USER = "t_user";
	// 接入系统表
	public static final String ACCESS_SYSTEM = "t_access_system";
	// 部门表
	public static final String DEPARTMENT = "t_department";
	// 知识库表
	public static final String KNOWLEDGE = "t_knowledge";
	// 分类、用户对应关系表
	public static final String QUESTION_TYPE_USER = "t_question_type_user_map";
	// 问题分类表
	public static final String QUESTION_TYPE = "t_question_type";
	// 快速答复表
	public static final String QUICK_REQLY = "t_quick_reply";
	// 答复表
	public static final String REPLY = "t_reply";
	// 工单表
	public static final String TICKET = "t_ticket";
	// 工单流转表
	public static final String TICKET_MOVE = "t_ticket_move";
	// 用户角色表
	public static final String USER_ROLE = "t_user_role";
	// 接入系统序号表
	public static final String COMMSEQUENCE = "t_commsequence";
	
	// 日志
	protected Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());
	// 主键
	protected Integer primaryKey = -1;
	// 3 密码错误 4 账号禁止登陆 5 账号异常
	/**
	 * 返回MAP状态值
	 */
	public static final String KEY_STATE = "state";
	/**
	 * 状态码0 服务器错误
	 */
	public static final Integer STATE_ZERO = 0;
	/**
	 * 状态码1 操作成功
	 */
	public static final Integer STATE_ONE = 1;
	/**
	 * 状态码2 操作失败
	 */
	public static final Integer STATE_TWO = 2;
	/**
	 * 状态码3 参数错误(包含无必填参数，参数类型不匹配)
	 */
	public static final Integer STATE_THREE = 3;
	/**
	 * 状态码4 用户名不存在
	 */
	public static final Integer STATE_FOUR = 4;
	/**
	 * 状态码5 密码错误
	 */
	public static final Integer STATE_FIVE = 5;
	/**
	 * 状态码6 账号禁止登陆
	 */
	public static final Integer STATE_SIX = 6;
	/**
	 * 状态码7 账号异常
	 */
	public static final Integer STATE_SEVEN = 7;
	/**
	 * 状态码8 用户名已存在
	 */
	public static final Integer STATE_EIGHT = 8;
	/**
	 * 状态码9 密码不存在
	 */
	public static final Integer STATE_NINE = 9;
	/**
	 * 状态码10 为空
	 */
	public static final Integer STATE_TEN = 10;
	/**
	 * 状态码11 数据库中已存在
	 */
	public static final Integer STATE_ELEVEN = 11;
	/**
	 * 状态码12 部门和分类有对应关系
	 */
	public static final Integer STATE_TEWELVE = 12;
	/**
	 * 状态码13 提示管理员分类对应部门
	 */
	public static final Integer STATE_THIRTEEN = 13;

	/**
	 * 状态码14权限不足
	 */
	public static final Integer STATE_FOURTEEN = 14;
	/**
	 * 状态码15没有数据
	 */
	public static final Integer STATE_FIFTEEN = 15;
	
	/**
	 *状态吗 16 角色不存在 
	 */
	public static final Integer STATE_SIXTEEN = 16;

	/**
	 * 处理sql
	 * 
	 * @author 林
	 * @date 2015年9月7日下午2:19:24
	 * @param dataMap
	 *            需要处理的参数Map
	 * @param sql
	 *            处理后的sql
	 * @param paramsList
	 *            处理后的参数
	 * @param likeParams
	 *            需要模糊查询的字段
	 * @throws Exception
	 * @update
	 * @date
	 */
	protected void handleSql(Map<String, Object> dataMap, StringBuffer sql, List<Object> paramsList, String likeParams)
			throws Exception {
		handleParams(dataMap, sql, paramsList, likeParams);
		if (dataMap.containsKey("orderBy")) {
			handleOrderSql(dataMap, sql, paramsList);
		}
		if (dataMap.containsKey("pageIndex") && dataMap.containsKey("pageSize")) {
			handlePageSql(dataMap, sql, paramsList);
		}
	}
	
	/**
	 * 性别转汉字
	 * 
	 * @param dataMap
	 */
	private void sexToStr(Map<String, Object> dataMap) {
		if(!CommonUtil.isEmpty(dataMap)){
			if ("0".equals(dataMap.get("sex") + "")) {
				dataMap.put("sex", "女");
			} else if ("1".equals(dataMap.get("sex") + "")) {
				dataMap.put("sex", "男");
			}
		}
	}
	
	/**
	 * 时间转日期
	 * @param dataMap
	 */
	private void timeToDate(Map<String, Object> dataMap) {
		if(!CommonUtil.isEmpty(dataMap) && dataMap.containsKey("createTime")){
			String datestr = DateUtil.getDateString(dataMap.get("createTime"), DateUtil.YYYY_MM_DD_HH_MM);
			dataMap.put("createTime", datestr);
		}
	}

	/**
	 * 普通参数的sql拼接(内部使用)
	 * 
	 * @author 林
	 * @date 2015年9月7日下午2:30:21
	 * @param paramsMap
	 *            需要处理的参数Map
	 * @param sql
	 *            处理后的sql
	 * @param paramsList
	 *            处理后的参数
	 * @param likeStrs
	 *            需要模糊查询的字段
	 * @throws Exception
	 * @update
	 * @date 修改方法为私有
	 */
	private void handleParams(Map<String, Object> paramsMap, StringBuffer sql, List<Object> paramsList, String likeStrs)
			throws Exception {
		if (!CommonUtil.isEmpty(paramsMap)) {
			List<String> likeList = new ArrayList<String>();
			if (likeStrs != null) {
				likeList = ListMapUtil.splitStrToList(likeStrs, ",");
			}

			String whereStr = " where 1 = 1 ";
			sql.append(whereStr);

			Iterator<Entry<String, Object>> iterator = paramsMap.entrySet().iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				String key = next.getKey();
				Object value = next.getValue();

				if (!key.equals("pageIndex") && !key.equals("pageSize") && !key.equals("orderBy")) {
					count++;
					if (likeList.contains(key)) {
						sql.append(" and ").append(key).append(" like ").append(" '%' ? '%' ");
					} else {
						sql.append(" and ").append(key).append(" = ").append(" ? ");
					}
					paramsList.add(value);
				}
			}

			if (count > 0) {
				int whereIndex = sql.indexOf(whereStr);
				sql.substring(whereIndex + 1, sql.length());
			}
		}
	}

	/**
	 * 排序的sql拼接(内部使用)
	 * 
	 * @author 林
	 * @date 2015年9月7日下午2:32:16
	 * @param paramsMap
	 *            需要处理的参数Map
	 * @param sql
	 *            处理后的sql
	 * @param paramsList
	 *            处理后的参数
	 * @update
	 * @date 修改方法为私有
	 */
	private void handleOrderSql(Map<String, Object> paramsMap, StringBuffer sql, List<Object> paramsList) {
		if (paramsMap.containsKey("orderBy")) {
//			sql.append(" order by ").append(" ? ");
//			paramsList.add(paramsMap.get("orderBy"));
			sql.append(" order by " + paramsMap.get("orderBy"));
		}
	}

	/**
	 * 分页的sql拼接(内部使用)
	 * 
	 * @author 林
	 * @date 2015年9月7日下午2:32:16
	 * @param paramsMap
	 *            需要处理的参数Map
	 * @param sql
	 *            处理后的sql
	 * @param paramsList
	 *            处理后的参数
	 * @update
	 * @date 修改方法为私有
	 */
	private void handlePageSql(Map<String, Object> paramsMap, StringBuffer sql, List<Object> paramsList)
			throws Exception {
		if (paramsMap.containsKey("pageIndex") && paramsMap.containsKey("pageSize")) {
			Integer pageIndex = Integer.parseInt(paramsMap.get("pageIndex").toString());
			Integer pageSize = Integer.parseInt(paramsMap.get("pageSize").toString());

			sql.append(" limit ").append(" ?,? ");
			paramsList.add(PageUtil.getStart(pageIndex, pageSize));
			paramsList.add(pageSize);
		}
	}

	public static void main(String[] args) throws Exception {
		StringBuffer sql = new StringBuffer(" select * from sys_user");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<Object> paramsList = new ArrayList<Object>();
		BaseBusiness baseBusiness = new BaseBusiness();

		paramsMap.put("userName", "sdfdsf");
		paramsMap.put("status", "1");
		paramsMap.put("title", "地地");
		paramsMap.put("pageIndex", 2);
		paramsMap.put("pageSize", 20);
		paramsMap.put("orderBy", "createTime desc");

		baseBusiness.handleSql(paramsMap, sql, paramsList, "userName");

		System.err.println(sql.toString());
		System.err.println(paramsList);
	}
}
