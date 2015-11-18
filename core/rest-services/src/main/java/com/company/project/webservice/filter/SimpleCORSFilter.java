package com.company.project.webservice.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class SimpleCORSFilter implements Filter {
	public void init(FilterConfig filterConfig) {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:9090");
		response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		// Access-Control-Request-Headers
		response.setHeader("Access-Control-Allow-Headers", "accept, Content-Type, Authorization");

		// Facebook error: Credentials flag is 'true', but the 'Access-Control-Allow-Credentials' header is ''. It must be 'true' to allow credentials.
		response.setHeader("Access-Control-Allow-Credentials", "true");

		HttpServletRequest httpRequest = (HttpServletRequest) req;
		if (isPreflight(httpRequest)) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}

		chain.doFilter(req, res);
	}

	public void destroy() {
	}

	/**
	 * Checks if this is a X-domain pre-flight request.
	 * 
	 * @param request
	 * @return
	 */
	private boolean isPreflight(HttpServletRequest request) {
		return "OPTIONS".equals(request.getMethod());
	}
}