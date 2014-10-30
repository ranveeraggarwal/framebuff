package listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RequestFilter
 */
@WebFilter("/*")
public class RequestFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RequestFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;
		HttpSession session = hreq.getSession();
		String requestURI = hreq.getRequestURI();
		
		if (hreq.getHeader("Upgrade") != null)
			chain.doFilter(request, response);
		else if (
				session.getAttribute("userId") != null || 
				requestURI.equals("/") ||
				requestURI.startsWith("/css/") || 
				requestURI.startsWith("/js/") ||
				requestURI.startsWith("/favicon") ||
				requestURI.startsWith("/fonts/")
				){
			chain.doFilter(request, response);
		}
		else {
			hres.sendRedirect("/");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
