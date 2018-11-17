package com.itheima.e_store.test;

import java.util.HashMap;
import java.util.Map;

import com.itheima.e_store.domain.UserBean;
import com.itheima.e_store.utils.MyBeanUtils;

public class Test {

	public static void main(String[] args) {
		Map<String , String[]> map  = new HashMap<>();
		map.put("zhangsan", new String[] {"beijing"});
		MyBeanUtils.populate(UserBean.class, map);
	}
}
