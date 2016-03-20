package com.jsy.business;

import org.springframework.stereotype.Service;

/**
 * 
 * @author jsy
 * @date 2015年9月14日上午11:39:05
 * 系统接入
 */

@Service
public interface ISystem {

	public Long createTableWithSysName(String systemName);

	long getNextKey(String tableName);
	
}
