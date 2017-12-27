package util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * Json工具类
 * 
 * @author qiuqingbo
 * @date 2015-4-13
 */
public class JsonUtil {

	public static String object2Json(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static <T> T json2Bean(String str, Class<T> beanClass) {
		return JSON.parseObject(str, beanClass);
	}

	public static <T> List<T> json2List(String str, Class<T> beanClass) {
		return JSON.parseArray(str, beanClass);
	}

	public static <T> T json2Bean(String str, TypeReference<T> typeReference) {
		return JSON.parseObject(str, typeReference);
	}

	public static Object parse(String str) {
		return JSON.parse(str);
	}
}
