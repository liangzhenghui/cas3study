package controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.AppHttpUtil;

@Controller
@RequestMapping("/cas")
public class TestCasController {
	@ResponseBody
	@RequestMapping(value = "/test")
	private Map test() {
		Map map = new HashMap();
		map.put("name", "123");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/test-proxy")
	private Map testProxy() throws Exception {
		AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
		// 2、获取对应的proxy ticket
		String targetUrl = "http://localhost:8080/cas-client-b-proxy/cas/test";
		String proxyTicket = principal.getProxyTicketFor(targetUrl);
		// 3、请求被代理应用时将获取到的proxy ticket以参数ticket进行传递
		String serviceUrl = targetUrl + "?ticket=" + URLEncoder.encode(proxyTicket, "UTF-8");
		String result = AppHttpUtil.doGet(serviceUrl);
		Map map = new HashMap();
		map.put("result", result);
		return map;
	}
}
