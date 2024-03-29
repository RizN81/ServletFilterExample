
package com.app.servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private ServletContext	context;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
		context.log("AuthenticationFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		context.log("Requested Resource::" + uri);

		HttpSession session = req.getSession(false);

		if (session == null && !(uri.endsWith("html") || uri.endsWith("LoginServlet")))
		{
			context.log("Unauthorized access request");
			res.sendRedirect("login.html");
		}
		else
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		//close any resources here
	}

}
