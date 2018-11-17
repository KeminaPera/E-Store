package com.itheima.e_store.service;

import com.itheima.e_store.domain.Order;
import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.UserBean;

public interface OrderService {

	boolean saveOrder(Order order)throws Exception;

	PageModel findOrdersByUidWithPage(UserBean user, String curNum) throws Exception;
	PageModel findAllOrdersWithPage(String curNum)throws Exception;
	PageModel findOrdersByState(Order order,String curNum)throws Exception;
	Order findOrderByOid(Order order)throws Exception;
}
