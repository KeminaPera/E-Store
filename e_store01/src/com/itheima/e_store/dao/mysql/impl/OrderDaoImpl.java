package com.itheima.e_store.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.e_store.dao.OrderDao;
import com.itheima.e_store.domain.Order;
import com.itheima.e_store.domain.OrderItem;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public boolean saveOrder(Order order) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getDatetime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelehone(),order.getUser().getUid()};
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			return true;
		}
		return false;
	}
	

	@Override
	public boolean saveOrderItems(OrderItem orderItem) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into orderitem values(?, ?, ?, ?, ?)";
		Object[] params = {orderItem.getItemid(), orderItem.getQuantity(), orderItem.getTotal(), orderItem.getProduct().getPid(), orderItem.getOrder().getOid()};
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<Order> findOrdersByUidWithPage(UserBean user) throws Exception {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid =?";
		//��ѯ�����û������ж���
		List<Order> orders = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid());
		//����������ÿ��������
		for (Order order : orders) {
			String sql1 = "select * from product p, orderitem o where p.pid=o.pid and o.oid = ?";
			//����ÿ�����������product���Բ���ʹ��bean����װ���ݣ����ʹ��List<Map<String, Object>>����װ����
			List<Map<String, Object>> list = queryRunner.query(sql1, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : list) {
				Product product = new Product();
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(product, map);
				BeanUtils.populate(orderItem, map);
				orderItem.setProduct(product);
				order.getOrderItems().add(0, orderItem);
			}
		}
		return orders;
	}


	@Override
	public int getTotalRecords(Order order) throws Exception {
		//������ѯ1�������û�id��ѯ����2����ѯ���ж���  ������������п��ܸ��ݶ���״̬�ٲ�ѯ
		//������ر���
		Long totalRecords;
		//�Ƿ�����û�id��ѯ������ ���uid��-1����ʾ������id��ѯ
		if ("-1".equals(order.getUser().getUid())) {
			//�Ƿ���ݶ���״̬��ѯ
			if (order.getState()==-1) {
				//��ѯ���ж���
				QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "select count(*) from orders";
				totalRecords = queryRunner.query(sql, new ScalarHandler<>());
			}else {
				//���ݶ���״̬��ѯ
				QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "select count(*) from orders where state = ?";
				totalRecords = queryRunner.query(sql, new ScalarHandler<>(), order.getState());
			}
		}else {
			//�Ƿ���ݶ���״̬��ѯ
			if (order.getState()==-1) {
				//��ѯ���û������ж���
				QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "select count(*) from orders where uid = ?";
				totalRecords = queryRunner.query(sql, new ScalarHandler<>(),order.getUser().getUid());
			}else {
				//�����û�id�Ͷ���״̬��ѯ
				QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				String sql = "select count(*) from orders where uid= ? and state = ?";
				totalRecords = queryRunner.query(sql, new ScalarHandler<>(),order.getUser().getUid(), order.getState());
			}
		}
		return totalRecords.intValue();
	}


	@Override
	public List<Order> findAllOrdersWithPage() throws Exception {
		String sql;
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		sql="select * from orders";
		List<Order> list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
		for (Order o : list) {
			sql = "select * from orders r, orderitem i where r.oid = i.oid and r.oid = ?";
			List<Map<String, Object>> maps = queryRunner.query(sql, new MapListHandler(),o.getOid());
			for (Map<String, Object> map : maps) {
				Order order = new Order();
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem , map);
				BeanUtils.populate(order, map);
				order.getOrderItems().add(0, orderItem);
			}
		}
		
		return list;
	}


	@Override
	public List<Order> findOrdersByState(Order order) throws Exception {
		String sql;
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		sql="select * from orders where state=?";
		List<Order> list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class),order.getState());
		for (Order o : list) {
			sql = "select * from orders r, orderitem i where r.oid = i.oid and r.oid = ?";
			List<Map<String, Object>> maps = queryRunner.query(sql, new MapListHandler(),o.getOid());
			for (Map<String, Object> map : maps) {
				Order ord = new Order();
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem , map);
				BeanUtils.populate(ord, map);
				ord.getOrderItems().add(0, orderItem);
			}
		}
		return list;
	}


	@Override
	public Order findOrderByOid(Order order) throws Exception {
		System.out.println(order.getOid());
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orderitem o, product p where o.pid = p.pid and"
				+ " o.oid = ?";
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), order.getOid());
		for (Map<String, Object> map : list) {
			Product product = new Product();
			OrderItem item = new OrderItem();
			BeanUtils.populate(product, map);
			BeanUtils.populate(item, map);
			item.setProduct(product);
			orderItems.add(item);
		}
		System.out.println(orderItems);
		order.setOrderItems(orderItems);
		return order;
	}

}
