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
			// ����ҵ���߼��㣬��ѯ����δ�¼���Ʒ
			PageModel pageModel = service.findProductByPflag(product, curNum);
			// ��pageModelд���������
			request.setAttribute("pageModel", pageModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��תҳ��
		return "admin11/product/list.jsp";
	}

	public String addProUI(HttpServletRequest request, HttpServletResponse response) {

		return "admin11/product/add.jsp";
	}

	/**
	 * �÷����������Ʒ��Ϣ(����ͼƬ)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addProduct(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<>();
		
		try {
			System.out.println("12312133333333333333");
			//��ȡ����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			//��������
			for (FileItem item : list) {
				//��ͨ��
				if (item.isFormField()) {
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else {
					//�ϴ���
					//��ȡ��ԭʼ�ļ�������
					String oldFileName = item.getName();
					//�õ��µ��ļ�����
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					//ͨ��item��ȡ�����������󣬿��Ի�ȡ��ͼƬ�Ķ�������Ϣ
					InputStream is = item.getInputStream();
					//��ȡ��Ҫ��ӵ��ļ���·��
					String realPath = this.getServletContext().getRealPath("/products/3");
					String dir = UploadUtils.getDir(newFileName);
					String path = realPath + dir; 
					//���ڴ�������һ���ļ�
					File newDir = new java.io.File(path);
					if (!newDir.exists()) {
						//������ļ��в�����
						newDir.mkdirs();
					}
					File finalFile = new File(newDir, newFileName);
					if (!finalFile.exists()) {
						//��������ھʹ����µ��ļ�
						finalFile.createNewFile();
					}
					//������������ļ�������ϵ
					OutputStream os = new FileOutputStream(finalFile);
					//�����������������ˢ���������
					IOUtils.copy(is, os);
					//�ͷ���Դ
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					//��ӵ�map��
					map.put("pimage", "products/3"+dir+"/"+newFileName);
				}
			}
			//����product����
			Product product = new Product();
			//����beanUtils��������װ��product������
			BeanUtils.populate(product, map);
			//��δ�еĲ�����ӽ�ȥ
			product.setPdate(new Date());
			product.setPflag(0);
			product.setPid(UUIDUtils.getId());
			System.out.println(product);
			//����ҵ���߼�������µ���Ʒ
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
	 * ��ת����Ʒ�༭ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String toEditPage(HttpServletRequest request, HttpServletResponse response) {

		try {
			//��ȡ����pid
			String pid = request.getParameter("pid");
			//����pid��ѯ����Ʒ
			ProductService service = new ProductServiceImpl();
			Product product = service.findProductByPid(pid);
			//������Ʒд���������
			request.setAttribute("product", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//��תҳ��
		return "admin11/product/edit.jsp";
	}

	public String editProduct(HttpServletRequest request, HttpServletResponse response) {

		return null;

	}
}
