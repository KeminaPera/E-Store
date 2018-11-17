package com.itheima.e_store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.Order;
import com.itheima.e_store.domain.OrderItem;
import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.service.OrderService;
import com.itheima.e_store.service.impl.OrderServiceImpl;
import com.itheima.e_store.web.base.BaseServlet;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public String findAllOrders(HttpServletRequest request, HttpServletResponse response) {
		
    	try {
    		//��ȡ��ز���
    		String curNum = request.getParameter("num");
			//����ҵ���߼����ѯ���ж���
			OrderService service = new  OrderServiceImpl();
			PageModel pageModel = service.findAllOrdersWithPage(curNum);
			//���ò���
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//��תҳ��
    	return "admin11/order/list.jsp";
    }
    
    public String findOrdersByState(HttpServletRequest request, HttpServletResponse response) {
		
    	try {
			String curNum = request.getParameter("num");
			String state = request.getParameter("state");
			int sta = Integer.parseInt(state);
			Order order = new Order();
			order.setState(sta);
			OrderService service = new OrderServiceImpl();
			PageModel pageModel = service.findOrdersByState(order,curNum);
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "admin11/order/list.jsp";
    }
    
    public String findOrderByOidWithAjax(HttpServletRequest request, HttpServletResponse response) {

    	try {
			//��ȡ������id
			String oid = request.getParameter("oid");
			OrderService service = new OrderServiceImpl();
			//����order����
			Order order = new Order();
			order.setOid(oid);
			Order o = service.findOrderByOid(order);
			List<OrderItem> orderItems = o.getOrderItems();
			response.setContentType("application/json;charset=utf-8");
			//����������ת����json����
			String jsonStr = JSONArray.fromObject(orderItems).toString();
			//��������
			response.getWriter().println(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return null;
    }

}
