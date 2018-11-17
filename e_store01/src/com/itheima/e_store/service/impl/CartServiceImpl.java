package com.itheima.e_store.service.impl;

import com.itheima.e_store.dao.ProductDao;
import com.itheima.e_store.dao.mysql.impl.ProductDaoImpl;
import com.itheima.e_store.domain.CartItem;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.service.CartService;

public class CartServiceImpl implements CartService {

	@Override
	public CartItem addCartItemToCart(String pid, String num) throws Exception {
		int num1 = Integer.parseInt(num);
		ProductDao dao = new ProductDaoImpl();
		Product product = dao.findProductByPid(pid);
		CartItem cartItem = new CartItem(product, num1);
		return cartItem;
	}
}
