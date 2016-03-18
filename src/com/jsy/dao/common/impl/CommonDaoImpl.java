package com.jsy.dao.common.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import com.jsy.dao.common.ICommonDao;
import com.jsy.util.common.CommonUtil;
import com.jsy.util.common.DateUtil;
import com.jsy.util.common.ListMapUtil;
import com.jsy.util.common.StringUtil;

/**
 * 数据持久操作实现
 * 
 * @author 张强 2013年9月16日 14时13分
 * 
 */
@Repository
public class CommonDaoImpl implements ICommonDao {
	private Log log = LogFactory.getLog(CommonDaoImpl.class);
	public final static String OR_JOIN_STRING = "or";
	public final static String AND_JOIN_STRING = "and";
	public final static String DESC = "desc";
	public final static String ASC = "asc";
	public final static String PRIMARY_KEY = "pk";
	public final static String UPDATE_PRIMARY_KEY = "updatepk";

	// springJDBC注入
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/*
	 * 删除单条数据-MAP
	 * 
	 */
	public Integer deleteOne(final Map<String, Object> dataMap,
			final String tableName) throws Exception {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			StringBuilder sql = new StringBuilder("delete from ");
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				sql.append(tableName).append(" where 1=1 ");
				
				List<String> valList = new ArrayList<String>();
				Set<String> keySet = dataMap.keySet();
				Iterator<String> iterator = keySet.iterator();
				String key = null;
				String val = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					sql.append(" and ").append(key).append(" = ? ");
					
					val = dataMap.get(key).toString();
					valList.add(val);
				}
				log.info("   删除表[" + tableName + "] 的SQL语句: " + sql);
				
