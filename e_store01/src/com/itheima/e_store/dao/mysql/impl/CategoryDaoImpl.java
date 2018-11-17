package com.itheima.e_store.dao.mysql.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.e_store.dao.CategoryDao;
import com.itheima.e_store.domain.CategoryBean;
import com.itheima.e_store.utils.JDBCUtils;
import com.itheima.e_store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<CategoryBean> getAllCategory() throws Exception {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		return queryRunner.query(sql, new BeanListHandler<CategoryBean>(CategoryBean.class));
	}

	/**
	 * ��ѯ���е������б���
	 */
	@Override
	public CategoryBean findAllCategoryByCid(CategoryBean category) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		CategoryBean cate = queryRunner.query(sql, new BeanHandler<CategoryBean>(CategoryBean.class),category.getCid());
		return cate;
	}

	/**
	 * ����cid�޸������
	 */
	@Override
	public boolean editCategory(CategoryBean category) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname = ? where cid=?";
		int result = queryRunner.update(sql,category.getCname(),category.getCid());
		if (result == 1) {
			updateRedis();
			return true;
		}
		return false;
	}

	/**
	 * �÷������������
	 */
	@Override
	public boolean addCategory(CategoryBean category) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category value(?,?)";
		Object[] params = {category.getCid(), category.getCname()};
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			updateRedis();
			return true;
		}
		return false;
	}

	/**
	 * �÷�����ɾ��������Ϣ
	 */
	@Override
	public boolean delCategoryByCid(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "delete from category where cid=?";
		int result = queryRunner.update(sql,cid);
		if (result == 1) {
			updateRedis();
			return true;
		}
		return false;
	}

	/**
	 * �÷��������ڸ���redis�б���ķ�����Ϣ
	 */
	private void updateRedis() {
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCategory");
		JedisUtils.closeJedis(jedis);
	}
}
