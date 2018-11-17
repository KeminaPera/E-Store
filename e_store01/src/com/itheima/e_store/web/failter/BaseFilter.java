package com.itheima.e_store.web.failter;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseFilter implements Filter {

    public BaseFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//×ª»»³ÉHttpServletRequest
		HttpServletRequest request = (HttpServletRequest) req;
		Method[] methods = request.getClass().getMethods();
		for (Method method : methods) {
			if ("getParameter".equals(method.getName())) {
				
			}
		}
		HttpServletResponse response = (HttpServletResponse) res;
		Method[] methods2 = response.getClass().getMethods();
		for (Method md : methods2) {
			if ("getWriter".equals(md.getName())) {
				response.setContentType("text/html;charset=utf-8");
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
