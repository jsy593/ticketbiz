package com.jsy.dao.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 数据持久操作接口,基于spring jdbc
 * 
 * @author 张强 2014年6月16日
 */
@Repository
public interface ICommonDao {

	/**
	 * 单个实体插入 -- 返回数据库自增长主键
	 * 
	 * @param dataMap MAP中的key,为字段,value为值,需要与数据库中的对应
	 * @param tableName 表名
	 * @return 返回主键值, null 或者异常为错误
	 * @throws Exception
	 */
	public Integer insertOne(Map<String, Object> dataMap, String tableName) throws Exception;
	
	/**
	 * 单个实体插入 -- 返回操作结果(不返回主键)
	 * 
	 * @param dataMap MAP中的key,为字段,value为值,需要与数据库中的对应
	 * @param tableName 表名
	 * @return 返回操作代码, 0以上为操作成功影响的行数,null 或者异常为错误
	 * @throws Exception
	 */
	public Integer insertManual(Map<String, Object> dataMap, String tableName) throws Exception;

	/**
	 * 单个数据更新操作
	 * 
	 * @param dataMap
	 *            MAP中的key,为字段,value为值,需要与数据库中的对应 <br />
	 *            需要指定哪一个是主键,主键作为更新条件,MAP的KEY放入"pk",value对应主键值,即可指定, value可指定多个,
	 *            用,号分开 <br />
	 *            当主键也需要将值更新时,MAP的KEY放入"update",即可指定哪些主键值也可更新,用,分开,可指定多个 <br />
	 *            所有用,号分开的字符,请不要在后面(或前面)有空格<br />
	 * @param tableName 表名
	 * @return 返回更新影响条数,小于1,则表示不成功,负数为异常
	 * @throws Exception
	 */
	public Integer updateOne(Map<String, Object> dataMap, String tableName) throws Exception;
	
	public int[] updateBatch(List<Map<String, Object>> paramList) throws Exception;
	
	/**
	 * 删除单条数据-根据MAP <br />
	 * 
	 * @param dataMap 需要删除的条件, key列名,value值, 全部条件用And拼接
	 * @param tableName 表名
	 * @return 返回操作执行结果,0为0行,0以上为执行结果数,-1为错误
	 * @throws Exception
	 */
	public Integer deleteOne(Map<String, Object> dataMap, String tableName) throws Exception;
	
	/**
	 * 批量删除数据
	 * 
	 * @param paramList  删除的参数列表,与删除条件对应
	 * @param deletefield 删除条件,仅一个
	 * @param tableName 删除的表名
	 * @return 返回删除结果或影响行数,0为影响0行, 负数为错误
	 * @throws Exception
	 */
	public Integer deleteBatch(List<String> paramList, String deletefield, String tableName) throws Exception;
	
	/**
	 * 多条件,多参数批量删除
	 * 
	 * @param paramList   删除的参数列表,要与条件字段对应数组
	 * @param fieldLsit  删除的条件字段集合
	 * @param tableName 删除的表名
	 * @return 返回删除结果或影响行数,0为影响0行, 负数为错误
	 * @throws Exception
	 */
	public Integer deleteBatch(List<String> paramList, List<String> fieldLsit, String tableName) throws Exception;

	/**
	 * 查询数据 -- 多个实体<br />
	 * entityClazz可以为视图或者是多表联接的查询,不限制,参数放入的时候必须和SQL拼接的?号顺序一致 <br />
	 * 
	 * @param sql
	 *            [是必须的]SQL语句格式如下: select a, b, c, from tableName where ano = ?
	 *            and bno = ?; <br >
	 *            <strong>注意:</strong>如果拼接是like时,%号或者_号,必须在参数传入前就拼接好, 如:select
	 *            a, b, c, from tableName where ano like ? and bno like ?;
	 *            那么List paramList = new ArraysList()<br />
	 *            paramList.add("%" + ano + "%");
	 * @param params 参数必须的?号对应,无参数可为null
	 * @param entityClazz
	 *            对应视图,可单表,多表视图;
	 * @return 返回执行结果,值为Integer, 1以上表示影响行数, 0为影响0行, 负数则为失败!
	 * @throws Exception
	 */
	public List<?> selectObject(String sql, List<Object> params, Class<?> entityClazz) throws Exception;

	/**
	 * 查询数据 多条数据,为listmap
	 * 
	 * @param sql 完整SQL
	 * @param params 对应的参数列表
	 * @return 返回一个List对象,一个对象为一个MAP,对应一个实体
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList(String sql, List<Object> params) throws Exception;
	
	/**
	 * 查询数据 多条数据,为listmap
	 * 
	 * @param sql 完整SQL
	 * @param params 对应的参数列表
	 * @return 返回一个List对象,一个对象为一个MAP,对应一个实体
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectListSimple(String sql, Object... args) throws Exception;
	
	/**
	 * 获取查询数量总数
	 * 
	 * @param sql 查询SQL
	 * @param params
	 *            需要拼接的参数列表
	 * @return 返回查询的数据
	 * @throws Exception
	 */
	public Long selectTotalCount(String sql, List<Object> params) throws Exception;
	
	/**
	 * 获取数据库时间
	 * 
	 * @return Date 返回一个Date类型的日期(yyyy-MM-dd hh:MM:ss:mm);
	 * @throws Exception
	 */
	public Date selectDBTime() throws Exception;

	/**
	 * 获取原生SPRING JDBC操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public JdbcTemplate getJdbcTemplate() throws Exception;
	
	/**
	 * 查询单个数据
	 * 
	 * @param <T>
	 * @param sql 查询语句
	 * @param args 参数数组
	 * @param clazz 指定返回类型 如integer string 也可以为一个实体
	 * @return 返回单条单行数据
	 * @throws Exception
	 */
	public <T> T selectForObject(String sql, Object[] args, Class<T> clazz) throws Exception;

	/**
	 * 查询单条数据，返回单条多行数据
	 * 
	 * @param sql  查询语句
	 * @param arg  参数列表 传入LIST时请转换为数据list.toArray();
	 * @return 返回map类型，key是查询列名，value是具体值
	 * @throws Exception
	 */
	public Map<String, Object> selectForMap(String sql, Object... arg) throws Exception;

	/**
	 * 分页查询数据，每页显示15条数据
	 * 
	 * @param sql 查询语句
	 * @param o 条件参数
	 * @param pageNumber  当前页
	 * @return 返回Map中key为allCount保存的是记录总数，key为data保存的是查询数据
	 * @throws Exception
	 */
//	public Map<String, Object> selectPageMap(String sql, Object[] o, Integer pageNumber, Integer pageSize) throws Exception;
	
}
