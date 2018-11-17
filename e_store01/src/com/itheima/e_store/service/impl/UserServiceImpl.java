package com.itheima.e_store.service.impl;

import java.sql.SQLException;

import com.itheima.e_store.dao.UserDao;
import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.service.UserService;
import com.itheima.e_store.utils.BeanFactory;

public class UserServiceImpl implements UserService {

	
	UserDao dao = (UserDao)BeanFactory.createDaoImpl("UserDao");
	
	/**
	 * 该方法是用户注册
	 * 
	 * @param UserBean
	 * @return true: 注册成功  false:注册失败
	 * @throws SQLException
	 */
	@Override
	public boolean registeUser(UserBean userBean) throws SQLException {
		return dao.registeUser(userBean);
	}

	
	/**
	 * 该方法是用户通过邮箱激活账户
	 * 
	 * @param code:激活码
	 * @return true:激活成功  false：激活失败
	 * @throws SQLException
	 */
	@Override
	public boolean active(String code) throws SQLException {
		System.out.println("2222"+code);
		UserBean user = new UserBean();
		//通过code查询用户
		user = dao.findUserByCode(code);
		if (null != user) {
			System.out.println("该用户存在");
			//如果该用户存在 ,修改该用户的信息
			user.setState(1);
			user.setCode(null);
			return dao.updateUser(user);
		}else {
			System.out.println("该用户不存在");
			//该用户不存在
			return false;
		}
	}

	/**
	 * 该方法是用户注册
	 * 
	 * @param username:用户名    
	 * @param password:密码
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
