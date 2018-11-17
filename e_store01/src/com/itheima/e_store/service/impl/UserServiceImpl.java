package com.itheima.e_store.service.impl;

import java.sql.SQLException;

import com.itheima.e_store.dao.UserDao;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.service.UserService;
import com.itheima.e_store.utils.BeanFactory;

public class UserServiceImpl implements UserService {

	
	UserDao dao = (UserDao)BeanFactory.createDaoImpl("UserDao");
	
	/**
	 * �÷������û�ע��
	 * 
	 * @param UserBean
	 * @return true: ע��ɹ�  false:ע��ʧ��
	 * @throws SQLException
	 */
	@Override
	public boolean registeUser(UserBean userBean) throws SQLException {
		return dao.registeUser(userBean);
	}

	
	/**
	 * �÷������û�ͨ�����伤���˻�
	 * 
	 * @param code:������
	 * @return true:����ɹ�  false������ʧ��
	 * @throws SQLException
	 */
	@Override
	public boolean active(String code) throws SQLException {
		System.out.println("2222"+code);
		UserBean user = new UserBean();
		//ͨ��code��ѯ�û�
		user = dao.findUserByCode(code);
		if (null != user) {
			System.out.println("���û�����");
			//������û����� ,�޸ĸ��û�����Ϣ
			user.setState(1);
			user.setCode(null);
			return dao.updateUser(user);
		}else {
			System.out.println("���û�������");
			//���û�������
			return false;
		}
	}

	/**
	 * �÷������û�ע��
	 * 
	 * @param username:�û���    
	 * @param password:����
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserBean userLogin(String username, String password) throws SQLException {
		
		return dao.login(username, password);
	}


	@Override
	public boolean findUserByUsername(String username) throws SQLException {
		
		return dao.findUserByUsername(username);
	}

}
