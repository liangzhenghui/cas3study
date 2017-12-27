package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;

import exceptions.BizException;

/**
 * { "cas:serviceResponse": { "xmlns:cas": "http://www.yale.edu/tp/cas",
 * "cas:authenticationSuccess": { "cas:attributes": { "cas:username": "root",
 * "cas:sex": "man", "cas:id": "abcd" }, "cas:user": "root" } } } {
 * "cas:serviceResponse": { "cas:authenticationFailure": { "content":
 * "未能够识别出目标 '1ST-33-CTZI9Gd7EytDTtcGP2Xh-cas01.example.org'票根", "code":
 * "INVALID_TICKET" }, "xmlns:cas": "http://www.yale.edu/tp/cas" } }
 * 
 * @author liangzhenghui
 *
 */
public class CasUtil {
	static final Logger LOG = Logger.getLogger(CasUtil.class.getName());
	private static final String CAS_LOGIN_URL_PART = "login";
	private static final String CAS_LOGOUT_URL_PART = "logout";
	private static final String CAS_SERVICE_VALIDATE_URL_PART = "serviceValidate";
	private static final String CAS_TICKET_BEGIN = "ticket=";

	/**
	 * 提取用户信息
	 * 
	 * @param dataStream
	 * @return
	 */
	public static String getCasServiceResponse(String result) {
		JSONObject json = XML.toJSONObject(result);
		JSONObject serviceResponse = (JSONObject) json.get("cas:serviceResponse");
		try {
			JSONObject authenticationSuccess = (JSONObject) serviceResponse.get("cas:authenticationSuccess");
			System.out.println("authenticationSuccess");
		} catch (Exception e) {
			System.out.println("authenticationFailure");
		}
		return "";
	}

	public static String getServiceTicket(String server, String ticketGrantingTicket, String service) {
		if (ticketGrantingTicket == null)
			return null;
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(server + "/" + ticketGrantingTicket);
		post.setRequestBody(new NameValuePair[] { new NameValuePair("service", service) });
		try {
			client.executeMethod(post);
			String response = post.getResponseBodyAsString();
			switch (post.getStatusCode()) {
			case 200:
				return response;
			default:
				LOG.warning("Invalid response code (" + post.getStatusCode() + ") from CAS server!");
				LOG.info("Response (1k): " + response.substring(0, Math.min(1024, response.length())));
				break;
			}
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public static String getTicketGrantingTicket(String server, String username, String password) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(server);
		post.setRequestBody(new NameValuePair[] { new NameValuePair("username", username),
				new NameValuePair("password", password) });
		try {
			client.executeMethod(post);
			String response = post.getResponseBodyAsString();
			switch (post.getStatusCode()) {
			case 201:
				Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(response);
				if (matcher.matches())
					return matcher.group(1);
				LOG.warning("Successful ticket granting request, but no ticket found!");
				LOG.info("Response (1k): " + response.substring(0, Math.min(1024, response.length())));
				break;
			default:
				LOG.warning("Invalid response code ($post.getStatusCode()) from CAS server!");
				LOG.info("Response:" + response);
				break;
			}
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	void notNull(Object object, String message) {
		if (object == null)
			throw new IllegalArgumentException(message);
	}

	public static void getServiceCall(String service, String serviceTicket) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(service);
		method.setQueryString(new NameValuePair[] { new NameValuePair("ticket", serviceTicket) });
		try {
			client.executeMethod(method);
			String response = method.getResponseBodyAsString();
			System.out.println(response);
			switch (method.getStatusCode()) {
			case 200:
				LOG.info("Response:" + response);
				break;
			default:
				LOG.warning("Invalid response code (" + method.getStatusCode() + ") from CAS server!");
				LOG.info("Response:" + response);
				break;
			}
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	void logout(String server, String ticketGrantingTicket) {
		HttpClient client = new HttpClient();
		DeleteMethod method = new DeleteMethod(server + "/" + ticketGrantingTicket);
		try {
			client.executeMethod(method);
			String response = method.getResponseBodyAsString();
			switch (method.getStatusCode()) {
			case 200:
				LOG.info("Logged out");
				LOG.info(response);
				break;
			default:
				LOG.warning("Invalid response code (" + method.getStatusCode() + ") from CAS server!");
				LOG.info("Response:" + response);
				break;
			}
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	public static void main(String[] args) {
		String server = "http://localhost:6688/cas/v1/tickets";
		String username = "root";
		String password = "root";
		// String service = "http://192.168.5.154:8080/cas-client/cas/test";
		String service = "http://localhost:8080/cas-client-b-proxy/cas/test";
		Client client = new Client();
		String ticketGrantingTicket = getTicketGrantingTicket(server, username, password);
		// String
		// ticketGrantingTicket="TGT-14-6OcMogvsO49id9zdqSVokad7cq2Z4Evd6grew32XVAUGzRLwov-cas01.example.org";
		System.out.println(ticketGrantingTicket);
		// println("TicketGrantingTicket is $ticketGrantingTicket");
		String serviceTicket = "";
		if (StringUtils.isNotBlank(ticketGrantingTicket)) {
			serviceTicket = getServiceTicket(server, ticketGrantingTicket, service);
		}
		System.out.println("serviceTicket" + serviceTicket);
		if (StringUtils.isNotBlank(ticketGrantingTicket)) {
			getServiceCall(service, serviceTicket);
		}
	}
}
