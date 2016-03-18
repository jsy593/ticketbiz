package com.jsy.util.sys.init.encoding;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * <li>
 * 专门处理spring框架字符集,在配置DispatcherServlet时,配置字符处理
 * <li>
 * 对GET请求,在TOMCAT中进行处理URIEncoding="UTF-8"
 * <Connector port="8080" maxHttpHeaderSize="8192"    
               maxThreads="150" minSpareThreads="25" maxSpareThreads="75"    
               enableLookups="false" redirectPort="8443" acceptCount="100"    
               connectionTimeout="20000" disableUploadTimeout="true" URIEncoding="UTF-8" />  
 * @author 张强 2014年3月21日 11时22分
 *
 */
@SuppressWarnings("serial")
public class EncodingDispatcherServlet extends DispatcherServlet {
	private String encoding;

	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		super.init(config);
	}

	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(encoding);
		super.doService(request, response);
	}
}
