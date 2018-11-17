package com.itheima.e_store.service;

import java.sql.SQLException;

import com.itheima.e_store.domain.UserBean;

public interface UserService {

	public boolean registeUser(UserBean userBean) throws SQLException;

	public boolean active(String code) throws SQLException;

	public UserBean userLogin(String username, String password) throws SQLException;

	public boolean findUserByUsername(String username) throws SQLException;
}
