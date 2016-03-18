package com.jsy.develop.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

import com.jsy.util.common.ConstantUtil;

/**
 * COOKIE管理工具类
 * <br />
 * 提供不传入response,request直接请求,这些直接从请求响应管理栈中取得
 *
 * @author 张强 2014年9月22日 下午2:52:15
 * @version 0.0.1
 */
public class CookieUtil extends CookieGenerator {
	
	/**
	 * 生成无名称Cookie 
	 * @param response 响应
	 * @param val COOKIE值
	 */
	public void generateCookie(HttpServletResponse response, String val) {
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieMaxAge(ConstantUtil.COOKIE_AGE);
		cookieGenerator.addCookie(response, val);
	}
	
	/**
	 * 生成Cookie 
	 * @param response 响应
	 * @param name COOKIE名
	 * @param val COOKIE值
	 */
	public void generateCookie(HttpServletResponse response, String name, String val) {
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName(name);
		cookieGenerator.setCookieMaxAge(ConstantUtil.COOKIE_AGE);
		cookieGenerator.addCookie(response, val);
	}
	
	/**
	 * 删除COOKIE, COOKIE名称,
	 * @param request 请求
	 * @param response 返回
	 * @param name COOKIE名
	 * @return true删除成功,false删除失败
	 */
	public boolean delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] allCookie = getAllCookie(request);
		int allCookieLen = allCookie.length;
		for (int i = 0; i < allCookieLen; i++) {
			String name2 = allCookie[i].getName();
			if (name.equals(name2)) {
				allCookie[i].setMaxAge(0);
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取单个cookie值
	 * @param request 请求
	 * @param name COOKIE名称
	 * @return 返回单COOKIE,即寻找到的COOKIE
	 */
	public Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] allCookie = getAllCookie(request);
		int allCookieLen = allCookie.length;
		for (int i = 0; i < allCookieLen; i++) {
			String name2 = allCookie[i].getName();
			if (name.equals(name2)) {
				return allCookie[i];
			}
		}
		return null;
	}
	
	/**
	 * 获取所有COOKIE
	 * @param request 请求
	 * @return 返回COOKIE数组
	 */
	public Cookie[] getAllCookie(HttpServletRequest request) {
		return request.getCookies();
	}
	
	// 以下为不需要传入HttpServletResponse 及 HttpServletRequest
	
	/**
	 * 生成无名称Cookie 
	 * @param response 响应
	 * @param val COOKIE值
	 */
	public void generateCookie(String val) {
		HttpServletResponse response = ReqRespUtil.instance().getResponse();
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieMaxAge(ConstantUtil.COOKIE_AGE);
		cookieGenerator.addCookie(response, val);
	}
	
	/**
	 * 生成Cookie 
	 * @param response 响应
	 * @param name COOKIE名
	 * @param val COOKIE值
	 */
	public void generateCookie(String name, String val) {
		HttpServletResponse response = ReqRespUtil.instance().getResponse();
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName(name);
		cookieGenerator.setCookieMaxAge(ConstantUtil.COOKIE_AGE);
		cookieGenerator.addCookie(response, val);
	}
	
	/**
	 * 删除COOKIE, COOKIE名称,
	 * @param request 请求
	 * @param response 返回
	 * @param name COOKIE名
	 * @return true删除成功,false删除失败
	 */
	public boolean delCookie(String name) {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		Cookie[] allCookie = getAllCookie(request);
		int allCookieLen = allCookie.length;
		for (int i = 0; i < allCookieLen; i++) {
			String name2 = allCookie[i].getName();
			if (name.equals(name2)) {
				allCookie[i].setMaxAge(0);
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取单个cookie值
	 * @param request 请求
	 * @param name COOKIE名称
	 * @return 返回单COOKIE,即寻找到的COOKIE
	 */
	public Cookie getCookie(String name) {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		Cookie[] allCookie = getAllCookie(request);
		int allCookieLen = allCookie.length;
		for (int i = 0; i < allCookieLen; i++) {
			String name2 = allCookie[i].getName();
			if (name.equals(name2)) {
				return allCookie[i];
			}
		}
		return null;
	}
	
	/**
	 * 获取所有COOKIE
	 * @param request 请求
	 * @return 返回COOKIE数组
	 */
	public Cookie[] getAllCookie() {
		HttpServletRequest request = ReqRespUtil.instance().getRequest();
		return request.getCookies();
	}
	
	
}
