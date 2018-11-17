package com.itheima.e_store.dao;

import java.sql.SQLException;

import com.itheima.e_store.domain.UserBean;

public interface UserDao {

	boolean registeUser(UserBean userBean) throws SQLException;

	UserBean findUserByCode(String code) throws SQLException;
	
	boolean updateUser(UserBean userBean) throws SQLException;

	UserBean login(String username, String password) throws SQLException;

	boolean findUserByUsername(String username) throws SQLException;

}
