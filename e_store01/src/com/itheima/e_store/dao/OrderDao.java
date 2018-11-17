package com.itheima.e_store.dao;

import java.util.List;

import com.itheima.e_store.domain.Order;
import com.itheima.e_store.domain.OrderItem;
import com.itheima.e_store.domain.UserBean;

public interface OrderDao {

	boolean saveOrder(Order order) throws Exception;
	boolean saveOrderItems(OrderItem orderItem) throws Exception;
	/*int getTotalPageNum(UserBean user) throws Exception;*/
	List<Order> findOrdersByUidWithPage(UserBean user)throws Exception;
	List<Order> findAllOrdersWithPage()throws Exception;
	int getTotalRecords(Order order) throws Exception;
	List<Order> findOrdersByState(Order order)throws Exception;
	Order findOrderByOid(Order order)throws Exception;
}
