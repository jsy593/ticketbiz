package com.jsy.develop.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jsy.util.common.CommonUtil;

/**
 * session管理器
 *  <br />
 * 提供不传入response,request直接请求,这些直接从请求响应管理栈中取得
 *
 * @author 张强 2014年9月22日 下午3:31:08
 * @version 0.0.1
 */
public class SessionUtil {
	private static Log log = LogFactory.getLog(SessionUtil.class);
	
	/**
	 * 获取所有session名称列表
	 * @param request 请求
	 * @return 返回List(String)类型的LIST
	 */
	public List<String> getAllSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		
		List<String> sessionList = new ArrayList<String>();
		while (attributeNames.hasMoreElements()) {
			String sessionName = (String) attributeNames.nextElement();
			sessionList.add(sessionName);
		}
		
		return sessionList;
	}
	
	/**
	 * 获取单个session
	 * 
	 * @param request 请求
	 * @param key KEY值
	 * @return 根据此KEY值,获取的session值
	 */
	public Object getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return session.getAttribute(key);
	}
	
	/**
	 * 添加单个session
	 * 
	 * @param request 请求
	 * @param key KEY值
	 * @param value VAL值
	 */
	public void addSession(HttpServletRequest request, String key, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}
	
	/**
	 * 批量添加session
	 * 
	 * @param request 请求
	 * @param sessionList LISTMAP session串,一个MAP,只可以有一对值
	 */
	public void addSessionList(HttpServletRequest request, List<Map<String, Object>> sessionList) {
		if (CommonUtil.isEmpty(sessionList)) {
			log.error("sessionList为空,");
			sessionList = new ArrayList<Map<String,Object>>();
		}
		
		HttpSession session = request.getSession();
		
		int sizeLen = sessionList.size();
		Map<String, Object> sessionMap = null;
		for (int i = 0; i < sizeLen; i++) {
			sessionMap = sessionList.get(i);
			
			Iterator<Entry<String, Object>> iterator = sessionMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				session.setAttribute(next.getKey(), next.getValue());
			}
		}
	}
	
	// 以下为不需要request,response, 直接从请求响应栈中获取
	
	/**
	 * 获取所有session名称列表
	 * @param request 请求
	 * @return 返回List(String)类型的LIST
	 */
	public List<String> getAllSession() {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		
		List<String> sessionList = new ArrayList<String>();
		while (attributeNames.hasMoreElements()) {
			String sessionName = (String) attributeNames.nextElement();
			sessionList.add(sessionName);
		}
		
		return sessionList;
	}
	
	/**
	 * 获取单个session
	 * 
	 * @param request 请求
	 * @param key KEY值
	 * @return 根据此KEY值,获取的session值
	 */
	public Object getSession(String key) {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		HttpSession session = request.getSession();
		return session.getAttribute(key);
	}
	
	/**
	 * 添加单个session
	 * 
	 * @param request 请求
	 * @param key KEY值
	 * @param value VAL值
	 */
	public void addSession(String key, String value) {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}
	

	/**
	 * 添加单个session
	 * 
	 * @param request 请求
	 * @param key KEY值
	 * @param map map对象
	 */
	public void addSessionMap(String key,Map<String, Object> map) {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		HttpSession session = request.getSession();
		session.setAttribute(key, map);
	}
	
	/**
	 * 批量添加session
	 * 
	 * @param request 请求
	 * @param sessionList LISTMAP session串,一个MAP,只可以有一对值
	 */
	public void addSessionList(List<Map<String, Object>> sessionList) {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		if (CommonUtil.isEmpty(sessionList)) {
			log.error("sessionList为空,");
			sessionList = new ArrayList<Map<String,Object>>();
		}
		
		HttpSession session = request.getSession();
		
		int sizeLen = sessionList.size();
		Map<String, Object> sessionMap = null;
		for (int i = 0; i < sizeLen; i++) {
			sessionMap = sessionList.get(i);
			
			Iterator<Entry<String, Object>> iterator = sessionMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				session.setAttribute(next.getKey(), next.getValue());
			}
		}
	}
}
