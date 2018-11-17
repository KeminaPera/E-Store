package com.itheima.e_store.service;

import java.util.List;

import com.itheima.e_store.domain.CategoryBean;

public interface CategoryService {

	public List<CategoryBean> getAllCategory() throws Exception;

	public CategoryBean findAllCategoryByCid(CategoryBean category) throws Exception;

	public boolean editCategory(CategoryBean category) throws Exception;

	public boolean addCategory(String cname) throws Exception;

	public List<CategoryBean> delCategoryByCid(String cid) throws Exception;
}
