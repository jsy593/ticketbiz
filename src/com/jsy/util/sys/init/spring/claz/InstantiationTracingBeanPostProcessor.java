package com.jsy.util.sys.init.spring.claz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 打印spring加载时的bean信息
 * 
 * @author 张强 2014年6月10日 下午3:27:25
 * @version 0.0.1
 */
public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {
	Log log = LogFactory.getLog(InstantiationTracingBeanPostProcessor.class);
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
//		log.info("Bean名称>>>>>>>>>>>>" + beanName);
        return bean;
	}
}
