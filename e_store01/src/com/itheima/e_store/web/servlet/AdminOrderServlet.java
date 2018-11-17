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
    		//获取相关参数
    		String curNum = request.getParameter("num");
			//调用业务逻辑层查询所有订单
			OrderService service = new  OrderServiceImpl();
			PageModel pageModel = service.findAllOrdersWithPage(curNum);
			//设置参数
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//跳转页面
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
			//获取到订单id
			String oid = request.getParameter("oid");
			OrderService service = new OrderServiceImpl();
			//创建order对象
			Order order = new Order();
			order.setOid(oid);
			Order o = service.findOrderByOid(order);
			List<OrderItem> orderItems = o.getOrderItems();
			response.setContentType("application/json;charset=utf-8");
			//将集合数据转换成json数据
			String jsonStr = JSONArray.fromObject(orderItems).toString();
			//返回数据
			response.getWriter().println(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return null;
    }

}
