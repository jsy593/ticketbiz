package com.jsy.util.sys.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * WEB下的ApplicationContext管理类, 管理手动获取Bean -- 适用于WEB,仅WEB启动状态下
 * <br />
 * 可手动指定哪些组件需要成为spring的bean,然后不需要new对象获取实体对象
 * <br />
 * compoent, service等注解
 * @author 张强 2014年6月10日 下午10:50:26
 * @version 0.0.1
 */
public class ContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;	// 启动spring时注入

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}
	
	/**
	 * 获取原生的ApplicationContext
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 获取单个bean
	 * @param beanName 需要的bean名称
	 * @return 返回实体对象
	 * @throws Exception 如果不包含此bean则返回null,如果applicationContext为空,抛出此异常
	 */
	public static Object getBean(String beanName) throws Exception {
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 是否包含某个bean
	 * @param beanName 需要的bean名称
	 * @return 包含true, 不包含false
	 * @throws Exception 如果applicationContext为空,抛出此异常
	 */
	public static boolean containsBean(String beanName) throws Exception {
		return applicationContext.containsBean(beanName);
	}
	
	/**
	 * 获取所有的Bean名称
	 * @return 返回内存中,spring管理的所有bean名称
	 * @throws Exception 如果applicationContext为空,抛出此异常
	 */
	public static List<String> getBeanNames() throws Exception {
		List<String> reBeanList = new ArrayList<String>();
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		reBeanList = Arrays.asList(beanDefinitionNames);
		return reBeanList;
	}
}
