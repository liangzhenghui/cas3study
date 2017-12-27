import java.net.URLEncoder;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.AppHttpUtil;

/**
 * reference from
 * https://docs.spring.io/spring-security/site/docs/3.1.x/reference/cas.html
 * 测试代理模式
 * @author liangzhenghui
 *
 */
public class ProxyHttpTest {
	private static final Logger log = LoggerFactory.getLogger(ProxyHttpTest.class);
	/**
	 * 代理模式的局限在于需要在登录的情况下才能使用
	 * @throws Exception
	 */
	@Test
	public void testProxyHttp() throws Exception {
		try{
		AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
		// 2、获取对应的proxy ticket
		String targetUrl = "http://localhost:8080/cas-client-b-proxy/cas/test";
		String proxyTicket = principal.getProxyTicketFor(targetUrl);
		// 3、请求被代理应用时将获取到的proxy ticket以参数ticket进行传递
		String serviceUrl = targetUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		String result = AppHttpUtil.doGet(serviceUrl);
		log.info(result);
		}catch(Exception e){
			System.out.println("may be you had not logon on");
		}
	}
}
