package com.itheima.e_store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itheima.e_store.domain.PageModel;
import com.itheima.e_store.domain.Product;
import com.itheima.e_store.service.ProductService;
import com.itheima.e_store.service.impl.ProductServiceImpl;
import com.itheima.e_store.utils.UUIDUtils;
import com.itheima.e_store.utils.UploadUtils;
import com.itheima.e_store.web.base.BaseServlet;

public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findProductByPflag(HttpServletRequest request, HttpServletResponse response) {

		try {
			String pflag = request.getParameter("pflag");
			int flag = Integer.parseInt(pflag);
			String curNum = request.getParameter("num");
			Product product = new Product();
			product.setPflag(flag);
			ProductService service = new ProductServiceImpl();
			// 调用业务逻辑层，查询所有未下架商品
			PageModel pageModel = service.findProductByPflag(product, curNum);
			// 将pageModel写入域对象中
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 跳转页面
		return "admin11/product/list.jsp";
	}

	public String addProUI(HttpServletRequest request, HttpServletResponse response) {

		return "admin11/product/add.jsp";
	}

	/**
	 * 该方法是添加商品信息(包含图片)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addProduct(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<>();
		
		try {
			System.out.println("12312133333333333333");
			//获取参数
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			//遍历集合
			for (FileItem item : list) {
				//普通项
				if (item.isFormField()) {
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else {
					//上传项
					//获取到原始文件的名称
					String oldFileName = item.getName();
					//得到新的文件名称
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					//通过item获取到数据流对象，可以获取到图片的二进制信息
					InputStream is = item.getInputStream();
					//获取到要添加的文件的路径
					String realPath = this.getServletContext().getRealPath("/products/3");
					String dir = UploadUtils.getDir(newFileName);
					String path = realPath + dir; 
					//在内存中声明一个文件
					File newDir = new java.io.File(path);
					if (!newDir.exists()) {
						//如果该文件夹不存在
						newDir.mkdirs();
					}
					File finalFile = new File(newDir, newFileName);
					if (!finalFile.exists()) {
						//如果不存在就创建新的文件
						finalFile.createNewFile();
					}
					//创建输出流和文件发生关系
					OutputStream os = new FileOutputStream(finalFile);
					//将输入流里面的数据刷到输出流里
					IOUtils.copy(is, os);
					//释放资源
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					//添加到map中
					map.put("pimage", "products/3"+dir+"/"+newFileName);
				}
			}
			//创建product对象
			Product product = new Product();
			//利用beanUtils将参数封装到product对象中
			BeanUtils.populate(product, map);
			//将未有的参数添加进去
			product.setPdate(new Date());
			product.setPflag(0);
			product.setPid(UUIDUtils.getId());
			System.out.println(product);
			//调用业务逻辑层添加新的商品
			ProductService service = new ProductServiceImpl();
			service.addProduct(product);
			response.sendRedirect("AdminProductServlet?method=findProductByPflag&num=1&pflag=0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	};

	public String pushdownProduct(HttpServletRequest request, HttpServletResponse response) {

		try {
			String pid = request.getParameter("pid");
			String curNum = request.getParameter("num");
			ProductService service = new ProductServiceImpl();
			Product product = new Product();
			product.setPid(pid);
			PageModel pageModel = service.updateProduct(product, curNum);
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin11/product/list.jsp";

	}

	/**
	 * 跳转到商品编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String toEditPage(HttpServletRequest request, HttpServletResponse response) {

		try {
			//获取参数pid
			String pid = request.getParameter("pid");
			//根据pid查询该商品
			ProductService service = new ProductServiceImpl();
			Product product = service.findProductByPid(pid);
			//将该商品写入域变量中
			request.setAttribute("product", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//跳转页面
		return "admin11/product/edit.jsp";
	}

	public String editProduct(HttpServletRequest request, HttpServletResponse response) {

		return null;

	}
}
