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
		//��order��������Ӷ���
		boolean result = dao.saveOrder(order);
		boolean result1 = false;
		//��orderitem������Ӷ�����
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
		//����ǰҳת������������
		int currentPageNum = Integer.parseInt(curNum);
		//����order����
		Order order = new Order();
		//��ѯ���û������ж�����������ز�����order.state=-1
		order.setState(-1);
		order.setUser(user);
		//��ѯ���еĶ�����¼��
		int totalRecords = dao.getTotalRecords(order);
		//����PageModel����
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 3);
		//��ѯ���û������ж������õ�list����
		List<Order> list = dao.findOrdersByUidWithPage(user);
		//��list���ϰ󶨵�pagemodel������
		pageModel.setList(list);
		//��url
		pageModel.setUrl("OrderServlet?method=findOrdersByUidWithPage");
		return pageModel;
	}

	@Override
	public PageModel findAllOrdersWithPage(String curNum) throws Exception {
		//ת����ǰҳ��Ϊ����
		int currentPageNum = Integer.parseInt(curNum);
		//����order����
		Order order = new Order();
		//��ѯ���ж���������order.state=-1,order.getUser().getUid=-1
		order.setState(-1);
		order.setUser(new UserBean());
		order.getUser().setUid("-1");
		//��ȡ�ܼ�¼��
		int totalRecords = dao.getTotalRecords(order);
		//����pageModel����
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//��ѯ���ж���
		List<Order> list = dao.findAllOrdersWithPage();
		//��list
		pageModel.setList(list);
		//��url
		pageModel.setUrl("");
		return pageModel;
	}

	@Override
	public PageModel findOrdersByState(Order order,String curNum) throws Exception {
		//����ǰҳ��ת��������
		int currentPageNum = Integer.parseInt(curNum);
		//��ȡ���ж���״̬��order.state�����ж�����
		//������ز�����orde.getUser().setUid(-1);
		order.setUser(new UserBean());
		order.getUser().setUid("-1");
		int totalRecords = dao.getTotalRecords(order);
		//����pageModel����
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//��ѯ����״̬�µ����ж�����Ϣ
		List<Order> list = dao.findOrdersByState(order);
		//��list
		pageModel.setList(list);
		//��url
		pageModel.setUrl("");
		//����pageModel����
		return pageModel;
	}

	@Override
	public Order findOrderByOid(Order order) throws Exception {
		OrderDao dao = new OrderDaoImpl();
		Order o = dao.findOrderByOid(order);
		return o;
	}

}
