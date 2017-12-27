package util;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;

class Client {
	static final Logger LOG = Logger.getLogger(Client.class.getName());

	String getServiceTicket(String server, String ticketGrantingTicket, String service) {
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

	String getTicketGrantingTicket(String server, String username, String password) {
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
				LOG.info("Response: $response");
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

	void getServiceCall(String service, String serviceTicket) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(service);
		method.setQueryString(new NameValuePair[] { new NameValuePair("ticket", serviceTicket) });
		try {
			client.executeMethod(method);
			String response = method.getResponseBodyAsString();
			System.out.println(response);
			switch (method.getStatusCode()) {
			case 200:
				LOG.info("Response: $response");
				break;
			default:
				LOG.warning("Invalid response code (" + method.getStatusCode() + ") from CAS server!");
				LOG.info("Response: $response");
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
				LOG.info("Response: $response");
				break;
			}
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			method.releaseConnection();
		}
	}

	public static void main(String[] args) {
		String server = "http://192.168.5.154:8080/cas/v1/tickets";
		String username = "1";
		String password = "1";
		//String service = "http://192.168.5.154:8080/cas-client/cas/test";
		String service = "http://192.168.5.154:8080/cas-client-b/cas/test";
		Client client = new Client();
		String ticketGrantingTicket = client.getTicketGrantingTicket(server, username, password);
		//String ticketGrantingTicket="TGT-14-6OcMogvsO49id9zdqSVokad7cq2Z4Evd6grew32XVAUGzRLwov-cas01.example.org";
		System.out.println(ticketGrantingTicket);
		// println("TicketGrantingTicket is $ticketGrantingTicket");
		String serviceTicket = client.getServiceTicket(server, ticketGrantingTicket, service);
		System.out.println("serviceTicket"+serviceTicket);
		// println("ServiceTicket is $serviceTicket");
		client.getServiceCall(service, serviceTicket);
		//client.logout(server, ticketGrantingTicket);
	}
}
