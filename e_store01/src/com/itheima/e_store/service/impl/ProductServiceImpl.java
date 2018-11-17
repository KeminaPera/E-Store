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
		//����product����
		Product product = new  Product();
		//����product������ԣ������ѯ��������������Ʒ��Ϣ
		product.setCid(cid);
		product.setPflag(-1);
		int totalRecords = dao.getTotalRecords(product);
		int currentPageNum = Integer.parseInt(currentNum);
		//����PageModel����
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//ͨ����ǰҳ���ÿҳ������������ѯproduct�����ظ�ҳ������
		List<Product> list = dao.getCurrentPageProducts(pageModel, cid);
		//��list
		
		System.out.println("���ǵõ�������==="+list.size()+pageModel.getStartIndex()+pageModel.getPageSize());
		
		pageModel.setList(list);
		//��url
		pageModel.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
		return pageModel;
	}

	/**
	 * �Է�ҳ����ʽ��ѯ����δ�¼���Ʒ
	 */
	@Override
	public PageModel findProductByPflag(Product product, String curNum) throws Exception {
		//����ǰҳת������������
		int currentPageNum = Integer.parseInt(curNum);
		//δ�¼���Ʒ������
		//����product������ԣ������ѯ������Ʒ������pflag
		product.setCid("-1");
		int totalRecords = dao.getTotalRecords(product);
		//����pagemodel����
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//��ѯ����δ�¼���Ʒ
		List<Product> undeliveredProducts = dao.findProductByPflag(product, pageModel);
		//��list
		pageModel.setList(undeliveredProducts);
		//��url
		pageModel.setUrl("AdminProductServlet?method=findProductByPflag&pflag="+product.getPflag());
		//����pagemodel
		return pageModel;
	}

	@Override
	public PageModel updateProduct(Product product, String curNum) throws Exception {
		
		//��һ����������Ʒ����
		Product pro = dao.findProductByPid(product.getPid());
		pro.setPflag(1);
		dao.updateProduct(pro);
		
		//�ڶ���
		//����ǰҳ��ת��������
		int currentPageNum = Integer.parseInt(curNum);
		//����product��ص����ԣ������ѯ������Ʒ��������pflag
		product.setCid("-1");
		//��ѯ����δ������Ʒ������
		int totalRecords = dao.getTotalRecords(product);
		//����pagemodel����
		PageModel pageModel = new PageModel(currentPageNum, totalRecords, 12);
		//������δ���ߵ���Ʒ��ѯ����
		List<Product> undeliveredProducts = dao.findProductByPflag(product, pageModel);
		System.out.println(undeliveredProducts);
		//��list
		pageModel.setList(undeliveredProducts);
		//��url
		pageModel.setUrl("");
		//����pagemodel
		return pageModel;
	}

	@Override
	public void addProduct(Product product) throws Exception {
		dao.addProduct(product);
	}

}
