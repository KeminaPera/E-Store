package com.itheima.e_store.web.servlet;

import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.CategoryBean;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.service.CategoryService;
import com.itheima.e_store.service.ProductService;
import com.itheima.e_store.service.impl.CategoryServiceImpl;
import com.itheima.e_store.service.impl.ProductServiceImpl;
import com.itheima.e_store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	public String findAllCats(HttpServletRequest request, HttpServletResponse response) {

		try {
			CategoryService service = new CategoryServiceImpl();
			List<CategoryBean> list = service.getAllCategory();
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin11/category/list.jsp";
	}

	public String toEditPage(HttpServletRequest request, HttpServletResponse response) {

		try {
			String cid = request.getParameter("cid");
			CategoryBean category = new CategoryBean();
			category.setCid(cid);
			CategoryService service = new CategoryServiceImpl();
			CategoryBean cate = service.findAllCategoryByCid(category);
			request.setAttribute("category", cate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin11/category/edit.jsp";

	}

	public String editCategory(HttpServletRequest request, HttpServletResponse response) {

		try {
			String cname = request.getParameter("cname");
			String cid = request.getParameter("cid");
			CategoryBean category = new CategoryBean();
			category.setCid(cid);
			category.setCname(cname);
			CategoryService service = new CategoryServiceImpl();
			boolean result = service.editCategory(category);
			if (result) {
				List<CategoryBean> list = service.getAllCategory();
				request.setAttribute("list", list);
				System.out.println("这是新的list"+list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin11/category/list.jsp";

	}

	/**
	 * 返回添加种类的界面
	 * 
	 * @param requet
	 * @param response
	 * @return
	 */
	public String addCatUI(HttpServletRequest requet, HttpServletResponse response) {
		return "admin11/category/add.jsp";
	}

	public String addCategory(HttpServletRequest request, HttpServletResponse response) {

		boolean result = false;
		try {
			String cname = request.getParameter("cname");
			CategoryService service = new CategoryServiceImpl();
			result = service.addCategory(cname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result) {
			request.setAttribute("messege", "添加成功");
		}else {
			request.setAttribute("messege", "添加失败");
		}
		return "jsp/info.jsp";
	}
	
	public String delCategory(HttpServletRequest request, HttpServletResponse response) {
		List<CategoryBean> list = null;
		try {
			String cid = request.getParameter("cid");
			CategoryService service = new CategoryServiceImpl();
			list = service.delCategoryByCid(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		return "admin11/category/list.jsp";
	}
	
}
