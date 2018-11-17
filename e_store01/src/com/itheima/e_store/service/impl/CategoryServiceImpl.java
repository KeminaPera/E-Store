package com.itheima.e_store.service.impl;

import java.util.List;

import com.itheima.e_store.dao.CategoryDao;
import com.itheima.e_store.domain.CategoryBean;
import com.itheima.e_store.service.CategoryService;
import com.itheima.e_store.utils.BeanFactory;
import com.itheima.e_store.utils.UUIDUtils;

public class CategoryServiceImpl implements CategoryService {

	CategoryDao dao = (CategoryDao)BeanFactory.createDaoImpl("CategoryDao");
	
	public List<CategoryBean> getAllCategory() throws Exception{
		return dao.getAllCategory();
	}

	@Override
	public CategoryBean findAllCategoryByCid(CategoryBean category) throws Exception {

		return dao.findAllCategoryByCid(category);
	}

	@Override
	public boolean editCategory(CategoryBean category) throws Exception {
		return dao.editCategory(category);
	}

	@Override
	public boolean addCategory(String cname) throws Exception {
		CategoryBean category = new CategoryBean();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		return dao.addCategory(category);
	}

	@Override
	public List<CategoryBean> delCategoryByCid(String cid) throws Exception {
		dao.delCategoryByCid(cid);
		return dao.getAllCategory();
	}
}
