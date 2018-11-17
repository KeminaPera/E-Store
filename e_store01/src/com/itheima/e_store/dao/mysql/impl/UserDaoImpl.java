package com.itheima.e_store.dao.mysql.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.e_store.dao.UserDao;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	/**
	 * 该方法是在数据持久层实现用户注册的功能
	 * 
	 * @param UserBean
	 * @return true:表示成功 false:表示失败
	 * @throws SQLException
	 */
	@Override
	public boolean registeUser(UserBean userBean) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { userBean.getUid(), userBean.getUsername(), userBean.getPassword(), userBean.getName(),
				userBean.getEmail(), userBean.getTelephone(), userBean.getBirthday(), userBean.getSex(),
				userBean.getState(), userBean.getCode() };
		// 执行插入语句
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			// 如果插入成功 返回true
			return true;
		} else {
			// 如果插入失败，返回false
			return false;
		}
	}

	/**
	 * 该方法是激活用户时查看验证码是否匹配
	 * 
	 * @param UserBean
	 * @return true : 激活成功  false : 激活失败
	 * @throws SQLException
	 */
	@Override
	public UserBean findUserByCode(String code) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where code = ?";
		UserBean userBean = new UserBean();
		System.out.println(userBean.getCode());
		userBean = queryRunner.query(sql, new BeanHandler<UserBean>(UserBean.class), code);
		return userBean;
	}

	/**
	 * 该方法是激活账户成功之后，清空该用户的激活码       目的：防止再次激活
	 * 
	 * @param UserBean
	 * @return true : 表示修改成功  false : 表示修改失败
	 * @throws SQLExcption
	 */
	@Override
	public boolean updateUser(UserBean userBean) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username=?, password=?, name=?, email=?, telephone=?, birthday=?, sex=?, state=?, code=? where uid=?";
		System.out.println(sql);
		Object[] params = {  userBean.getUsername(), userBean.getPassword(), userBean.getName(),
				userBean.getEmail(), userBean.getTelephone(), userBean.getBirthday(), userBean.getSex(),
				userBean.getState(), userBean.getCode(), userBean.getUid() };
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 该方法是用户登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 
	 * @throws SQLEception
	 */
	@Override
	public UserBean login(String username, String password) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		UserBean userBean = queryRunner.query(sql, new BeanHandler<UserBean>(UserBean.class), username, password);
		if (userBean == null) {
			throw new RuntimeException("密码不正确!");
		}else if (userBean.getState() == 0) {
			throw new RuntimeException("账户未激活，请先激活！");
		}else {
			return userBean;
		}
	}

	/**
	 * 该方法是通过注册的用户名查询是否已经存在该用户名
	 * 
	 * @param username
	 * @return false:表示该用户名已存在  true:用户名可用
	 * @throws SQLException
	 */
	@Override
	public boolean findUserByUsername(String username) throws SQLException {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ?";
		UserBean userBean = queryRunner.query(sql, new BeanHandler<UserBean>(UserBean.class), username);
		if (userBean != null) {
			return false;
		}else {
			return true;
		}
	}

}
