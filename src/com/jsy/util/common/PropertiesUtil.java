package com.jsy.util.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * prop文件util
 * @author 陈建伟
 * @datetime 2015年8月4日上午10:48:38
 */
public class PropertiesUtil {

	private Logger log = Logger.getLogger(PropertiesUtil.class);
	
	private Properties props;
	private String fileName;
	
	public PropertiesUtil() {
		
	}
	
	public PropertiesUtil(String fileName) {
		this.fileName = fileName;
		this.readProperties(fileName);
	}
	
	/**
	 * 读取文件
	 * @param fileName
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月4日上午10:50:08 新建该方法</li>
	 * </ul>
	 */
	private void readProperties(String fileName){
		try {
			props = new Properties();
			InputStream  fis = new BufferedInputStream (new FileInputStream(PropertiesUtil.class.getResource("/").getPath()+fileName));
			props.load(fis);
		} catch (Exception e) {
			this.log.error(e,e);
		}
	}
	
	/**
	 * 获取某个属性值
	 * @param key
	 * @return
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月4日上午10:54:11 新建该方法</li>
	 * </ul>
	 */
	public String getProperty(String key){
		return this.props.getProperty(key);
	}
	
	/**
	 * 获取该属性文件的所有值
	 * @return
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月4日上午10:55:40 新建该方法</li>
	 * </ul>
	 */
	public Map<String, String> getAllProperty(){
		Map<String, String> result = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		Enumeration<String> enu = (Enumeration<String>) props.propertyNames();
		
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = this.props.getProperty(key);
			
			result.put(key, value);
		}
		
		return result;
	}
	
	/**
	 * 更新或者插入
	 * @param key
	 * @param value
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月4日上午11:01:19 新建该方法</li>
	 * </ul>
	 */
	public void writeProperties(String key, String value){
		try {
			OutputStream fos = new FileOutputStream(this.fileName);
			this.props.setProperty(key, value);
			// 写入
			this.props.store(fos, "update "+key);
		} catch (Exception e) {
			this.log.error(e,e);
		}
	}
}
