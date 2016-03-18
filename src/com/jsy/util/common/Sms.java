package com.jsy.util.common;

/**
 * 短信实体类
 * 
 * @author 刘一洋
 * 
 */
public class Sms {

	private String reg;// 注册号(必填)

	private String pwd;// 密码(必填)

	private String sourceadd;// 子通道号(可填)

	private String tim;// 定时时间 格式：yyyy-mm-dd HH:MM:SS(必填)

	private String phone;// 手机号码(多个用逗号(,)隔开，最多1000个)(必填)

	private String content;// 短信内容(必填)

	private String result;// 结果码，0表示成功，其他表示失败

	private String message;// 结果码介绍

	private String smsid;// 短信包ID

	private String total;// 当天提交总数

	private String waitnum;// 等待发送的数量

	private String sendingnum;// 正在发送的数量

	private String sucessnum;// 发送成功的数量

	private String failnum;// 发送失败的数量

	private String balance;// 余额

	private String motime;// 手机上行短信时间，格式为：yyyy-MM-dd HH:mm:ss

	private String source;// 扩展码

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSourceadd() {
		return sourceadd;
	}

	public void setSourceadd(String sourceadd) {
		this.sourceadd = sourceadd;
	}

	public String getTim() {
		return tim;
	}

	public void setTim(String tim) {
		this.tim = tim;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSmsid() {
		return smsid;
	}

	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getWaitnum() {
		return waitnum;
	}

	public void setWaitnum(String waitnum) {
		this.waitnum = waitnum;
	}

	public String getSendingnum() {
		return sendingnum;
	}

	public void setSendingnum(String sendingnum) {
		this.sendingnum = sendingnum;
	}

	public String getSucessnum() {
		return sucessnum;
	}

	public void setSucessnum(String sucessnum) {
		this.sucessnum = sucessnum;
	}

	public String getFailnum() {
		return failnum;
	}

	public void setFailnum(String failnum) {
		this.failnum = failnum;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getMotime() {
		return motime;
	}

	public void setMotime(String motime) {
		this.motime = motime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("注册号：" + reg);

		sb.append("\r\n密码:" + pwd);

		sb.append("\r\n子通道号:" + sourceadd);

		sb.append("\r\n定时时间:" + tim);

		sb.append("\r\n手机号码:" + phone);

		sb.append("\r\n短信内容:" + content);

		sb.append("\r\n结果码:" + result);

		sb.append("\r\n结果码介绍:" + message);

		sb.append("\r\n短信包ID:" + smsid);

		sb.append("\r\n当天提交总数:" + total);

		sb.append("\r\n等待发送的数量:" + waitnum);

		sb.append("\r\n正在发送的数量:" + sendingnum);

		sb.append("\r\n发送成功的数量:" + sucessnum);

		sb.append("\r\n发送失败的数量:" + failnum);

		sb.append("\r\n余额:" + balance);

		return sb.toString();

	}

}
