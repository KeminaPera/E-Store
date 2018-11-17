package com.itheima.e_store.dao.mysql.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.e_store.dao.ProductDao;
import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> getNewProducts() throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select pid,pname,market_price,shop_price,pimage,is_hot,pdesc,pflag,cid from product where pflag = 0 order by pdate desc limit 0,9";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> getHotProducts() throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select pid,pname,market_price,shop_price,pimage,is_hot,pdesc,pflag,cid  from product where is_hot=1 and pflag=0 order by pdate desc limit 0,9";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pid =?";
		return queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	/**
	 * 获取product表中的数据的总条数
	 * 
	 * @exception
	 */
	@Override
	public int getTotalRecords(Product product) throws Exception {
		
		//相关变量的定义
		String sql = null;
		Long totalRecords = null;
		QueryRunner queryRunner= null;
		//获取到pflag
		int pflag = product.getPflag();
		
		/*QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());*/
		//获取到cid如果是-1，则表示不需要按照种类查询商品
		String cid = product.getCid();
		if ("-1".equals(cid)) {
			if (pflag == -1) {
				queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				//表示查询所有该种类下面的商品信息
				sql="select count(*) from product";
				totalRecords = (Long) queryRunner.query(sql, new ScalarHandler<>());
			}else {
				queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				//查询该种类下面pflag满足一定条件的商品信息
				sql = "select count(*) from product where pflag = ?";
				totalRecords = (Long) queryRunner.query(sql, new ScalarHandler<>(),product.getPflag());
			}
		}else {
			queryRunner = new QueryRunner(JDBCUtils.getDataSource());
			if (pflag == -1) {
				//表示查询所有该种类下面的商品信息
				sql="select count(*) from product where cid = ?";
				totalRecords = (Long) queryRunner.query(sql, new ScalarHandler<>(),product.getCid());
			}else {
				queryRunner = new QueryRunner(JDBCUtils.getDataSource());
				//查询该种类下面pflag满足一定条件的商品信息
				sql = "select count(*) from product where cid = ? and pflag = ?";
				totalRecords = (Long) queryRunner.query(sql, new ScalarHandler<>(),product.getCid(),product.getPflag());
			}
		}
		return totalRecords.intValue();
	}

	/**
	 * 该方法是获取该页面的产品数据
	 */
	@Override
	public List<Product> getCurrentPageProducts(PageModel pageModel, String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ?, ?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class),cid, pageModel.getStartIndex(), pageModel.getPageSize());
	}

	@Override
	public List<Product> findProductByPflag(Product product, PageModel pageModel) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pflag = ? limit ?, ?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class),product.getPflag(), pageModel.getStartIndex(),pageModel.getPageSize());
	}


	@Override
	public void updateProduct(Product product) throws Exception {
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid =?";
		Object[] params= {product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid(),product.getPid()};
		queryRunner.update(sql, params);
	}

	@Override
	public boolean addProduct(Product product) throws Exception {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid() };
		int result = queryRunner.update(sql, params);
		if (result == 1) {
			return true;
		}
		return false;
	}

}
