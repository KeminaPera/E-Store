package com.itheima.e_store.dao;

import java.util.List;

import com.itheima.e_store.domain.CategoryBean;

public interface CategoryDao {

	List<CategoryBean> getAllCategory() throws Exception;

	CategoryBean findAllCategoryByCid(CategoryBean category) throws Exception;

	boolean editCategory(CategoryBean category) throws Exception;

	boolean addCategory(CategoryBean category)throws Exception;

	boolean delCategoryByCid(String cid) throws Exception;

}
