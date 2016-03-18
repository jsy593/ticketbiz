package com.jsy.util.common;

import java.util.Map;

/**
 * @Description map 工具类
 * @method
 * 		批量移除map中不需要的主键{@link #removeKey(Map, String)}<p>
 * 		判断map中是否包含某些主键{@link #isExistMap(Map, String)}
 * @author schoff [2015年7月22日]
 */
public class MapUtil {
	
	/**
	 * @Description 批量移除map中不需要的key
	 * @param map 需要移除key的map
	 * @param keys 需要移除的key;以逗号[,]分隔 eg:"1,2,3,4,5"
	 * @author schoff [2015年7月22日]
	 * @return void
	 */
	public static void removeKey(Map<String, Object> map, String keys) {
		for (String key : keys.split(",")) {
			map.remove(key);
		}
	}

	/**
	 * @Description 判断map中是否包含某些key
	 * @param map 	需要判断的map
	 * @param keys	需要判断的key;以逗号[,]分隔 eg:"1,2,3,4,5"
	 * @author schoff [2015年7月22日]
	 * @return boolean
	 */
	public static boolean isExistMap(Map<String, Object> map, String keys) {
		
		if (map.isEmpty() || null == keys) {
			return false;
		}
		for (String key : keys.split(",")) {
			if (map.containsKey(key)) {
				/*包含跳过*/
			}else {
				return false;
			}
		}
		return true;
	}
	
}
