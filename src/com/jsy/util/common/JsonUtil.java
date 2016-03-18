package com.jsy.util.common;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json，java对象互相转换类
 * @author 
 *	<ul>
 *	 <li>高文相  2014  年  9月 22 日 新增<li>
 *	<ul>
 */
public class JsonUtil implements ConstantUtil{
	private static Log log = LogFactory.getLog(JsonUtil.class);
	private static ObjectMapper  objectMapper = null;
	private static JsonUtil json = null;
	
	/**
	 * 实例化JsonUtil
	 * @author 
	 *	<ul>
	 *	 <li>高文相  2014  年  9月 22 日 新增<li>
	 *	<ul>
	 */
	 @SuppressWarnings("unused")
	private static JsonUtil instance() {
    	if(json == null) {
    		json = new JsonUtil();
    	}
    	return json;
	 }
	 
	/**
	 * 将Object对象转为JSON字符串
	 * 
	 * @param obj JavaBean/Map/List<?> 等
	 * 例：User user， 实体User的对象
	 * Map<String,Object> map; map对象
	 * List<Object> list;list对象
	 * List<Map<String,Object>> listmap;listmap对象
	 * @author 
	 *	<ul>
	 *	 <li>高文相  2014  年  9月 22 日 新增<li>
	 *	<ul>
	 */
	public static String ObjectToJson(Object obj){
		if(obj == null){
			return JSONUTILE_JSONNULL;
		}
		try {
			objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.debug("Object转换json字符串错误!", e);
		};
		return JSONUTILE_JSONNULL;
	}
	
	/**
	 * 将JSON字符串转换为Object对象
	 * @param json 需要转换的json字符串;
	 * @param clas 需要转换成的类型
	 * 例：List.class; User.class(实体类)
	 * @author 
	 *	<ul>
	 *	 <li>高文相  2014  年  9月 22 日 新增<li>
	 *	<ul>
	 */
	public static <T> T JsonToObject(String json, Class<T> clas){
		if(json == null){
			return null;
		}
			try {
				objectMapper = new ObjectMapper();
				return objectMapper.readValue(json, clas);
			} catch (IOException e) {
				log.debug("json转换Object错误!", e);
			}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static  List<Map<String, Object>> readJson2ListMap(String json) {
		try {
			objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	@SuppressWarnings("unchecked")
	public static  Map<String, Object> readJson2Map(String json) {
		try {
			objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	
	
	/**
	 * 将JSON格式中的null值转换成""串
	 * @author 
	 *	<ul>
	 *	 <li>高文相  2014  年  9月 22 日 新增<li>
	 *	<ul>
	 */
	@SuppressWarnings("unused")
	private static String convertNullToEmpty(String json) {
		return json.replaceAll(JSONUTILE_ISNULL,JSONUTILE_REPLACE);
	}
	
	/**
     * 设置时间格式
     * setDateFormate(DateUtil.ddd);
     * @param dateFormat 
     * @author 
	 *	<ul>
	 *	 <li>高文相  2014  年  9月 22 日 新增<li>
	 *	<ul>
	 */
    public JsonUtil setDateFormate(String dateFormt) {
    	SimpleDateFormat sdf = new SimpleDateFormat(dateFormt);
    	objectMapper = new ObjectMapper();
    	objectMapper.setDateFormat(sdf);  
        return this;  
    }
    
}


