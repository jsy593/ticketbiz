package com.jsy.develop.spring;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 请求,响应前置处理,将他们压入到管理栈中.
 *
 * @author 张强 2014年9月22日 下午3:55:22
 * @version 0.0.1
 */
public class ReqRespToThread extends HandlerInterceptorAdapter {
	
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(ReqRespToThread.class);

	@Override
	public boolean preHandle(HttpServletRequest request,  
			HttpServletResponse response, Object handler) throws Exception {
		
		
			return true;
		}

}
