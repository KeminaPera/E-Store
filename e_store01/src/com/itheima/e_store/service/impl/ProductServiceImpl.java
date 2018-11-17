package com.itheima.e_store.service.impl;

import java.util.List;

import com.itheima.e_store.dao.ProductDao;
import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.service.ProductService;
import com.itheima.e_store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	ProductDao dao = (ProductDao)BeanFactory.createDaoImpl("ProductDao");
	
	@Override
	public List<Product> getNewProducts() throws Exception {
		return dao.getNewProducts();
	}

	@Override
	public List<Product> getHotProducts() throws Exception {
		return dao.getHotProducts();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		return dao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsWithCidAndPage(String currentNum, String cid) throws Exception {
		//创建product对象
		Product product = new  Product();
		//设置product相关属性，让其查询该种类下所有商品信息
		product.setCid(cid);
		product.setPflag(-1);
		int totalRecords = dao.getTotalRecords(product);
		int currentPageNum = Integer.parseInt(currentNum);
		//创建PageModel对象
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//通过当前页码和每页的数据条数查询product表，返回该页的数据
		List<Product> list = dao.getCurrentPageProducts(pageModel, cid);
		//绑定list
		
		System.out.println("这是得到的数据==="+list.size()+pageModel.getStartIndex()+pageModel.getPageSize());
		
		pageModel.setList(list);
		//绑定url
		pageModel.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
		return pageModel;
	}

	/**
	 * 以分页的形式查询所有未下架商品
	 */
	@Override
	public PageModel findProductByPflag(Product product, String curNum) throws Exception {
		//将当前页转换成整形数据
		int currentPageNum = Integer.parseInt(curNum);
		//未下架商品的总数
		//设置product相关属性，让其查询所有商品且满足pflag
		product.setCid("-1");
		int totalRecords = dao.getTotalRecords(product);
		//创建pagemodel对象
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//查询所有未下架商品
		List<Product> undeliveredProducts = dao.findProductByPflag(product, pageModel);
		//绑定list
		pageModel.setList(undeliveredProducts);
		//绑定url
		pageModel.setUrl("AdminProductServlet?method=findProductByPflag&pflag="+product.getPflag());
		//返回pagemodel
		return pageModel;
	}

	@Override
	public PageModel updateProduct(Product product, String curNum) throws Exception {
		
		//第一步：将该商品下线
		Product pro = dao.findProductByPid(product.getPid());
		pro.setPflag(1);
		dao.updateProduct(pro);
		
		//第二步
		//将当前页号转换成整数
		int currentPageNum = Integer.parseInt(curNum);
		//设置product相关的属性，让其查询所有商品，且满足pflag
		product.setCid("-1");
		//查询所有未下线商品的总数
		int totalRecords = dao.getTotalRecords(product);
		//创建pagemodel对象
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//将所有未下线的商品查询出来
		List<Product> undeliveredProducts = dao.findProductByPflag(product, pageModel);
		System.out.println(undeliveredProducts);
		//绑定list
		pageModel.setList(undeliveredProducts);
		//绑定url
		pageModel.setUrl("");
		//返回pagemodel
		return pageModel;
	}

	@Override
	public void addProduct(Product product) throws Exception {
		dao.addProduct(product);
	}

}
