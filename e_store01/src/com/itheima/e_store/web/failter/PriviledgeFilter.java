package com.itheima.e_store.web.failter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.UserBean;

public class PriviledgeFilter implements Filter {

    public PriviledgeFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		//转换类型
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//获取参数
		UserBean user = (UserBean)req.getSession().getAttribute("loginUser");
		//判断用户是否登录，如果登陆，放行，否则提示用户先登录再访问
		if (user != null) {
			//放行
			chain.doFilter(request, response);
		}else {
			//提示用户登陆再访问
			req.setAttribute("messege", "请先登录在访问资源");
			req.getRequestDispatcher("info.jsp").forward(req, res);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
