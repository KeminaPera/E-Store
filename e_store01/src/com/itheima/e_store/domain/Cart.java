package com.itheima.e_store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cart {

	Map<String, CartItem> cartItems = new HashMap<String, CartItem>();
	double total;
	
	public void clearCart() {
		cartItems.clear();
	}
	
	public void delCartItem(String pid) {
		cartItems.remove(pid);
	}
	
	public void addCartItemToCart(CartItem cartItem) {
		//�������Ʒ�Ѿ��ڹ��ﳵ�У�����ԭ������Ʒ�����ϼ���������Ʒ�ĸ���
		String key = cartItem.getProduct().getPid();
		if(cartItems.containsKey(key)) {
			CartItem item = cartItems.get(key);
			item.setNum(item.getNum()+cartItem.getNum());
		}else {
			//�����ڵĻ���ֱ�ӽ�����Ʒ��ӵ����ﳵ��
			cartItems.put(cartItem.getProduct().getPid(), cartItem);
		}
	}
		
	
	public Collection<CartItem> getCartItems() {
		return cartItems.values();
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotal() {
		total = 0.0;
		for (CartItem cartItem : this.getCartItems()) {
			total +=cartItem.getSubTotal();
		}
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Cart() {
	}

	public Cart(Map<String, CartItem> cartItems, double total) {
		this.cartItems = cartItems;
		this.total = total;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "Cart [cartItems=" + (cartItems != null ? toString(cartItems.entrySet(), maxLen) : null) + ", total="
				+ total + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}
	
}
