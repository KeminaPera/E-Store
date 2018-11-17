package com.itheima.e_store.web.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.service.ProductService;
import com.itheima.e_store.service.impl.ProductServiceImpl;
import com.itheima.e_store.web.base.BaseServlet;

import net.sf.json.JSONArray;

public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) {
		
		String path = null;
		
		try {
			//��ȡ����
			String pid = request.getParameter("pid");
			System.out.println(pid);
			//����ҵ���߼����findProductByPid�ķ���
			ProductService service = new ProductServiceImpl();
			Product productBean = service.findProductByPid(pid);
			//���������
			if (productBean != null) {
				request.setAttribute("productInfo", productBean);
				path = "/jsp/product_info.jsp";
			}else {
				request.setAttribute("messege", "����Ʒ��Ϣ��ѯ������");
				path = "/jsp/info.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * �÷�����ͨ����Ʒ���������Ʒ��������Ʒ�Է�ҳ����ʽ��ʾ����
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findProductsWithCidAndPage(HttpServletRequest request, HttpServletResponse response) {

		try {
			//��ȡ����(�����Լ���ǰҳ��)
			String currentNum = request.getParameter("num");
			String cid = request.getParameter("cid");
			//����ҵ���߼������ط������õ�PageModel����
			ProductService service = new ProductServiceImpl();
			PageModel pageModel = service.findProductsWithCidAndPage(currentNum, cid);
			//���ò���������תҳ��
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/product_list.jsp";
	}
	
	public String findNewProducts(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("������һ������");
		try {
			ProductService service = new ProductServiceImpl();
			List<Product> list = new ArrayList<Product>();
			list = service.getNewProducts();
			String newProducts = JSONArray.fromObject(list).toString();
			response.setContentType("application/json;charset=ute-8");
			System.out.println(newProducts);
			response.getWriter().write(newProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String findHotProducts(HttpServletRequest request, HttpServletResponse response) {
		try {
			ProductService service = new ProductServiceImpl();
			List<Product> list = new ArrayList<Product>();
			list = service.getHotProducts();
			request.setAttribute("hotProducts", list);
			String hotProducts = JSONArray.fromObject(list).toString();
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(hotProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
