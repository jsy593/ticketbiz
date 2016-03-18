package com.jsy.controller.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.jsy.util.common.JsonUtil;

public class BaseController {
	protected Log log = LogFactory.getLog(BaseController.class);
	
	/**
	 * BaseController父类注入
	 */
	protected HttpServletRequest request = null;

	/**
	 * BaseController父类注入
	 */
	protected HttpServletResponse response = null;
	
	public static String title = "";
	
	/**
	 * 注入请求
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	protected void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json"); 
        /*设置字符集为'UTF-8'*/
		response.setCharacterEncoding("UTF-8");
		this.request = request;
		this.response = response;
	}
	
	
	/**
	 * BaseController 将JSON数据写出到页面
	 * <ul>
	 * 	<li></li>
	 * </ul>
	 * @param jsonStr 需要的JSON字符串
	 */
	protected void writeJson(Object jsonObj) {
		PrintWriter out = null;
		try {
			out = this.response.getWriter();
			String jsonStr = null;
			if (jsonObj instanceof String) {
				
				jsonStr = jsonObj.toString();
			} else {
				jsonStr = JsonUtil.ObjectToJson(jsonObj);
			}
			
			out.write(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("将结果JSON数据写出到页面时出错!", e);
		}
	}
	
	/**
	 * BaseController 将JSON数据写出到页面, 重载的一个方法,如果是JSON字符串,可以直接调这个方法
	 * <ul>
	 * 	<li></li>
	 * </ul>
	 * @param jsonStr 需要的JSON字符串
	 */
	protected void writeJsonStr(String jsonStr) {
		PrintWriter out = null;
		try {
			out = this.response.getWriter();
			out.write(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("将结果JSON数据写出到页面时出错!", e);
		}
	}
	
	/**
	 * 增 、删 、改时JSON 提示
	 *
	 * <ul>
	 * 	<li></li>
	 * </ul>
	 * @param altName     json KEY
	 * @param value	  json value
	 * @version 0.0.1
	 */
	protected void writeJson(String altName,Object value){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put(altName, value);
		
		String json = JsonUtil.ObjectToJson(map);
		writeJson(json);
	}
	
	/**
	 * 获取当前session
	 * 
	 * @return 返回session
	 */
	protected HttpSession getSession() {
		return request.getSession();
	}
	
	/**
	 * 获取某个session值列表
	 * @param key 需要获取的session
	 * @return
	 */
	protected Object getAttribute(String key) {
		HttpSession session = getSession();
		return session.getAttribute(key);
	}
	
	/**
	 * 设置session值
	 * @param key 需要获取的session
	 */
	protected void setAttribute(String key, Object obj) {
		HttpSession session = getSession();
		session.setAttribute(key, obj);
	}
	
	/**
	 * 删除session值
	 * @param key 需要删除的session KEY
	 */
	protected void removeAttribute(String key) {
		HttpSession session = getSession();
		session.removeAttribute(key);
	}
	
	/**
	 * 生成 ModelandView  供返回页面 
	 * @param page
	 * @return
	 * @date 2015年7月24日上午10:42:51
	 */
	
	protected ModelAndView forwardPage(String page) {
		return new ModelAndView(page);
	}

}
