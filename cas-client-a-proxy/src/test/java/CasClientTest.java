import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import appclient.CasClient;

public class CasClientTest {
	//没调通
	@Test
	public void Test() throws Exception{
		String serviceUrl="http://localhost:6688/cas/login";
		String username="root";
		String password="root";
		CloseableHttpClient client = HttpClients.createDefault();
		CasClient casClient=new CasClient(client,"http://localhost:6688/cas/");
		casClient.login(serviceUrl, username, password);
	}
}
