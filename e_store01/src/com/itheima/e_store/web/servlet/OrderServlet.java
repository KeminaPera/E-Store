package com.itheima.e_store.web.servlet;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.Cart;
import com.itheima.e_store.domain.CartItem;
import com.itheima.e_store.domain.Order;
import com.itheima.e_store.domain.OrderItem;
import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.service.OrderService;
import com.itheima.e_store.service.impl.OrderServiceImpl;
import com.itheima.e_store.utils.UUIDUtils;
import com.itheima.e_store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {

	/**
	 * 该方法是保存订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 判断用户是否登录
		UserBean user = (UserBean) request.getSession().getAttribute("loginUser");
		if (user == null) {
			// 如果没有登录，跳转到提示页面，请先登录帐号
			request.setAttribute("messege", "请先登录帐号");
			return "jsp/info.jsp";
		}
		// 获取到购物车中的购物项信息
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 如果登录了，创建Order对象，并添加相应的值
		Order order = new Order();
		order.setOid(UUIDUtils.getId());
		order.setDatetime(new Date());
		order.setTotal(cart.getTotal());
		order.setUser(user);
		// 遍历购物车，创建OrderItem
		Collection<CartItem> cartItems = cart.getCartItems();
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getId());
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getNum());
			orderItem.setTotal(cartItem.getSubTotal());
			order.getOrderItems().add(orderItem);
		}
		OrderService service = new OrderServiceImpl();

		service.saveOrder(order);
		// 清空购物车
		cart.clearCart();
		// 跳转到/jsp/order_info.jsp
		// 设置参数
		request.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}

	/**
	 * 该方法是查看该用户的所有的订单并以分页形式显示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findOrdersByUidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//获取当前页
		String curNum = request.getParameter("num");
		//获取用户信息
		UserBean user = (UserBean)request.getSession().getAttribute("loginUser");
		//调用业务逻辑层findOrdersByUidWithPage方法，并得到Pagemodel对象
		OrderService service = new OrderServiceImpl();
		System.out.println(user+"   "+curNum);
		PageModel pageModel = service.findOrdersByUidWithPage(user, curNum);
		//将pagemodel对象写入域对象中
		System.out.println(pageModel.getList());
		request.setAttribute("pageModel", pageModel);
		//转发请求
		return "/jsp/order_list.jsp";
	}
}
