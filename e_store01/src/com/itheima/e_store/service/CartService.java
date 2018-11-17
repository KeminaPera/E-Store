package com.itheima.e_store.service;

import com.itheima.e_store.domain.CartItem;

public interface CartService {

	public CartItem addCartItemToCart(String pid, String num) throws Exception;
}
