package com.jsy.develop.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 请求,响应管理栈
 *
 * @author 张强 2014年9月22日 下午3:50:20
 * @version 0.0.1
 */
public class ReqRespUtil {
	private static ReqRespUtil springReqResp;
	private ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();
	
	private ReqRespUtil() {}
	
	public static synchronized ReqRespUtil instance() {
		if (springReqResp == null) {
			springReqResp = new ReqRespUtil();
		}
		return springReqResp;
	}
	
	
	/**
	 * 设置HttpServletRequest对象 
	 * @param request
	 */
	public void setRequest(HttpServletRequest request) {
		this.requestLocal.set(request);
	}
	
	/**
	 * 设置HttpServletResponse对象
	 * @param response
	 */
	public void setResponse(HttpServletResponse response) {
		this.responseLocal.set(response);
	}
	
	/**
	 * 获得HttpServletRequest对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return this.requestLocal.get();
	}
	
	/**
	 * 获得HttpServletResponse对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return this.responseLocal.get();
	}
	
	/**
	 * 获得HttpSession对象
	 * @return
	 */
	public HttpSession getSession() {
		if(this.getRequest() != null) {
			return this.getRequest().getSession();
		} else {
			return null;
		}
	}
	
	/**
	 * 获得ServletContext对象
	 * @return
	 */
	public ServletContext getServletContext() {
		if(this.getSession() != null) {
			return this.getSession().getServletContext();
		} else {
			return null;
		}
	}
}
