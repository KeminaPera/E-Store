package com.itheima.e_store.dao;

import java.util.List;

import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;

public interface ProductDao {

	/**
	 * �÷����ǻ�ȡ��Ʒ���������pdate������(����)����Ʒ
	 * 
	 * @return 
	 * @throws Exception
	 */
	List<Product> getNewProducts() throws Exception;
	/**
	 * �÷����ǻ�ȡ��Ʒ������is_hot��1(������Ʒ)0(��������Ʒ)����Ʒ
	 * @return
	 * @throws Exception
	 */
	List<Product> getHotProducts() throws Exception;
	/**
	 * �÷����Ǹ���pid��ѯ��Ʒ
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	Product findProductByPid(String pid) throws Exception;
	/**
	 * �÷����ǻ�ȡ����һ���������ܼ�¼��
	 * 
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	int getTotalRecords(Product product) throws Exception;
	/**
	 * ��ȡ��ҳ����Ʒ����
	 * 
	 * @param pageModel
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	List<Product> getCurrentPageProducts(PageModel pageModel, String cid) throws Exception;
	/**
	 * ͨ���Ƿ��¼ܻ�ȡ��Ʒ��Ϣ
	 * 
	 * @param product
	 * @param pageModel
	 * @return
	 * @throws Exception
	 */
	List<Product> findProductByPflag(Product product, PageModel pageModel)throws Exception;
	/**
	 * ������Ʒ
	 * 
	 * @param product
	 * @throws Exception
	 */
	void updateProduct(Product product)throws Exception;
	boolean addProduct(Product product)throws Exception;

}
