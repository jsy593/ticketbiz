/**
 * Copyright © xxty Inc. All Rights Reserved.
 */
package com.jsy.util.common;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Uuid生成功能辅助类
 * 
 * @author 
 * <ul>
 * 	<li>张强 2014年9月22日 新增</li>
 * </ul>
 */
public class UuidUtil {
	private UuidUtil() {
	}

	/**
	 * 快捷生成java.util.UUID字符串
	 * <br /> 返回格式,无-号
	 * @author 
	 * <ul>
	 * 	<li>张强 2014年9月22日 新增</li>
	 * </ul>
	 * @return
	 */
	public synchronized static String generateUUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 产生服务器集群可以安全使用的uuid,生成模式：{主机标识}-{进程标识}-{java.util.UUID}
	 * <br />返回格式有-,集群使用
	 * @author 
	 * <ul>
	 * 	<li>张强 2014年9月22日 新增</li>
	 * </ul>
	 * @return
	 */
	public synchronized static String generateClusterUUID() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			byte[] bts = localhost.getAddress();
			StringBuffer sb = new StringBuffer();
			for (byte bt : bts) {
				int num = bt + 128;
				sb.append(Integer.toHexString(num));
			}
			sb.append('-');
			sb.append(obtainPid());
			sb.append('-');
			sb.append(generateUUID());
			return sb.toString();
		} catch (UnknownHostException u) {
			throw new RuntimeException(
					"generateClusterUUID meet UnknownHostException", u);
		}
	}

	/**
	 * 获取当前JVM的进程ID
	 * 
	 * @author 
	 * <ul>
	 * 	<li>张强 2014年9月22日 新增</li>
	 * </ul>
	 * @return
	 */
	public synchronized static int obtainPid() {
		// obtain PID
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName(); // format: "pid@hostname"
		try {
			return Integer.parseInt(name.substring(0, name.indexOf('@')));
		} catch (Exception e) {
			return -1;
		}
	}
}
