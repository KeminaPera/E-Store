package com.itheima.e_store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.service.UserService;
import com.itheima.e_store.service.impl.UserServiceImpl;
import com.itheima.e_store.utils.MailUtils;
import com.itheima.e_store.utils.MyBeanUtils;
import com.itheima.e_store.utils.UUIDUtils;
import com.itheima.e_store.web.base.BaseServlet;

/**
 * �����װ�����к��û����йص��û�����
 *
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * �÷����ǻ�ȡ��½����
	 * 
	 * @param request
	 * @param response
	 * @return ��½����URL
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			response.sendRedirect("/e_store01/jsp/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
//		return "/jsp/login.jsp";
		
	}
	
	/**
	 * �÷�����ȡע�����
	 * 
	 * @return String
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("��Ϣ������");
		return "/jsp/register.jsp";
	}

	/**
	 * �÷��������� ע���û���
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//��ȡ���в��������map���ݽṹ��
		Map<String, String[]> parameterMap = request.getParameterMap();
		UserBean userBean = MyBeanUtils.populate(UserBean.class, parameterMap);
		userBean.setUid(UUIDUtils.getId());
		userBean.setCode(UUIDUtils.getCode());
		UserService service = new UserServiceImpl();
		boolean result = service.registeUser(userBean);
		if(result) {
			//ע��ɹ��������ʼ�
			MailUtils.sendMail(userBean.getEmail(), userBean.getCode());
			request.setAttribute("messege", "���Ѿ�ע��ɹ����뼤�");
		}else {
			//ע��ʧ�ܣ�����ע��
			request.setAttribute("messege", "ע��ʧ�ܣ�������ע��...");
		}
		return "/jsp/info.jsp";
	}
	
	/**
	 * �÷��������������û��˺ŵ�
	 * @param request
	 * @param response
	 * @return
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) {
	
		String path = null;
		try {
			String code = request.getParameter("code");
			UserService service = new UserServiceImpl();
			boolean result = service.active(code);
			if (result) {
				request.setAttribute("messege", "����ɹ���");
				path = "/jsp/login.jsp";
			}else {
				request.setAttribute("messege", "����ʧ�ܣ�");
				path = "/jsp/info.jsp";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * �÷���ʵ���û���¼
	 * 
	 * @param request
	 * @param response
	 * @return 
	 */
	public String userLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String path = null;
		try {
			//��ȡform���е���Ϣ
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//����service�еķ���
			UserService service = new UserServiceImpl();
			UserBean userBean = service.userLogin(username, password);
			if (userBean != null) {
				//��¼�ɹ�
				request.getSession().setAttribute("loginUser", userBean);
				path = "/jsp/index.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("messege", e.getMessage());
			path = "/jsp/login.jsp";
		}
		return path;
	}
	
	/**
	 * �÷������û��˳�
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String userLogout(HttpServletRequest request, HttpServletResponse response) {
		
		request.getSession().invalidate();
		return "IndexServlet?method=";
	}
	
	public String findUserByUsername(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			//��ȡ����
			String username = request.getParameter("username");
			//����ҵ���߼���
			UserService service = new UserServiceImpl();
			boolean result = service.findUserByUsername(username);
			//������Ϣ
			if (result) {
				response.getWriter().write("<font style='color:green; font-size:12px'>���û�������</font>");
			}else {
				response.getWriter().write("<font style='color:green; font-size:12px'>���û����Ѵ���</font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
