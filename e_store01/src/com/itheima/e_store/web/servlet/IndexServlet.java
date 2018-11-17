package com.itheima.e_store.web.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.CategoryBean;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.service.CategoryService;
import com.itheima.e_store.service.ProductService;
import com.itheima.e_store.service.impl.CategoryServiceImpl;
import com.itheima.e_store.service.impl.ProductServiceImpl;
import com.itheima.e_store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductService service01 = new ProductServiceImpl();
		List<Product> list01 = new ArrayList<Product>();
		List<Product> list02 = new ArrayList<Product>();
		list01 = service01.getNewProducts();
		list02 = service01.getHotProducts();
		request.setAttribute("hotProducts", list02);
		request.setAttribute("newProducts", list01);
		
		return "/jsp/index.jsp";
		
	}

	

}
