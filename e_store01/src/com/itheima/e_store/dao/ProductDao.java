package com.itheima.e_store.dao;

import java.util.List;

import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;

public interface ProductDao {

	/**
	 * 该方法是获取商品表里面根据pdate最近添加(最新)的商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	List<Product> getNewProducts() throws Exception;
	/**
	 * 该方法是获取商品表里面is_hot是1(热卖商品)0(非热卖商品)的商品
	 * @return
	 * @throws Exception
	 */
	List<Product> getHotProducts() throws Exception;
	/**
	 * 该方法是根据pid查询商品
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	Product findProductByPid(String pid) throws Exception;
	/**
	 * 该方法是获取满足一定条件的总记录数
	 * 
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	int getTotalRecords(Product product) throws Exception;
	/**
	 * 获取该页的商品数据
	 * 
	 * @param pageModel
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	List<Product> getCurrentPageProducts(PageModel pageModel, String cid) throws Exception;
	/**
	 * 通过是否下架获取商品信息
	 * 
	 * @param product
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	List<Product> findProductByPflag(Product product, PageModel pageModel)throws Exception;
	/**
	 * 更新商品
	 * 
	 * @param product
	 * @throws Exception
	 */
	void updateProduct(Product product)throws Exception;
	boolean addProduct(Product product)throws Exception;

}
