package com.jsy.util.sys.init.spring.xml;

//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration.Dynamic;
//import javax.servlet.SessionCookieConfig;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import cn.thrdmuseum.util.sys.init.encoding.EncodingDispatcherServlet;

/**
 * WEB容器初始化,用于替代web.xml
 * <br />
 * 如果需要配置listener,servlet,mapping等,使用onStartup方法中的servletContext进行配置
 * 
 * @author 张强 2014年6月10日 下午2:28:23
 * @version 0.0.1
 * 
 * @see EncodingDispatcherServlet
 */
//public class WebInitializer implements WebApplicationInitializer {

//	@Override
//	public void onStartup(ServletContext servletContext)
//			throws ServletException {
//		// spring
//		// 配置加载初始化参数
//		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
//
//		// 配置初始化路径
//		appContext.setConfigLocation("classpath:spring/springmvc-configall.xml");
//
//		// 配置转发及监听路径
//		Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(appContext));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//		
//		// 配置全局字符集成处理,主要是get请求的中文集符,详见EncodingDispatcherServlet
//		Dynamic springmvc = servletContext.addServlet("springmvc", new EncodingDispatcherServlet());
//		springmvc.setInitParameter("encoding", "UTF-8");
//		
//		// session
//		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
//		sessionCookieConfig.setMaxAge(15);
//		
//	}
//}
