package com.itheima.e_store.service.impl;

import java.util.List;

import com.itheima.e_store.dao.OrderDao;
import com.itheima.e_store.dao.mysql.impl.OrderDaoImpl;
import com.itheima.e_store.domain.Order;
import com.itheima.e_store.domain.OrderItem;
import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.utils.BeanFactory;

public class OrderServiceImpl implements com.itheima.e_store.service.OrderService {

	OrderDao dao = (OrderDao)BeanFactory.createDaoImpl("OrderDao");
	
	@Override
	public boolean saveOrder(Order order) throws Exception {
		//给order表里面添加订单
		boolean result = dao.saveOrder(order);
		boolean result1 = false;
		//给orderitem里面添加订单项
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			result1 = dao.saveOrderItems(orderItem);
		}
		if (result&& result1) {
			return true;
		}
		return false;
	}

	@Override
	public PageModel findOrdersByUidWithPage(UserBean user, String curNum) throws Exception {
		//将当前页转换成整形数据
		int currentPageNum = Integer.parseInt(curNum);
		//创建order对象
		Order order = new Order();
		//查询该用户的所有订单，设置相关参数：order.state=-1
		order.setState(-1);
		order.setUser(user);
		//查询所有的订单记录数
		int totalRecords = dao.getTotalRecords(order);
		//创建PageModel对象
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 3);
		//查询该用户的所有订单，得到list集合
		List<Order> list = dao.findOrdersByUidWithPage(user);
		//将list集合绑定到pagemodel对象上
		pageModel.setList(list);
		//绑定url
		pageModel.setUrl("OrderServlet?method=findOrdersByUidWithPage");
		return pageModel;
	}

	@Override
	public PageModel findAllOrdersWithPage(String curNum) throws Exception {
		//转换当前页面为整数
		int currentPageNum = Integer.parseInt(curNum);
		//创建order对象
		Order order = new Order();
		//查询所有订单，设置order.state=-1,order.getUser().getUid=-1
		order.setState(-1);
		order.setUser(new UserBean());
		order.getUser().setUid("-1");
		//获取总记录数
		int totalRecords = dao.getTotalRecords(order);
		//创建pageModel对象
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//查询所有订单
		List<Order> list = dao.findAllOrdersWithPage();
		//绑定list
		pageModel.setList(list);
		//绑定url
		pageModel.setUrl("");
		return pageModel;
	}

	@Override
	public PageModel findOrdersByState(Order order,String curNum) throws Exception {
		//将当前页码转换成整数
		int currentPageNum = Integer.parseInt(curNum);
		//获取所有订单状态是order.state的所有订单数
		//设置相关参数：orde.getUser().setUid(-1);
		order.setUser(new UserBean());
		order.getUser().setUid("-1");
		int totalRecords = dao.getTotalRecords(order);
		//创建pageModel对象
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//查询出该状态下的所有订单信息
		List<Order> list = dao.findOrdersByState(order);
		//绑定list
		pageModel.setList(list);
		//绑定url
		pageModel.setUrl("");
		//返回pageModel对象
		return pageModel;
	}

	@Override
	public Order findOrderByOid(Order order) throws Exception {
		OrderDao dao = new OrderDaoImpl();
		Order o = dao.findOrderByOid(order);
		return o;
	}

}
