package com.jsy.business.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsy.business.ISystem;
import com.jsy.dao.common.ICommonDao;

@Service
public class SystemImpl implements ISystem{
		
	private Log logger = LogFactory.getLog(SystemImpl.class);
	
	@Autowired
	private ICommonDao commonDaoImpl;
	
	@Transactional
	@Override
	public synchronized long getNextKey(String tableName) {
		try {
			String sql = "UPDATE t_commSequence SET id = LAST_INSERT_ID(id + 1) WHERE name = '"
					+ tableName + "'";
			String sql2 = "select LAST_INSERT_ID()";
			commonDaoImpl.getJdbcTemplate().update(sql);
			return commonDaoImpl.getJdbcTemplate().queryForObject(sql2, Long.class);
		} catch (Exception e) {
			logger.error("获得对象的索引号错误:" + e.getMessage());
			return (long) 0;
		}
	}
	

	@Override
	public Long createTableWithSysName(String systemName) {
		Long index=this.getNextKey("t_access_system");
		if(0==index){
			return 0L;
		}
		
		Properties p = new Properties ();
		FileInputStream fis;
		try {
			String preSql = "update t_access_system set systemIndex="+index+" where systemName='"+systemName+"'";
			commonDaoImpl.getJdbcTemplate().update(preSql);
			fis = new FileInputStream(SystemImpl.class.getResource("/").getPath()+"/baseSql.xml");
			p.loadFromXML(fis);
			for (Object key : p.keySet()) {
				String tbName= key.toString();
				String sql = p.getProperty(tbName);
				StringBuilder sbStr = new StringBuilder();
				sbStr.append("t_sys");
				sbStr.append(index);
				sbStr.append(tbName.substring(1, tbName.length()));
				sql=sql.replaceAll(tbName, sbStr.toString());
				commonDaoImpl.getJdbcTemplate().execute(sql);
				//System.out.println(sql);
				//System.out.println(sbStr);
			}
			
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (InvalidPropertiesFormatException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (DataAccessException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return index;
	}
		
	
}
