package com.jsy.util.common;

/**
 * 自定义异常，用于发送短信
 * 
 * @author 刘一洋
 * 
 */
public class SmsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmsException() {
		super();
	}

	public SmsException(String message) {
		super(message);
	}

}
