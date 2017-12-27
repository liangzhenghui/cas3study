import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.AppHttpUtil;
import util.CasUtil;
import util.Client;

/**
 * reference from
 * https://docs.spring.io/spring-security/site/docs/3.1.x/reference/cas.html
 * 测试代理模式
 * @author liangzhenghui
 *
 */
public class RestLoginTest {
	private static final Logger log = LoggerFactory.getLogger(RestLoginTest.class);
	/**
	 * 
	 * 
	 * 
	 * 
	 {
	    "cas:serviceResponse": {
	        "xmlns:cas": "http://www.yale.edu/tp/cas", 
	        "cas:authenticationSuccess": {
	            "cas:attributes": {
	                "cas:username": "root", 
	                "cas:sex": "man", 
	                "cas:id": "abcd"
	            }, 
	            "cas:user": "root"
	        }
	    }
	}
	 {
	    "cas:serviceResponse": {
	        "cas:authenticationFailure": {
	            "content": "未能够识别出目标 '1ST-33-CTZI9Gd7EytDTtcGP2Xh-cas01.example.org'票根", 
	            "code": "INVALID_TICKET"
	        }, 
	        "xmlns:cas": "http://www.yale.edu/tp/cas"
	    }
	}
	
		TGT是门户独有的,不应该传给子应用,子应用需要访问cas环境里面的某个服务,
		就要通过cas门户去申请,cas门户给子应用返回st,子应用再通过st去访问该服务
	
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception {
		String serviceValidate = "http://localhost:6688/cas/serviceValidate";
		String server = "http://localhost:6688/cas/v1/tickets";
		String username = "root";
		String password = "root";
		// String service = "http://192.168.5.154:8080/cas-client/cas/test";
		//登录接口会返回的登录票据,tgt根据需要申请的服务,然后返回对应的serviceTicket
		//String service = "http://localhost:6688/cas/login";
		String service = "http://localhost:8080/cas-client-b-proxy/cas/test";
		Client client = new Client();
		String ticketGrantingTicket = CasUtil.getTicketGrantingTicket(server, username, password);
		// String
		// ticketGrantingTicket="TGT-14-6OcMogvsO49id9zdqSVokad7cq2Z4Evd6grew32XVAUGzRLwov-cas01.example.org";
		System.out.println(ticketGrantingTicket);
		// println("TicketGrantingTicket is $ticketGrantingTicket");
		String serviceTicket = "";
		if (StringUtils.isNotBlank(ticketGrantingTicket)) {
			serviceTicket = CasUtil.getServiceTicket(server, ticketGrantingTicket, service);
		}
		System.out.println("--------serviceTicket-----------" + serviceTicket);
		//String result=AppHttpUtil.doGet(serviceValidate+"?service="+URLEncoder.encode(service, "UTF-8")+"&ticket="+serviceTicket);
		String result=AppHttpUtil.doGet(serviceValidate+"?service="+URLEncoder.encode(service, "UTF-8")+"&ticket="+serviceTicket);
		System.out.println("result=="+result);
		CasUtil.getCasServiceResponse(result);
	}
}
