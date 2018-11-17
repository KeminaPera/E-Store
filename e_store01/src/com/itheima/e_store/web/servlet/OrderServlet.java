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
	 * �÷����Ǳ��涩��
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �ж��û��Ƿ��¼
		UserBean user = (UserBean) request.getSession().getAttribute("loginUser");
		if (user == null) {
			// ���û�е�¼����ת����ʾҳ�棬���ȵ�¼�ʺ�
			request.setAttribute("messege", "���ȵ�¼�ʺ�");
			return "jsp/info.jsp";
		}
		// ��ȡ�����ﳵ�еĹ�������Ϣ
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// �����¼�ˣ�����Order���󣬲������Ӧ��ֵ
		Order order = new Order();
		order.setOid(UUIDUtils.getId());
		order.setDatetime(new Date());
		order.setTotal(cart.getTotal());
		order.setUser(user);
		// �������ﳵ������OrderItem
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
		// ��չ��ﳵ
		cart.clearCart();
		// ��ת��/jsp/order_info.jsp
		// ���ò���
		request.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}

	/**
	 * �÷����ǲ鿴���û������еĶ������Է�ҳ��ʽ��ʾ
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findOrdersByUidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//��ȡ��ǰҳ
		String curNum = request.getParameter("num");
		//��ȡ�û���Ϣ
		UserBean user = (UserBean)request.getSession().getAttribute("loginUser");
		//����ҵ���߼���findOrdersByUidWithPage���������õ�Pagemodel����
		OrderService service = new OrderServiceImpl();
		System.out.println(user+"   "+curNum);
		PageModel pageModel = service.findOrdersByUidWithPage(user, curNum);
		//��pagemodel����д���������
		System.out.println(pageModel.getList());
		request.setAttribute("pageModel", pageModel);
		//ת������
		return "/jsp/order_list.jsp";
	}
}
