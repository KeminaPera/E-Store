package com.itheima.e_store.dao.mysql.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.e_store.dao.UserDao;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	/**
	 * �÷����������ݳ־ò�ʵ���û�ע��Ĺ���
	 * 
	 * @param UserBean
	 * @return true:��ʾ�ɹ� false:��ʾʧ��
	 * @throws SQLException
	 */
	@Override
	public boolean registeUser(UserBean userBean) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { userBean.getUid(), userBean.getUsername(), userBean.getPassword(), userBean.getName(),
				userBean.getEmail(), userBean.getTelephone(), userBean.getBirthday(), userBean.getSex(),
				userBean.getState(), userBean.getCode() };
		// ִ�в������
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			// �������ɹ� ����true
			return true;
		} else {
			// �������ʧ�ܣ�����false
			return false;
		}
	}

	/**
	 * �÷����Ǽ����û�ʱ�鿴��֤���Ƿ�ƥ��
	 * 
	 * @param UserBean
	 * @return true : ����ɹ�  false : ����ʧ��
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
	 * �÷����Ǽ����˻��ɹ�֮����ո��û��ļ�����       Ŀ�ģ���ֹ�ٴμ���
	 * 
	 * @param UserBean
	 * @return true : ��ʾ�޸ĳɹ�  false : ��ʾ�޸�ʧ��
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
	 * �÷������û���¼
	 * 
	 * @param username �û���
	 * @param password ����
	 * @return 
	 * @throws SQLEception
	 */
	@Override
	public UserBean login(String username, String password) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		UserBean userBean = queryRunner.query(sql, new BeanHandler<UserBean>(UserBean.class), username, password);
		if (userBean == null) {
			throw new RuntimeException("���벻��ȷ!");
		}else if (userBean.getState() == 0) {
			throw new RuntimeException("�˻�δ������ȼ��");
		}else {
			return userBean;
		}
	}

	/**
	 * �÷�����ͨ��ע����û�����ѯ�Ƿ��Ѿ����ڸ��û���
	 * 
	 * @param username
	 * @return false:��ʾ���û����Ѵ���  true:�û�������
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
