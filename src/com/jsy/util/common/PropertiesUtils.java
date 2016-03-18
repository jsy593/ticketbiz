package com.jsy.util.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

/**
 * ip
 * 
 * @author yhl
 * 
 */
public class PropertiesUtils {

	public static String LOCAL_HOST = null;
	public static String LOCAL_HOST_PAGE = null;

	public PropertiesUtils() {
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new ClassPathResource(
					"localhost.properties").getFile().getPath());
			props.load(fis);
			PropertiesUtils.LOCAL_HOST = props.getProperty("localhost");
			PropertiesUtils.LOCAL_HOST_PAGE = props
					.getProperty("localhostPost");
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String localHostVal(String hostname) {
		Properties props = new Properties();
		String hostvalue = "";
		try {
			FileInputStream fis = new FileInputStream(new ClassPathResource(
					"localhost.properties").getFile().getPath());
			props.load(fis);
			hostvalue = props.getProperty(hostname);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hostvalue;
	}

	/**
	 * 功能开关配置
	 * 
	 * @param hostname
	 * @return
	 */
	public static String on_offVal(String hostname) {
		Properties props = new Properties();
		String hostvalue = "";
		try {
			FileInputStream fis = new FileInputStream(new ClassPathResource(
					"on-off.properties").getFile().getPath());
			props.load(fis);
			hostvalue = props.getProperty(hostname);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hostvalue;
	}
	/*
	 * 
	 * 
	 * private String myip = null;
	 * 
	 * public PropertiesUtils(String wly) { try {
	 * myip=InetAddress.getLocalHost().toString(); } catch (UnknownHostException
	 * e) { e.printStackTrace(); } }
	 * 
	 * public static void main(String[] args) { PropertiesUtils ptu = new
	 * PropertiesUtils("wly"); System.out.println(ptu.myip); }
	 */
}
