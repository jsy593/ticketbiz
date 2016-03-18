package com.jsy.develop.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jsy.develop.web.CachePool;
import com.jsy.util.common.PropertiesUtil;


/**
 * spring 容器初始化完成事件
 * @author jsy
 * @datetime 2015年7月8日上午10:42:14
 */
public class SpringContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {

	private Log log = LogFactory.getLog(SpringContextRefreshedEvent.class);
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		log.debug("spring加载完成事件开始执行");
		
		String path = SpringContextRefreshedEvent.class.getResource("/").getPath();
		PropertiesUtil prop = new PropertiesUtil(path + "/base.properties");
		CachePool.baseProp = prop.getAllProperty();
		
		log.debug("spring 成功执行完");
	}

	
}