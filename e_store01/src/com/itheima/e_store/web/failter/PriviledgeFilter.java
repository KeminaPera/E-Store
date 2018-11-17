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

		//ת������
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//��ȡ����
		UserBean user = (UserBean)req.getSession().getAttribute("loginUser");
		//�ж��û��Ƿ��¼�������½�����У�������ʾ�û��ȵ�¼�ٷ���
		if (user != null) {
			//����
			chain.doFilter(request, response);
		}else {
			//��ʾ�û���½�ٷ���
			req.setAttribute("messege", "���ȵ�¼�ڷ�����Դ");
			req.getRequestDispatcher("info.jsp").forward(req, res);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
