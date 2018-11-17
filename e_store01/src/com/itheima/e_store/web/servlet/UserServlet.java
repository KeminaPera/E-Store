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
 * 该类封装了所有和用户表有关的用户操作
 *
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 该方法是获取登陆界面
	 * 
	 * @param request
	 * @param response
	 * @return 登陆界面URL
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
	 * 该方法获取注册界面
	 * 
	 * @return String
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("消息过来了");
		return "/jsp/register.jsp";
	}

	/**
	 * 该方法是用来 注册用户的
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//获取所有参数存放在map数据结构中
		Map<String, String[]> parameterMap = request.getParameterMap();
		UserBean userBean = MyBeanUtils.populate(UserBean.class, parameterMap);
		userBean.setUid(UUIDUtils.getId());
		userBean.setCode(UUIDUtils.getCode());
		UserService service = new UserServiceImpl();
		boolean result = service.registeUser(userBean);
		if(result) {
			//注册成功，发送邮件
			MailUtils.sendMail(userBean.getEmail(), userBean.getCode());
			request.setAttribute("messege", "您已经注册成功，请激活！");
		}else {
			//注册失败，重新注册
			request.setAttribute("messege", "注册失败，请重新注册...");
		}
		return "/jsp/info.jsp";
	}
	
	/**
	 * 该方法是用来激活用户账号的
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
				request.setAttribute("messege", "激活成功！");
				path = "/jsp/login.jsp";
			}else {
				request.setAttribute("messege", "激活失败！");
				path = "/jsp/info.jsp";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 该方法实现用户登录
	 * 
	 * @param request
	 * @param response
	 * @return 
	 */
	public String userLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String path = null;
		try {
			//获取form表单中的信息
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//调用service中的方法
			UserService service = new UserServiceImpl();
			UserBean userBean = service.userLogin(username, password);
			if (userBean != null) {
				//登录成功
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
	 * 该方法是用户退出
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
			//获取参数
			String username = request.getParameter("username");
			//调用业务逻辑层
			UserService service = new UserServiceImpl();
			boolean result = service.findUserByUsername(username);
			//返回信息
			if (result) {
				response.getWriter().write("<font style='color:green; font-size:12px'>该用户名可用</font>");
			}else {
				response.getWriter().write("<font style='color:green; font-size:12px'>该用户名已存在</font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
