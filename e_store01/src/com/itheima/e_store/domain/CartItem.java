package com.itheima.e_store.domain;

public class CartItem {

	private Product product;
	private int num;
	private double subTotal;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getSubTotal() {
		subTotal = product.getShop_price()*num;
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public CartItem() {
		// TODO Auto-generated constructor stub
	}

	public CartItem(Product product, int num) {
		this.product = product;
		this.num = num;
	}

	@Override
	public String toString() {
		return "CartItem [product=" + product + ", num=" + num + ", subTotal=" + subTotal + "]";
	}

}
