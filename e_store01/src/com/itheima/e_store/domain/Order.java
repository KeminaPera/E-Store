package com.itheima.e_store.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	private String oid;
	private Date datetime;
	private double total;
	private int state;
	private String address;
	private String name;
	private String telephone;
	private UserBean user;
	private List<OrderItem> orderItems = new ArrayList<>(); 

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelehone() {
		return telephone;
	}

	public void setTelehone(String phone) {
		this.telephone = phone;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}
