package com.jsy.develop.web;

import com.jsy.util.common.MailUtil;

/**
 * 百居易meil扩展
 * @author 陈建伟
 * @datetime 2015年8月25日下午2:27:06
 */
public class JsyMeilDevelop {

	private static String meilServer = "smtp.exmail.qq.com"; // 邮件服务器
	private static String meilAdd = "system@862.com.cn"; // 邮箱地址
	private static String meilUserName = "system@862.com.cn"; // 用户名
	private static String meilPassword = "ly123456"; // 密码
	
	
	public JsyMeilDevelop() {
		// 读取配置文件初始化
		
	}
	
	
	/**
	 * 邮箱验证
	 * @param email 目标邮箱
	 * @param content 发送内容
	 * @return 是否发送成功
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月25日下午2:37:35 新建该方法</li>
	 * </ul>
	 */
	public static boolean sendCheckMail(String email, String content){
		return MailUtil.send(JsyMeilDevelop.meilServer, JsyMeilDevelop.meilAdd, email, "收发宝邮箱验证", content, JsyMeilDevelop.meilUserName, JsyMeilDevelop.meilPassword);
	}
	
}