				PreparedStatement pstm = null;
				try {
					pstm = conn.prepareStatement(sql.toString());
					int valListLen = valList.size();
					for (int i = 0; i < valListLen; i++) {
						pstm.setObject((i + 1), valList.get(i));
					}
				} catch (Exception e) {
					log.error("删除表[" + tableName + "]时出错, SQL:" + sql, e);
					throw new SQLException("delete4Entity 删除表[" + tableName + "]时出错!!!", e);
				} finally {
					try {
						CommonUtil.offObject(sql, valList, keySet, iterator,key, val);
					} catch (Exception e) {
						log.error("删除表[" + tableName + "]时清空出错, SQL:" + sql + "; 清空对象出错!!!" , e);
					}
				}
				return pstm;
			}
		});
	}

	/*
	 * 批量删除
	 * 
	 */
	public Integer deleteBatch(final List<String> paramList, String deletefield,
			final String tableName) throws Exception {
		StringBuilder sql = new StringBuilder("delete from ");
		sql.append(tableName).append(" where 1=1 ").append(" and ").append(
				deletefield).append(" = ? ");
		log.info("   删除表[" + tableName + "] 的SQL语句: " + sql);
		Integer deleteStatus = null;
		try {
			int[] batchUpdate = jdbcTemplate.batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public int getBatchSize() {
							return paramList.size();
						}

						@Override
						public void setValues(PreparedStatement pstm, int i)
								throws SQLException {
							pstm.setObject(1, paramList.get(i));
						}

					});
			if (batchUpdate.length > 0) {
				deleteStatus = batchUpdate.length;
			}
		} catch (Exception e) {
			throw new SQLException(
					"delete4Entity 删除表[" + tableName + "]时出错!!!", e);
		} finally {
			sql = null;
		}
		return deleteStatus;
	}

	/*
	 * 批量删除
	 * 
	 */
	public Integer deleteBatch(final List<String> paramList, List<String> fieldLsit, String tableName) throws Exception {
		StringBuilder sql = new StringBuilder("delete from ");

		if (fieldLsit != null) {
			sql.append(tableName).append(" where 1=1 ");
			for (int i = 0; i < fieldLsit.size(); i++) {
				sql.append(" and " + fieldLsit.get(i) + " = ? ");
			}
		}

		log.info("   删除表[" + tableName + "] 的SQL语句: " + sql);
		Integer deleteStatus = null;

		try {
			int[] batchUpdate = jdbcTemplate.batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public int getBatchSize() {
							return paramList.size();
						}

						@Override
						public void setValues(PreparedStatement pstm, int i)
								throws SQLException {
							Object objectParam = paramList.get(i);
							pstm.setObject(i, objectParam);
						}
					});
			if (batchUpdate.length > 0) {
				deleteStatus = 9;
			}
		} catch (Exception e) {
			throw new SQLException(
					"delete4Entity 删除表[" + tableName + "]时出错!!!", e);
		} finally {
			sql = null;
		}
		return deleteStatus;
	}

	/*
	 * 单个删除,通过MAP
	 * 
	 */
	public Integer insertOne(Map<String, Object> dataMap, final String tableName)
			throws Exception {
		final List<Object> paramSql = this.getInsertSql(dataMap, tableName);
		final int paramSqlLen = paramSql.size() - 1;
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); // 获取主键值

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				String insertSql = paramSql.get(paramSql.size() - 1).toString(); // 获取SQL
				log.info("   操作表[" + tableName + "] 的SQL语句: " + insertSql
						+ " 参数:" + paramSql);
				PreparedStatement pstm = null;
				Object param = null;
				try {
					pstm = conn.prepareStatement(insertSql,
							Statement.RETURN_GENERATED_KEYS);
					for (int i = 0; i < paramSqlLen; i++) {
						param = paramSql.get(i);
						if (param instanceof Date) {
							pstm.setTimestamp(i + 1, new Timestamp(
									((Date) param).getTime()));
						} else {
							pstm.setObject(i + 1, param);
						}
					}
				} finally {
					if (insertSql != null) {
						insertSql = null;
					}

					if (param != null) {
						param = null;
					}
				}
				return pstm;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}


	/*
	 * 数据插入,返回操作代码
	 * 
	 */
	public Integer insertManual(Map<String, Object> dataMap, String tableName)
			throws Exception {
		final List<Object> paramSql = getInsertSql(dataMap, tableName);
		final int paramSqlLen = paramSql.size() - 1;
		log.info("   操作表[" + tableName + "] 的SQL语句: "
				+ paramSql.get(paramSql.size() - 1));
		int updateState = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstm = null;
				Object param = null;
				try {
					pstm = conn.prepareStatement(paramSql.get(
							paramSql.size() - 1).toString());
					for (int i = 0; i < paramSqlLen; i++) {
						param = paramSql.get(i);
						if (param instanceof Date) {
							pstm.setTimestamp(i + 1, new Timestamp(
									((Date) param).getTime()));
						} else {
							pstm.setObject(i + 1, param);
						}
					}
				} catch (Exception e) {
					throw new SQLException("不返回无主键实体更新出错!!!", e);
				} finally {
					param = null;
				}
				return pstm;
			}
		});
		return updateState;
	}


	/*
	 * 查询数据-不分页,可排序 查询数据,entityClazz可以为视图或者是多表联接的查询,不限制,参数放入的时候必须和SQL拼接的?号顺序一致
	 * 
	 */
	public List<?> selectObject(String sql, List<Object> params,
			Class<?> entityClazz) throws Exception {
		SqlRowSet rs = null;
		SqlRowSetMetaData metaData = null;
		int columnCount = 0;
		Map<String, Integer> tableColMetaMap = new HashMap<String, Integer>();
		Object instance = null;
		Field[] declaredFields = null;
		int fieldsLen = 0;
		List<Object> reList = new ArrayList<Object>();
		if (CommonUtil.isEmpty(params)) {
			params = new ArrayList<Object>();
		}
		try {
			log.info(" ====>> 查询语句:" + sql);
			rs = jdbcTemplate.queryForRowSet(sql, params.toArray());
			metaData = rs.getMetaData();
			columnCount = metaData.getColumnCount();
			// 获取列名及列类型
			for (int i = 0; i < columnCount; i++) {
				tableColMetaMap.put(metaData.getColumnLabel(i + 1)
						.toLowerCase(), metaData.getColumnType(i + 1));
			}

			// 将查询数据装入到实体中,实体放入到LIST中返回
			while (rs.next()) {
				instance = entityClazz.newInstance();
				declaredFields = instance.getClass().getDeclaredFields();
				fieldsLen = declaredFields.length;
				for (int i = 0; i < fieldsLen; i++) {
					declaredFields[i].setAccessible(true);
					setFiledVal(declaredFields[i], instance, rs,
							tableColMetaMap);
				}
				reList.add(instance);
			}

		} finally {
			// 有些数据清空可能会影响查询后的数据异常
			if (rs != null) {
				rs = null;
			}

			if (metaData != null) {
				metaData = null;
			}

			if (!tableColMetaMap.isEmpty()) {
				tableColMetaMap = null;
			}

			instance = null;
			declaredFields = null;
		}
		return reList;
	}

	/*
	 */
	public List<Map<String, Object>> selectList(String sql, List<Object> params)
			throws Exception {
		log.info("====>> 查询SQL语句:" + sql + "  || 参数:" + params);
		if (CommonUtil.isEmpty(params)) {
			params = new ArrayList<Object>();
		}
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql,
				params.toArray());
		return queryForList;
	}
	
	/*
	 */
	public List<Map<String, Object>> selectListSimple(String sql, Object... args) throws Exception {
		log.info("====>> 查询SQL语句:" + sql + "  || 参数:" + Arrays.toString(args));
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, args);
		return queryForList;
	}


	/*
	 * 数据模糊查询--查询所有,可以传入任何sql语句
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<?> selectObject(String sql, Map<String, Object> paramMap,
			Map<String, Object> orderMap, Class<?> entityClazz)
			throws Exception {
		List<Object> sqlList = joinFieldValueToSQL(sql, paramMap);
		int sqlListSize = sqlList.size();
		List<?> reList = null;
		try {
			if (sqlListSize == 1) {
				// 没有任何参数时
				StringBuilder sqlInList = new StringBuilder(sqlList.get(0)
						.toString());
				String joinOrderSql = joinOrderSql(sqlInList, orderMap);
				List<Object> paramList = new ArrayList<Object>();
				reList = selectObject(joinOrderSql, paramList, entityClazz);
			} else if (sqlListSize == 2) {
				// 有参数,需要进行拼装
				StringBuilder sqlInList = new StringBuilder(sqlList.get(0)
						.toString());
				String joinOrderSql = joinOrderSql(sqlInList, orderMap);
				List<Object> paramList = (List<Object>) sqlList.get(1);
				reList = selectObject(joinOrderSql, paramList, entityClazz);
			}
		} finally {
			if (sqlList != null) {
				sqlList = null;
			}
		}
		return reList;
	}

	public Integer updateOne(final Map<String, Object> dataMap,
			final String tableName) throws Exception {
		int updateStatus = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				List<Object> paramSql = null;
				Object updateSql = null;
				PreparedStatement pstm = null;
				Object param = null;
				int paramSqlLen = 0;
				try {
					paramSql = getUpdateSql(dataMap, tableName);
					updateSql = paramSql.get(paramSql.size() - 1);
					log.info("   更新表[" + tableName + "] 的SQL语句: " + updateSql);
					paramSqlLen = paramSql.size() - 1;
					pstm = conn.prepareStatement(updateSql.toString());
					// 循环装入参数,日期参数转型后装入
					for (int i = 0; i < paramSqlLen; i++) {
						param = paramSql.get(i);
						if (param instanceof Date) {
							pstm.setTimestamp(i + 1, new Timestamp(
									((Date) param).getTime()));
						} else {
							pstm.setObject(i + 1, param);
						}
					}
				} catch (Exception e) {
					throw new SQLException("update4Entity 更新表[" + tableName
							+ "]时出错!!!", e);
				} finally {
					paramSql = null;
					updateSql = null;
					param = null;
				}
				return pstm;
			}
		});
		return updateStatus;
	}

	/**
	 * 将MAP拼装成SQL语句
	 * 
	 * @param map
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getInsertSql(Map<String, Object> map, String tableName)
			throws Exception {
		List<Object> reList = new ArrayList<Object>(); // 拼装后的SQL LIST
		StringBuilder colSql = new StringBuilder();
		StringBuilder valueSql = new StringBuilder();
		StringBuilder sql = null;

		// 实体相关
		Object primaryKey = null;

		Set<Entry<String, Object>> entrySet = null;
		Iterator<Entry<String, Object>> iterator = null;
		@SuppressWarnings("unused")
		int filedsLen = 0;

		try {
			sql = new StringBuilder();
			if (map.containsKey(CommonDaoImpl.PRIMARY_KEY)) {
				primaryKey = map.get("pk");
			}
			entrySet = map.entrySet();
			iterator = entrySet.iterator();

			log.debug("))))oooo 列值拼接 列格式 为(a, b, c) ,值格式为(?, ?, ?)");
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
						.next();
				/**
				 * 如果报空指针,则说明实体的getPrimaryKey方法返回值为null
				 */
				if (!map.containsKey(CommonDaoImpl.PRIMARY_KEY)) {
					if (entry.getValue() != null) {
						colSql.append(entry.getKey().toString().toLowerCase()
								+ ",");
						valueSql.append("?,");
						reList.add(entry.getValue());
					}
				}

			}
			// 将列或拼接后的SQL最后一个, 去掉
			if (colSql != null && colSql.length() >= 0) {
				colSql.deleteCharAt(colSql.length() - 1);
			}
			if (valueSql != null && valueSql.length() >= 0) {
				valueSql.deleteCharAt(valueSql.length() - 1);
			}

			// 拼接完整SQL
			sql.append("insert into ").append(tableName).append(" (").append(
					colSql).append(" )").append(" values( ").append(valueSql)
					.append(" )");
			log.debug("完成拼接,SQL为:" + sql.toString());
			reList.add(sql);
		} finally {
			CommonUtil.offObject(colSql, valueSql, sql, primaryKey, tableName, entrySet, iterator);
		}
		return reList;
	}
	
	/**
	 * 将MAP拼装成SQL语句,并直接拼装值
	 * 
	 * @param map
	 * @param tableName
	 * @return 直接返回成品SQL
	 * @throws Exception
	 */
	public String getInsertBatchSqlWithVal(Map<String, Object> map, String tableName)
			throws Exception {
		StringBuilder colSql = new StringBuilder();
		StringBuilder valueSql = new StringBuilder();
		StringBuilder sql = null;

		Set<Entry<String, Object>> entrySet = null;
		Iterator<Entry<String, Object>> iterator = null;
		try {
			sql = new StringBuilder();
			entrySet = map.entrySet();
			iterator = entrySet.iterator();

			log.debug("))))oooo 列值拼接 列格式 为(a, b, c) ,值格式为(?, ?, ?)");
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
				/**
				 * 如果报空指针,则说明实体的getPrimaryKey方法返回值为null
				 */
				if (!map.containsKey(CommonDaoImpl.PRIMARY_KEY)) {
					if (entry.getValue() != null) {
						colSql.append(entry.getKey().toString().toLowerCase() + ",");
						valueSql.append("'").append(entry.getValue()).append("',");
					}
				}

			}
			// 将列或拼接后的SQL最后一个, 去掉
			if (colSql != null && colSql.length() >= 0) {
				colSql.deleteCharAt(colSql.length() - 1);
			}
			if (valueSql != null && valueSql.length() >= 0) {
				valueSql.deleteCharAt(valueSql.length() - 1);
			}

			// 拼接完整SQL
			sql.append("insert into ").append(tableName).append(" (").append(
					colSql).append(" )").append(" values( ").append(valueSql)
					.append(" )");
			log.debug("完成拼接,SQL为:" + sql.toString());
		} finally {
			CommonUtil.offObject(colSql, valueSql, sql, tableName, entrySet, iterator);
		}
		return sql.toString();
	}


	/**
	 * 将MAP,拼装成SQL语句
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String joinMapToSql(Map<String, Object> map, String tableName)
			throws Exception {

		return null;
	}

	/**
	 * 模糊查询时拼接orderby语句
	 * 
	 * @param sqlInList
	 *            需要拼接的语句
	 * @param orderMap
	 *            拼接参数,排序map,key只能是"desc"或者"asc",
	 *            value为需要排序的对象,如有多个需要排序,则用逗号分隔(,), 比如map.put("desc",
	 *            "id,time"),map的key请使用CommonDaoImpl类的常量CommonDaoImpl.DESC来避免错误
	 *            map.put(CommonDaoImpl.DESC, "id")<br />
	 *            当orderMap为null,或者orderMap的key不包含"desc"或者"asc"时,则表示不排序
	 * @return
	 */
	private String joinOrderSql(StringBuilder sqlInList,
			Map<String, Object> orderMap) {
		if (!CommonUtil.isEmpty(orderMap) && orderMap.size() > 0) {
			if (orderMap.containsKey(CommonDaoImpl.ASC)) {
				sqlInList.append(" order by " + orderMap.get(CommonDaoImpl.ASC)
						+ " " + CommonDaoImpl.ASC + " ");
				return sqlInList.toString();
			}

			if (orderMap.containsKey(CommonDaoImpl.DESC)) {
				sqlInList.append(" order by "
						+ orderMap.get(CommonDaoImpl.DESC) + " "
						+ CommonDaoImpl.DESC + " ");
				return sqlInList.toString();
			}
		}
		return sqlInList.toString();
	}

	/**
	 * 将查询字段及值拼接成完整的SQL语句; <br />
	 * 如:select a.aa, a.ab from abc a where 1 = 1; <br />
	 * MAP里有值对是这样的: name:Lucy , age = 7 <br />
	 * 拼接后为:select a.aa, a.ab from abc a where 1 = 1 and name like %Lucy% and
	 * age like %7%;
	 * 
	 * @param sql
	 *            需要拼接的SQL语句,注意SQL语句应该带 where 1 = 1;
	 * @param paramMap
	 *            查询字段拼接,其方式有3种 <br />
	 *            第一种:普通类型,直接拼接成AND,值为LIKE,如 and name like "value"p;特殊的时间类型将拼接成
	 *            time = time1; <br />
	 *            第二种:LIST类型,拼接成between and
	 *            格式,LIST需要2个字段,list.get(0)在and前,list.get(1)在and后,如 list.add(3),
	 *            list.add(20),语句为:between 3 and 20 <br />
	 *            第三种:MAP类型,各查询字段之间的连接串为OR,如: or name like "张" or age like "20";
	 * 
	 * @return 返回拼接好后的SQL<br />
	 *         如:select a.aa, a.ab from abc a where 1 = 1 and name like %Lucy%
	 *         and age like %7%; <br />
	 *         当MAP即参数为空的时候则不作处理,直接返回一个List长度为1的List, 里面只含有sql; <br />
	 *         当MAP不为空,则返回list为长度为2的List里面含有拼接好后的SQL语句,及一个List,装有对应的参数
	 * @throws Exception
	 */
	public List<Object> joinFieldValueToSQL(String sql,
			Map<String, Object> paramMap) throws Exception {
		StringBuilder sbSql = new StringBuilder(sql);
		List<Object> reList = new ArrayList<Object>();

		if (!CommonUtil.isEmpty(paramMap) && paramMap.size() >= 0) {
			Set<String> keySet = paramMap.keySet();
			List<Object> paramList = new ArrayList<Object>();
			Iterator<String> iterator = keySet.iterator();

			/*
			 * 拼接除类型为MAP的SQL语句
			 */
			List<Object> customSQLList = joinFieldValueToCustomSQL(paramMap,
					CommonDaoImpl.AND_JOIN_STRING);

			if (customSQLList.size() == 2) {
				sbSql.append(customSQLList.get(0));
				@SuppressWarnings("unchecked")
				List<Object> cusParamList = (List<Object>) customSQLList.get(1);
				for (int i = 0; i < cusParamList.size(); i++) {
					paramList.add(cusParamList.get(i));
				}
			}

			/*
			 * 将类型为MAP的拼成OR语句
			 */
			while (iterator.hasNext()) {
				String nextElement = iterator.next();
				boolean isContainsKey = paramMap.containsKey(nextElement);
				if (isContainsKey
						&& paramMap.get(nextElement) instanceof Map<?, ?>) {
					Map<?, ?> orMap = (Map<?, ?>) paramMap.get(nextElement);
					List<Object> customSQLMapList = joinFieldValueToCustomSQL(
							orMap, CommonDaoImpl.OR_JOIN_STRING);
					if (customSQLMapList.size() == 2) {
						sbSql.append(customSQLMapList.get(0));
						@SuppressWarnings("unchecked")
						List<Object> cusParamList = (List<Object>) customSQLMapList
								.get(1);
						for (int i = 0; i < cusParamList.size(); i++) {
							paramList.add(cusParamList.get(i));
						}
					}
				}
			}
			reList.add(sbSql.toString());
			reList.add(paramList);
		} else {
			reList.add(sbSql.toString());
		}
		sbSql = null;
		return reList;
	}

	/**
	 * 拼接自定义查询字符串, 可自定义是or或者是and拼接,需要指定相应的字符串, CommonDaoImpl类的常量指定是哪种拼接方式 <br />
	 * 如:joinFieldValueToOrSQL(map, CommonDaoImpl.ORJOINSTRING) <br />
	 * 
	 * @param map
	 *            需要拼接的字符串,KEY, VALUE的类型可能为DATE,LIST,MAP类型, 当遇到是MAP类型时会跳过,不处理,
	 *            DATE型只能是=符号连拼接, LIST类型时则会处理成between and,
	 *            LIST应该有2个字段,分别是AND前后的值,且LIST(0)的值应该是小于LIST(1)的值 <br />
	 *            如:List paramList = new ArraysList...(); <br />
	 *            paramList.add(1);paramList.add(2);
	 * 
	 * @param joinString
	 *            拼接提示字符串,可为CommonDaoImpl.ORJOINSTRING,
	 *            CommonDaoImpl.ANDJOINSTRING 2种 <br />
	 *            为ORJOINSTRING时,则把所有字段拼成OR, ANDJOINSTRING则把所有字段拼接成AND
	 * @throws Exception
	 */
	public List<Object> joinFieldValueToCustomSQL(Map<?, ?> map,
			String joinString) throws Exception {
		// 需要使用OR连接查询的时候,检查是否为MAP
		Set<?> orParamkeySet = map.keySet();
		Iterator<?> orParamIterator = null;
		if (orParamkeySet != null) {
			orParamIterator = orParamkeySet.iterator();
		}
		List<Object> reList = new ArrayList<Object>();
		StringBuilder orSql = null;
		List<Object> paramList = null;
		Object sqlOrValue = null;

		if (CommonDaoImpl.OR_JOIN_STRING.equals(joinString)) {
			joinString = " or ";
		}
		if (CommonDaoImpl.AND_JOIN_STRING.equals(joinString)) {
			joinString = " and ";
		}

		if (!CommonUtil.isEmpty(map) && map.size() >= 0) {
			orSql = new StringBuilder(200);
			paramList = new ArrayList<Object>();

			while (orParamIterator.hasNext()) {
				String orParamNextElement = (String) orParamIterator.next();
				boolean isContainsKeyInOrParam = map
						.containsKey(orParamNextElement);

				// 此MAP中不为MAP的全部类型则处理
				if (!(map.get(orParamNextElement) instanceof Map<?, ?>)) {
					if (isContainsKeyInOrParam
							&& map.get(orParamNextElement) instanceof Date) {
						// 日期类型查询只能用=, 不能用like
						sqlOrValue = map.get(orParamNextElement);
						orSql.append(joinString + orParamNextElement + " = ? ");
						paramList.add(sqlOrValue);
					} else if (isContainsKeyInOrParam
							&& map.get(orParamNextElement) instanceof List<?>) {
						// 需要用betweent and时MAP的VALUE需要放一个LIST
						List<?> betweenParam = (List<?>) map
								.get(orParamNextElement);
						if (betweenParam.size() == 2) {
							orSql.append(joinString + " ( "
									+ orParamNextElement
									+ " between ? and ? ) ");
							paramList.add(betweenParam.get(0));
							paramList.add(betweenParam.get(1));
						}
					} else {
						if (isContainsKeyInOrParam) {
							sqlOrValue = map.get(orParamNextElement);
							sqlOrValue = "%" + sqlOrValue + "%";
						}
						orSql.append(joinString + orParamNextElement
								+ " like ? ");
						paramList.add(sqlOrValue);
					}
				}
			}
			reList.add(orSql.toString());
			reList.add(paramList);
		}
		if (orParamkeySet != null) {
			orParamkeySet = null;
		}

		if (orParamIterator != null) {
			orParamIterator = null;
		}

		orSql = null;
		paramList = null;
		sqlOrValue = null;

		return reList;
	}

	/**
	 * 将MAP拼装成SQL
	 * 
	 * @param map
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getUpdateSql(Map<String, Object> map, String tableName)
			throws Exception {
		List<Object> reList = new ArrayList<Object>();
		StringBuilder colSql = new StringBuilder();
		StringBuilder sql = null;

		String primaryKeyStr = null;
		List<String> primaryKeyList = null;
		Map<String, Object> pkMap = new HashMap<String, Object>(); // 

		Set<Entry<String, Object>> entrySet = null;
		Iterator<Entry<String, Object>> iterator = null;
		Map.Entry<String, Object> entry = null;

		Iterator<Entry<String, Object>> iterator2 = null;
		Entry<String, Object> next = null;
		try {
			sql = new StringBuilder();
			if (map.containsKey(CommonDaoImpl.PRIMARY_KEY)) {
				primaryKeyStr = map.get(CommonDaoImpl.PRIMARY_KEY).toString();
				primaryKeyList = ListMapUtil
						.splitStrToList(primaryKeyStr, null);
				map.remove(CommonDaoImpl.PRIMARY_KEY);
				for (int i = 0; i < primaryKeyList.size(); i++) {
					pkMap.put(primaryKeyList.get(i), map.get(primaryKeyList
							.get(i)));
					map.remove(primaryKeyList.get(i));
				}
				if (map.containsKey("update")) {
					String updateStr = map.get("update").toString();
					map.remove("update");
					List<String> updateStrList = ListMapUtil.splitStrToList(
							updateStr, null);
					for (int i = 0; i < updateStrList.size(); i++) {
						map.put(updateStrList.get(i), pkMap.get(updateStrList
								.get(i)));
					}
				}
			}
			entrySet = map.entrySet();
			iterator = entrySet.iterator();
			log.debug("))))oooo 列值拼接 列格式 为(a, b, c) ,值格式为(= ?, = ?, = ?)");
			while (iterator.hasNext()) {
				entry = (Map.Entry<String, Object>) iterator.next();
				if (entry.getValue() != null) {
					colSql.append(entry.getKey().toString().toLowerCase()
							+ " = ?,");
					reList.add(entry.getValue());
				}
			}
			if (colSql != null && colSql.length() >= 0) {
				colSql.deleteCharAt(colSql.length() - 1);
			}

			sql.append("update ").append(tableName).append(" set ").append(
					colSql).append(" ").append(" where 1 = 1 ").append(" and ");

			iterator2 = pkMap.entrySet().iterator();

			while (iterator2.hasNext()) {
				next = iterator2.next();
				sql.append(next.getKey()).append(" = ? and ");
				reList.add(next.getValue());
			}

			if (sql != null && sql.length() >= 0) {
				sql.delete(sql.lastIndexOf("and"), sql.length());
			}
			reList.add(sql);
		} finally {
			CommonUtil.offObject(colSql, sql, primaryKeyStr, primaryKeyList, pkMap, entrySet, iterator, entry, iterator2, next);
		}
		return reList;
	}
	
	/**
	 * 将MAP拼装成SQL
	 * 
	 * @param map
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public String getUpdateSqlWithVal(Map<String, Object> map, String tableName) throws Exception {
		StringBuilder colSql = new StringBuilder();
		StringBuilder sql = null;

		String primaryKeyStr = null;
		List<String> primaryKeyList = null;
		Map<String, Object> pkMap = new HashMap<String, Object>(); // 

		Set<Entry<String, Object>> entrySet = null;
		Iterator<Entry<String, Object>> iterator = null;
		Map.Entry<String, Object> entry = null;

		Iterator<Entry<String, Object>> iterator2 = null;
		Entry<String, Object> next = null;
		sql = new StringBuilder();
		try {
			if (map.containsKey(CommonDaoImpl.PRIMARY_KEY)) {
				primaryKeyStr = map.get(CommonDaoImpl.PRIMARY_KEY).toString();
				primaryKeyList = ListMapUtil
						.splitStrToList(primaryKeyStr, null);
				map.remove(CommonDaoImpl.PRIMARY_KEY);
				for (int i = 0; i < primaryKeyList.size(); i++) {
					pkMap.put(primaryKeyList.get(i), map.get(primaryKeyList
							.get(i)));
					map.remove(primaryKeyList.get(i));
				}
				if (map.containsKey("update")) {
					String updateStr = map.get("update").toString();
					map.remove("update");
					List<String> updateStrList = ListMapUtil.splitStrToList(
							updateStr, null);
					for (int i = 0; i < updateStrList.size(); i++) {
						map.put(updateStrList.get(i), pkMap.get(updateStrList.get(i)));
					}
				}
			}
			entrySet = map.entrySet();
			iterator = entrySet.iterator();
			log.debug("))))oooo 列值拼接 列格式 为(a, b, c) ,值格式为(= ?, = ?, = ?)");
			String key = null;
			Object value = null;
			while (iterator.hasNext()) {
				entry = (Map.Entry<String, Object>) iterator.next();
				if (entry.getValue() != null) {
					key = entry.getKey();
					value = entry.getValue();
					colSql.append(key.toLowerCase() + " = '").append(value).append("',");
//					reList.add(entry.getValue());
				}
			}
			if (colSql != null && colSql.length() >= 0) {
				colSql.deleteCharAt(colSql.length() - 1);
			}

			sql.append("update ").append(tableName).append(" set ").append(
					colSql).append(" ").append(" where 1 = 1 ").append(" and ");

			iterator2 = pkMap.entrySet().iterator();

			while (iterator2.hasNext()) {
				next = iterator2.next();
				sql.append(next.getKey()).append(" = '" + next.getValue() + "' and ");
//				reList.add(next.getValue());
			}

			if (sql != null && sql.length() >= 0) {
				sql.delete(sql.lastIndexOf("and"), sql.length());
			}

		} finally {
			CommonUtil.offObject(colSql, sql, primaryKeyStr, primaryKeyList, pkMap, entrySet, iterator, entry, iterator2, next);
		}
		return sql.toString();
	}


	/**
	 * 查询数据总条数
	 * 
	 * @param sql
	 *            查询的SQL语句
	 * @param params
	 *            对应的参数,对应SQL中的?号
	 * @return 返回查询到的总条数
	 * @throws Exception
	 */
	public Long selectTotalCount(String sql, List<Object> params)
			throws Exception {
		StringBuilder countSql = new StringBuilder()
				.append("select count(*) counts from ");
		List<Map<String, Object>> queryForList = null;

		try {
			if (sql.indexOf("ORDER") >= 0) {
				sql = sql.substring(0, sql.indexOf("ORDER")); // 总条数查询SQL处理
			}
			if (sql.indexOf("order") >= 0) {
				sql = sql.substring(0, sql.indexOf("order")); // 总条数查询SQL处理
			}
			
			countSql.append("( " + sql + ") obj");
			log.info("====>> 查询数据总条数SQL语句: " + countSql);
			if (CommonUtil.isEmpty(params)) {
				params = new ArrayList<Object>();
			}
			queryForList = jdbcTemplate.queryForList(countSql.toString(),
					params.toArray());
			log.info("====>> 数据总条数: "
					+ Long.parseLong(queryForList.get(0).get("counts")
							.toString()));
			return Long.parseLong(queryForList.get(0).get("counts")
					.toString());
		} finally {
			if (queryForList != null) {
				queryForList = null;
			}

			if (countSql != null) {
				countSql = null;
			}
		}
	}

	/**
	 * 将查询数据设置到实体对象中
	 * 
	 * @param field
	 *            实体相应的设置器
	 * @param instance
	 *            需要设置到哪一个实体
	 * @param rs
	 *            结果集,保存着查询后的数据
	 * @param tableColMetaMap
	 *            查询数据的列名,以前列的类型(列名与实体成员变量表列一致);
	 * @throws Exception
	 */
	private void setFiledVal(Field field, Object instance, SqlRowSet rs,
			Map<String, Integer> tableColMetaMap) throws Exception {
		// 取出设置器的名字
		String fieldName = field.getName().toLowerCase();

		// 判断是否有此列,有则设置到实体中
		if (tableColMetaMap.containsKey(fieldName)) {
			switch (tableColMetaMap.get(fieldName)) {
			case Types.INTEGER:
			case Types.SMALLINT:
				field.set(instance, rs.getInt(fieldName));
				break;
			case Types.BIGINT:
				field.set(instance, rs.getLong(fieldName));
				break;
			case Types.FLOAT:
				field.set(instance, rs.getFloat(fieldName));
				break;
			case Types.DATE:
			case Types.TIMESTAMP:
				field.set(instance, rs.getTimestamp(fieldName));
				break;
			case Types.CHAR:
			case Types.NCHAR:
			case Types.VARCHAR:
			case Types.NVARCHAR:
				field.set(instance, rs.getString(fieldName));
				break;
			default:
				// log.debug("将【" + fieldName + "】 值设置到 【 " + instance + "】中");
				field.set(instance, rs.getObject(fieldName));
				break;
			}
		}
	}

	/**
	 * 获取数据库时间
	 * 
	 * @return Date 返回一个Date类型的日期(yyyy-MM-dd hh:MM:ss:mm);
	 * 
	 */
	public Date selectDBTime() throws Exception {
		return jdbcTemplate.queryForObject("SELECT CONVERT(VARCHAR(20),GETDATE(),120)", Date.class);
	}

	/**
	 * 获取jdbcTemplate
	 */
	@Override
	public JdbcTemplate getJdbcTemplate() throws Exception {
		return jdbcTemplate;
	}

	@Override
	public <T> T selectForObject(String sql, Object[] args, Class<T> clazz)
			throws Exception {
		try {
			return jdbcTemplate.queryForObject(sql, args, clazz);
		} catch (Exception e) {
			if ((e instanceof IncorrectResultSizeDataAccessException)
					&& ((IncorrectResultSizeDataAccessException) e)
							.getActualSize() == 0) {
				log.debug("数据不存在或者返回条数大于 1");
				return null;
			}
			// 其他的异常正常抛出
			throw e;
		}
	}

	@Override
	public Map<String, Object> selectForMap(String sql, Object... args)
			throws Exception {
		try {
			return jdbcTemplate.queryForMap(sql, args);
		} catch (Exception e) {
			if ((e instanceof IncorrectResultSizeDataAccessException)
					&& ((IncorrectResultSizeDataAccessException) e)
							.getActualSize() == 0) {
				log.debug("数据不存在或者返回条数大于 1");
				return null;
			}
			// 其他的异常正常抛出
			throw e;
		}
	}


	/**
	 * 组装插入sql语句，以及参数
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private Map<Integer, Object> getInsertSql(Map<String, Object> dataMap)
			throws Exception {
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		Iterator<Entry<String, Object>> iter = null;
		Entry<String, Object> entry = null;
		StringBuffer value = new StringBuffer(" VALUES(");
		Map<Integer, Object> params = new HashMap<Integer, Object>();
		try {
			if (dataMap.get("tableName") == null
					|| dataMap.get("tableName").equals("")) {
				log.error("表名不存在");
				throw new Exception("表名不存在");
			} else {
				sql.append(dataMap.get("tableName").toString()).append("(");
				dataMap.remove("tableName");
			}
			iter = dataMap.entrySet().iterator();
			int count = 0;
			int size = dataMap.size();
			while (iter.hasNext()) {
				entry = iter.next();
				sql.append(entry.getKey());
				value.append("?");

				if (size - 1 > count) {
					sql.append(", ");
					value.append(", ");
				}
				count++;
				params.put(count, entry.getValue());
			}
			sql.append(")").append(value.append(")").toString());
			params.put(-1, sql.toString());
		} catch (Exception e) {
			log.error("插入SQL语句拼接失败");
			
		} finally {
			value = null;
			iter = null;
			entry = null;
		}
		log.info("插入SQL语句  " + sql.toString());
		return params;
	}
	
	/**
	 * 获取批量SQL
	 * @param paramList 需要组装的SQL LISTMAP值, LISTMAP中必须包含tableName,否则不组装
	 * @param type  1 新增, 2修改
	 * @return 返回SQL LIST
	 * @throws Exception
	 */
	private List<String> getBatchSql(List<Map<String, Object>> paramList, int type) throws Exception {
		List<String> batchSqlList = new ArrayList<String>();
		if (paramList != null && paramList.size() > 0) {
			int paramListLen = paramList.size();
			
			String batchSql = null;
			String tableName = null;
			for (int i = 0; i < paramListLen; i++) {
				Map<String, Object> paramMap = paramList.get(i);
				
				if (paramMap.containsKey("tableName")) {
					tableName = paramMap.get("tableName").toString();
					paramMap.remove("tableName");
					
					if (type == 1) {
						batchSql = this.getInsertBatchSqlWithVal(paramMap, tableName);
					} else {
						batchSql = this.getUpdateSqlWithVal(paramMap, tableName);
					}
					batchSqlList.add(batchSql);
				}
			}
		}
		
		return batchSqlList;
	}
	
	/**
	 * 每个SQL可能不同的值, 
	 * @param paramList  paramList的每一个Map必须有字段tableName, 指明是哪一个表名,如果未指明,则不处理
	 * @param tableName
	 * @return 未执行则为空, 否则为执行结果(第一个元素为每一条的执行结果)
	 * @throws Exception 
	 */
	public int[] insertBatch(List<Map<String, Object>> paramList) throws Exception {
		int[] batchUpdate = null;
		List<String> sqlList = this.getBatchSql(paramList, 1);
		if (sqlList != null) {
			String[] sqlArr = (String[]) sqlList.toArray();
			batchUpdate = jdbcTemplate.batchUpdate(sqlArr);
		}
		
		return batchUpdate;
	}
	
	/**
	 * 批量更新, 更新的所有字段都是一样的
	 * 
	 * @param sqlCol 需要新增的字段
	 * @param tableName 表名
	 * @param valList	值列表
	 * @return 返回null,传入的参数有误, int[]无数据,执行条数为0,否则就是执行结果
	 * @throws Exception
	 */
	public int[] insertBatch(List<String> sqlCol, String tableName, List<List<String>> valList) throws Exception {
		int[] batchUpdate = null;
		StringBuffer sql = new StringBuffer("insert into ");
		if (sqlCol != null && sqlCol.size() > 0 && tableName != null && valList != null && valList.size() > 0) {
			String sqlColStr = sqlCol.toString();
			sqlColStr = sqlColStr.replace("[", "(");
			sqlColStr = sqlColStr.replace("]", ")");
			sql.append(tableName).append(" ").append(sqlColStr).append(" values (");
			
			int sqlColLen = sqlCol.size();
			int lastInt = 0;
			for (int i = 0; i < sqlColLen; i++) {
				lastInt = sqlColLen - 1;
				if (i == lastInt) {
					sql.append(" ? )");
				} else {
					sql.append(" ?, ");
				}
			}
			
			List<Object[]> paramList = new ArrayList<Object[]>();
			int valListLen = valList.size();
			List<String> list = null;
			Object[] array = null;
			for (int i = 0; i < valListLen; i++) {
				list = valList.get(i);
				array = list.toArray();
				paramList.add(array);
			}
			
			batchUpdate = jdbcTemplate.batchUpdate(sql.toString(), paramList);
		}
		
		return batchUpdate;
	}
	
	/**
	 * 执行批量更新的SQL, LISTMAP的 MAP中值必须的tableName, 可选参数,pk指定根据哪些条件更新, update指定哪些pk需要更新
	 * @param paramList
	 * @return 返回 null 参数错误或程序错误, 长度为0,则是执行了0条, 正常情况为执行情况(行数)
	 * @throws Exception
	 */
	@Override
	public int[] updateBatch(List<Map<String, Object>> paramList) throws Exception {
		int[] batchUpdate = null;
		List<String> sqlList = this.getBatchSql(paramList, 2);
		if (sqlList != null) {
			String[] sqlArr = (String[]) sqlList.toArray();
			batchUpdate = jdbcTemplate.batchUpdate(sqlArr);
		}
		return batchUpdate;
	}
	
	/**
	 * 批量更新
	 * @param colList	需要更新的列
	 * @param conditionList		需要更新的条件,所有条件都是and组装
	 * @param tableName	 表名
	 * @param valList	列值+条件值,按顺序放入
	 * @return
	 * @throws Exception
	 */
	public int[] updateBatch(List<String> colList, List<String> conditionList,
			String tableName, List<List<String>> valList) throws Exception {
		int[] batchUpdate = null;
		StringBuffer sql = new StringBuffer("update ");
		if (colList != null && colList.size() > 0 && tableName != null && valList != null && valList.size() > 0) {
			String sqlColStr = colList.toString();
			sqlColStr = sqlColStr.replace("[", "(");
			sqlColStr = sqlColStr.replace("]", ")");
			sql.append(tableName).append(" set ");
			 
			int sqlColLen = colList.size();
			int lastInt = 0;
			for (int i = 0; i < sqlColLen; i++) {
				lastInt = sqlColLen - 1;
				
				String colStr = colList.get(i);
				if (i == lastInt) {
					sql.append(colStr).append("= ? ");
				} else {
					sql.append(colStr).append("= ?, ");
				}
			}
			
			// 处理条件
			if (conditionList != null && conditionList.size() > 0) {
				sql.append(" where ");
				int conditionListLen = conditionList.size();
				for (int i = 0; i < conditionListLen; i++) {
					String conditionStr = conditionList.get(i);
					sql.append(" and " + conditionStr + " = ? ");
				}
			}
			
			// 将参数转化为List<Object[]> 
			List<Object[]> paramList = new ArrayList<Object[]>();
			int valListLen = valList.size();
			List<String> list = null;
			Object[] array = null;
			for (int i = 0; i < valListLen; i++) {
				list = valList.get(i);
				array = list.toArray();
				paramList.add(array);
			}
			batchUpdate = jdbcTemplate.batchUpdate(sql.toString(), paramList);
		}
		
		return batchUpdate;
	}
	
	public List<Map<String, Object>> selectPagenation(String tableName,
			Object[] selectFields, String whereFilter, Object[] params,
			Object[] groups, Object[] orderFields, int orderType,
			long pageSize, long pageIndex) throws Exception {
		if (StringUtil.isNull(tableName)) {
			log.info("分页查询错误：数据表名为空！");
			throw new IllegalArgumentException("the table name cannot be is null!");
		}
		
		if (StringUtil.isNull(whereFilter)) {
			if (whereFilter.contains("?") && CommonUtil.isEmpty(params)) {
				log.info("分页查询错误：过滤条件必须与参数一起使用！");
				throw new IllegalArgumentException( "the query parameters cannot be is null!");
			}
		}

		String sql = getSQLPageination(tableName, selectFields, whereFilter,
				groups, orderFields, orderType, pageSize, pageIndex);
		
		log.info("分页查询：" + sql);
		
		if (pageIndex != 1 && !CommonUtil.isEmpty(params)) {
			Object[] param = new Object[params.length * 2];
			System.arraycopy(params, 0, param, 0, params.length);
			System.arraycopy(params, 0, param, params.length, params.length);
			params = param;
			CommonUtil.offObject(param);
		}
		return jdbcTemplate.queryForList(sql, params);
	}

	private String getSQLPageination(String tableName, Object[] selectFields,
			String whereFilter, Object[] groups, Object[] orderFields,
			int orderType, long pageSize, long pageIndex) throws Exception {
		String tempSelect = " ";
		String tempOrderFields = " ";// 排序字段
		String tempOrderSelect = " ";
		String tempOrderBy = " ";
		String tempGroups = null;

		/** 拼接查询字段 */
		if (!CommonUtil.isEmpty(selectFields)) {
			tempSelect = ListMapUtil.arrayToString(selectFields,
					CommonUtil.SPLIT_COMMA);
		}
		/** 拼接分组 */
		if (!CommonUtil.isEmpty(groups)) {
			tempGroups = ListMapUtil.arrayToString(groups, CommonUtil.SPLIT_COMMA);
		}
		/** 接排序字段 */
		if (!CommonUtil.isEmpty(orderFields)) {
			tempOrderFields = ListMapUtil.arrayToString(orderFields, CommonUtil.SPLIT_COMMA);
		}
		/** 拼接排序 */
		if (orderType == 0) {// 升序
			tempOrderSelect = " >(select max ";
			tempOrderBy = " order by " + tempOrderFields + " asc ";
		} else {
			tempOrderSelect = " <(select min ";
			tempOrderBy = " order by " + tempOrderFields + " desc ";
		}

		StringBuffer sql = new StringBuffer("select top ");
		sql.append(pageSize + " ");
		sql.append(tempSelect);
		if (!CommonUtil.isEmpty(groups)) {
			if (!StringUtil.isNull(tempSelect)) {
				sql.append(",");
			}
			sql.append(tempGroups);
		}
		sql.append(" from ");
		sql.append(tableName);
		sql.append(" where ");

		if (pageIndex == 1) {
			sql.append(whereFilter);
			if (!CommonUtil.isEmpty(groups)) {
				sql.append(" group by ");
				sql.append(tempGroups);
			}
			sql.append(tempOrderBy);
			return sql.toString();
		}

		sql.append(tempOrderFields);
		sql.append(tempOrderSelect);
		sql.append(" ( ");
		sql.append(tempOrderFields);
		sql.append(" ) ");
		sql.append("from ( select top ");
		sql.append((pageIndex - 1) * pageSize);
		sql.append(" " + tempOrderFields);
		if (!CommonUtil.isEmpty(groups)) {
			sql.append(",");
			sql.append(tempGroups);
		}
		sql.append(" from ");
		sql.append(tableName);
		if (!StringUtil.isNull(whereFilter)) {
			sql.append(" where ");
			sql.append(whereFilter);
			if (!CommonUtil.isEmpty(groups)) {
				sql.append(" group by ");
				sql.append(tempGroups);
			}
			sql.append(tempOrderBy);
			sql.append(" ) as tblTmp) and ");
			sql.append(whereFilter);
		} else {
			sql.append(tempOrderBy).append(") as tblTmp)");
		}
		if (!CommonUtil.isEmpty(groups)) {
			sql.append(" group by ");
			sql.append(tempGroups);
		}
		sql.append(tempOrderBy);
		CommonUtil.offObject(tempSelect, tempGroups, tempOrderFields,
				tempOrderSelect, tempOrderBy, sql);
		return sql.toString();
	}

	/**
	 * 分页查询 -- 适用表无任何ID,或主键时查询
	 * <br />
	 * 适用于任何分页查询,可能会存在效率问题
	 * <br />
	 * 如果数字需要正常排序,请使用cast(field as int)的方式排序
	 * @param sql 需要查询的SQL语句,正常SQL语句即可
	 * @param pageNumber 页号
	 * @param pageSize	条数
	 * @return 返回null 则未查询到任何数据,否则为listmap数据格式
	 * @throws Exception 可能有数据连接异常,sql语句错误异常
	 */
	public List<Map<String, Object>> selectPagenation(String sql, int pageNumber, int pageSize, Object... args) throws Exception {
		StringBuffer pageSql = new StringBuffer(" select * from(select a.*,row_number() over ( ");
		String pageFromSql = " ) as rownum from ( ";
		String pageNumberSql = " ) a ) b where rownum between ";
		
		// 将order by 语句和主句分享
		String realSql = null;
		String orderBySql = null;
		if (sql != null && !"".equals(sql.trim())) {
			if (sql.indexOf("order") != -1 && sql.indexOf("by") != -1 
					|| (sql.indexOf("ORDER") != -1 && sql.indexOf("BY") != -1)) {
				int sqlLen = sql.length();
				int indexOf = sql.indexOf("order");
				realSql = sql.substring(0, indexOf);
				orderBySql = sql.substring(indexOf, sqlLen);
			}
		}
		
		// 拼接新的SQL语句
		pageSql.append(orderBySql).append(pageFromSql).append(realSql)
			.append(pageNumberSql).append((pageNumber-1)*pageSize+1).append(" and ").append(pageNumber*pageSize);
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(pageSql.toString(), args);
		return queryForList;
	}
	
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext("config/spring/applicationContext.xml");
		JdbcTemplate jdbcTemplate1 = (JdbcTemplate) context.getBean("jdbcTemplate");
		
		CommonDaoImpl cdi = new CommonDaoImpl();
		cdi.setJdbcTemplate(jdbcTemplate1);
		
		String sql = "select * from t_user";
		
		String date2String = DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
		System.err.println(date2String);
		List<Map<String, Object>> selectList = cdi.selectList(sql, null);
		System.err.println(selectList);
		System.err.println(DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
	
}
