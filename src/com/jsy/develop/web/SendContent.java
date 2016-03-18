package com.jsy.develop.web;


/**
 * 发送验证码、邮件内容
 * @author 陈建伟
 * @datetime 2015年8月21日下午2:06:17
 */
public class SendContent {

	/**
	 * 发送注册时的短信验证
	 * @param validatCode 验证码
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月21日下午2:08:42 新建该方法</li>
	 * </ul>
	 */
	public static String getRegistContent_sms(String validatCode){
		StringBuffer result = new StringBuffer("");
		result.append("您的验证码为：");
		result.append(validatCode);
		result.append(" 【百居易电子商务】");
		
		return result.toString();
	}
	
	
	/**
	 * 注册邮箱验证
	 * @param userName 注册用户名
	 * @param email 注册邮箱
	 * @param host 主机
	 * @param url 点击链接验证地址
	 * @return
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月25日下午2:52:40 新建该方法</li>
	 * </ul>
	 */
	public static String getValidateEmailContent_mail(String userName, String email, String host, String url){
		StringBuffer  sb=new StringBuffer();
		sb.append("<div style=\"width:680px; height:750px; border:1px #000 solid;margin:30px;\">");
		sb.append("<div style=\"border-bottom:1px #000 solid; padding:0px 0px 5px 20px;\"><img src=\"http://192.168.1.61:8080/wlsc/resources/images/logo-2.png\" width=\"135\" height=\"78\" /></div>");
		sb.append("<div style=\"margin:10px; font-size:12px; height:550px;\">");
		sb.append("<p style=\"font-size:14px;\">尊敬的"+userName+"您好：</p>");
		sb.append("<h3 style=\"text-align:center;\">感谢您注册发哪儿(www.862.com.cn)</h3>");
		sb.append("<h3 style=\"text-align:center;\">我们将为您提供最贴心的服务，祝您使用愉快！</h3>");
		sb.append("<img src=\""+host+"resources/images/line.png\" height=\"3\" width=\"660\" />");
		sb.append("<p style=\"float:left; width:300px;\">您的用户名："+userName+"</p>");
		sb.append("<p style=\"float:left; width:300px;\">您的注册邮箱："+email+"</p>");
		sb.append("<div style=\"clear:both;\"></div>");
		sb.append("<div style=\"border:1px #F6F solid; *margin-top:30px; background:#FCF; line-height:25px;padding-left:5px;\">安全提示：</div>");
		sb.append("<p>为了您的账户安全，我们建议您点击以下链接验证邮箱：</p>");
		sb.append("<a href=\""+url+"\" >"+url+"</a>");
		sb.append("<p>请在24小时内点击该链接，您也可以将链接复制到浏览器地址栏访问。</p>");
		sb.append("<img src=\""+host+"resources/images/line.png\" height=\"3\" width=\"660\" />");
		sb.append("<p>邮件中包含您的个人信息，建议您保管好本邮件！</p>");
		sb.append("<p>您之所以收到这封邮件，是因为您曾经注册成为发哪儿的用户。<br/>本邮件由发哪儿系统自动发出，请勿直接回复！<br/>在使用中遇到任何问题，请点击 帮助中心。<br/>如果您有任何疑问或建议，请联系我们4000-800-862。</p></div>");
		sb.append("<div style=\" text-align:center;\"><img src=\""+host+"resources/images/ziseImgs/logo.png\" width=\"138\" height=\"58\" /></div>");
		sb.append("<div style=\" text-align:center; color:#333; line-height:30px; font-size:14px;\">Copyright @ 2010 www.862.com.cn All Rights Reserved.百居易公司 版权所有</div></div>");
		return sb.toString();
	}
}
