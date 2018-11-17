package com.itheima.e_store.web.servlet;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.e_store.domain.Cart;
import com.itheima.e_store.domain.CartItem;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.service.CartService;
import com.itheima.e_store.service.impl.CartServiceImpl;
import com.itheima.e_store.web.base.BaseServlet;

public class CartServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	public String addToCart(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			UserBean user = (UserBean)request.getSession().getAttribute("loginUser");
			if (user == null) {
				request.setAttribute("messege", "���ȵ�¼�ʺ�");
				return "/jsp/info.jsp";
			}
			String pid = request.getParameter("pid");
			String num = request.getParameter("num");
			CartService service = new CartServiceImpl();
			CartItem cartItem = service.addCartItemToCart(pid, num);
			//��session�л�ȡcart
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if (cart != null) {
				cart.addCartItemToCart(cartItem);
			}else {
				//���Ϊ�գ�����һ��cart������Ӹ�cartitem,�ٽ�cartд��session��
				cart = new Cart();
				cart.addCartItemToCart(cartItem);
			}
			request.getSession().setAttribute("cart", cart);
			//Ҫ��ת��ҳ��
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/cart.jsp";
		
	}
	
	public String delCartItem(HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("pid");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.delCartItem(pid);
		return "/jsp/cart.jsp";
		
	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response) {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clearCart();
		return "/jsp/cart.jsp";
		
	}
}
