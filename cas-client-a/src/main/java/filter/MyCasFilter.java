package filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.authentication.AttributePrincipal;

public class MyCasFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();  
		/*过滤器拦截到响应url的请求后会先执行doFilter()方法中
		 * chain.doFilter()之前的代码，然后执行下一个过滤器或者servelt。
		 * 紧接着执行chain.doFilter()之后的代码。
		 * */
		//chain.doFilter(req, resp);
		AttributePrincipal principal1 = (AttributePrincipal) request.getUserPrincipal();  
		Map<String, Object> attributes = principal1.getAttributes();  
		for (String key : attributes.keySet()) {  
		    System.out.println(key + "/" + attributes.get(key));  
		}
		System.out.println("----------------MyCasFilter-------------------------");
		//放行
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
