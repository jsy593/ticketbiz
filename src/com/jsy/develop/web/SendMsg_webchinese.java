package com.jsy.develop.web;

import java.util.HashMap;
import java.util.Map;

import com.jsy.util.common.HttpUtil;



public class SendMsg_webchinese {

	private static final String Uid = "y.liu";

	private static final String Key = "057b5d9348e29358b4ec";
	//密码为 635497
	
	
	/**
	 * 发送注册验证码
	 * @param smsMob
	 * @param validationCode
	 * @author 陈建伟
	 * <ul>
	 *  <li>陈建伟 2015年8月17日下午4:43:42 新建该方法</li>
	 * </ul>
	 */
	public static String sendMsg(String smsMob, String content){
		HttpUtil util = new HttpUtil();
		Map<String, String> params = new HashMap<String, String>();
		params.put("Uid", SendMsg_webchinese.Uid);
		params.put("Key", SendMsg_webchinese.Key);
		params.put("smsMob", smsMob);
		params.put("smsText", content);
		Map<String, Object> result = util.sendPostMapRequest("http://utf8.sms.webchinese.cn/", params, "utf-8");
		return result.get("respContent").toString();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		/*SendSms sms = new SendSms();
		sms.setPhone("18716648769");
		sms.setContent("收得到验证码撒。。。。。【百居易电子商务】");
		try {
			SendMsg_webchinese.sendSms(sms);
		} catch (SmsException e) {
			e.printStackTrace();
		}*/
		
		HttpUtil util = new HttpUtil();
		Map<String, String> params = new HashMap<String, String>();
		params.put("Uid", "y.liu");
		params.put("Key", "057b5d9348e29358b4ec");
		params.put("smsMob", "18716648769");
		params.put("smsText", "可以收到不？【百居易电子商务】");
		Map<String, Object> result = util.sendPostMapRequest("http://utf8.sms.webchinese.cn/", params, "utf-8");
	}
}
