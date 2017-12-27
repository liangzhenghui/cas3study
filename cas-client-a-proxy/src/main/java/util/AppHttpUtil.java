package util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.BizException;

/**
 * 调用各系统接口工具
 * reference from http://hc.apache.org/httpcomponents-client-ga/quickstart.html
 * @author liangzhenghui
 * @date 2017-12-26
 */
public class AppHttpUtil {

	private static final Logger log = LoggerFactory.getLogger(AppHttpUtil.class);
	
	/**
	 * post json方式请求服务
	 * @param data 请求的参数
	 * @param url 系统URL
	 * @return
	 */
	public static String doPost(Map data,String url) throws IOException {
		StringEntity entity = new StringEntity(JsonUtil.object2Json(data),"UTF-8");
		/*entity.setContentType("application/json");
		entity.setContentEncoding("UTF-8");*/
		HttpPost httppost = new HttpPost(url);
		log.debug("post url is {}.", url);
		httppost.setEntity(entity);
		httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			int satus = response.getStatusLine().getStatusCode();
			if (satus == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}else{
				throw BizException.CALL_SERVER_ERROR;
			}

		} finally {
			httpclient.close();
		}
	}

	/**
	 * get json方式请求服务
	 * @param url 系统URL
	 * @return
	 */
	public static String doGet(String url) throws IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			//获取返回状态码・・・「200」 
			int satus = response.getStatusLine().getStatusCode();
			if (satus == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}else{
				throw BizException.CALL_SERVER_ERROR;
			}
		} finally {
			httpclient.close();
		}
	}
}
