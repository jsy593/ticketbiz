package com.jsy.util.common;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 短信工具包
 * 
 * @author 刘一洋
 * 
 */
public class SendSmsUtils {

	private static final String reg = "101100-WEB-HUAX-822168";

	private static final String pwd = "MXWSUKHZ";
	

	/**
	 * 查询余额
	 * 
	 * @return Sms
	 */
	public static Sms queryBalance() {

		String url = "http://www.stongnet.com/sdkhttp/getbalance.aspx?reg="
				+ reg + "&pwd=" + pwd;

		String r = exec(url);

		return buildSms(buildMap(r));

	}

	/**
	 * 注销账户( * 慎用)
	 * 
	 * @return boolean 是否注销成功
	 */
	public static boolean unReg() throws SmsException {

		String url = "http://www.stongnet.com/sdkhttp/getbalance.aspx?reg="
				+ reg + "&pwd=" + pwd;

		String r = exec(url);

		Sms sms = buildSms(buildMap(r));

		return result(sms);

	}

	/**
	 * 修改密码
	 * 
	 * @param newPwd
	 *            新的密码
	 * @return boolean 是否修改成功
	 */
	public static boolean uptPwd(String newPwd) throws SmsException {

		String url = "http://www.stongnet.com/sdkhttp/uptpwd.aspx?reg=" + reg
				+ "&pwd=" + pwd + "&newpwd=" + newPwd;

		String r = exec(url);

		Sms sms = buildSms(buildMap(r));

		return result(sms);

	}

	/**
	 * 查询发送报告
	 * 
	 * @return Sms
	 */
	public static Sms queryMtreport() {

		String url = "http://www.stongnet.com/sdkhttp/getmtreport.aspx?reg="
				+ reg + "&pwd=" + pwd;

		String r = exec(url);

		return buildSms(buildMap(r));

	}

	/**
	 * 发送及时短信
	 * 
	 * @param sms
	 *            Sms
	 * @return boolean 发送是否成功
	 */
	public static boolean sendSms(Sms sms) throws SmsException {

		String url = "http://www.stongnet.com/sdkhttp/sendsms.aspx?reg=" + reg
				+ "&pwd=" + pwd + "&sourceadd=" + sms.getSourceadd()
				+ "&phone=" + sms.getPhone() + "&content=" + sms.getContent();

		String r = exec(url);

		return result(buildSms(buildMap(r)));

	}

	/**
	 * 发送定时短信
	 * 
	 * @param sms
	 *            Sms
	 * @return boolean 发送是否成功
	 */
	public static boolean sendsChSms(Sms sms) throws SmsException {

		String url = "http://www.stongnet.com/sdkhttp/sendsms.aspx?reg=" + reg
				+ "&pwd=" + pwd + "&tim=" + sms.getTim() + "&phone="
				+ sms.getPhone() + "&content=" + sms.getContent();

		String r = exec(url);

		return result(buildSms(buildMap(r)));

	}

	/**
	 * 上行短信推送
	 * 
	 * @param sms
	 *            Sms
	 * @return boolean 发送是否成功
	 */
	public static boolean testRecvMo(Sms sms) throws SmsException {

		String url = "http://www.stongnet.com/sdkhttp/TestRecvMo.aspx?phone="
				+ sms.getPhone() + "&source=" + sms.getSource() + "&motime="
				+ sms.getMotime() + "&content=" + sms.getContent();

		String r = exec(url);

		return result(buildSms(buildMap(r)));

	}

	/**
	 * exec方法
	 * 
	 * @param url
	 *            带参的链接
	 * @return 结果
	 */
	private static String exec(String url) {

		String result = null;

		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);

		ResponseHandler<String> handler = new BasicResponseHandler();

		try {

			result = client.execute(get, handler);

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		client.getConnectionManager().shutdown();

		return result;

	}

	/**
	 * 构造map
	 * 
	 * @param result
	 *            得到的结果
	 * @return map
	 */
	private static Map<String, String> buildMap(String result) {

		Map<String, String> map = new HashMap<String, String>();

		if (result == null || result.trim().length() < 1) {

			return map;

		}

		String[] kvs = result.split("&");

		for (String s : kvs) {

			String[] kv = s.split("=");

			if (kv.length == 2) {

				map.put(kv[0], kv[1]);

			}

		}

		return map;

	}

	/**
	 * 给Message的属性赋值
	 * 
	 * @param map
	 *            Map<String, String>
	 * @return Sms
	 */
	private static Sms buildSms(Map<String, String> map) {

		Sms sms = new Sms();

		Set<String> set = map.keySet();

		for (String key : set) {

			try {

				Method method = Sms.class.getMethod(
						"set" + StringJuger.charToUppercase(0, key),
						String.class);

				method.invoke(sms, map.get(key));

			} catch (SecurityException e) {

				e.printStackTrace();

			} catch (IllegalArgumentException e) {

				e.printStackTrace();

			} catch (NoSuchMethodException e) {

				e.printStackTrace();

			} catch (IllegalAccessException e) {

				e.printStackTrace();

			} catch (InvocationTargetException e) {

				e.printStackTrace();

			}
		}

		return sms;

	}

	/**
	 * 判断结果
	 * 
	 * @param Sms
	 *            sms
	 * @return boolean
	 */
	private static boolean result(Sms sms) throws SmsException {

		if (sms.getResult() != null && sms.getResult().trim().length() > 0
				&& sms.getResult().equals("0")) {

			return true;

		} else {

			throw new SmsException(sms.getMessage());

		}

	}

}
