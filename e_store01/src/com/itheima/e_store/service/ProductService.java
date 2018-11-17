package com.itheima.e_store.service;

import java.util.List;

import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;

public interface ProductService {

	List<Product> getNewProducts() throws Exception;
	List<Product> getHotProducts() throws Exception;
	Product findProductByPid(String pid) throws Exception;
	PageModel findProductsWithCidAndPage(String currentNum, String cid)throws Exception;
	PageModel findProductByPflag(Product product, String curNum)throws Exception;
	PageModel updateProduct(Product product,String curNum) throws Exception;
	void addProduct(Product product)throws Exception;
}
