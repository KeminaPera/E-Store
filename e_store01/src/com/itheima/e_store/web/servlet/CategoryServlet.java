package com.itheima.e_store.web.servlet;

import com.itheima.e_store.domain.CategoryBean;
import com.itheima.e_store.service.CategoryService;
import com.itheima.e_store.service.impl.CategoryServiceImpl;
import com.itheima.e_store.utils.JedisUtils;
import com.itheima.e_store.web.base.BaseServlet;
import com.mysql.fabric.Response;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String getAllCategory(HttpServletRequest request, HttpServletResponse response) {

		Jedis jedis = null;

		try {
			// ��ȡjedis
			jedis = JedisUtils.getJedis();
			//�鿴redis���Ƿ���keyΪallCategory������
			String allCategory = jedis.get("allCategory");
			// ���redis��û�л��������
			if (null == allCategory || "".equals(allCategory)) {
				CategoryService service = new CategoryServiceImpl();
				List<CategoryBean> allCats = service.getAllCategory();
				String jsonStr = JSONArray.fromObject(allCats).toString();
				//����ѯ������������ӵ�redis��
				jedis.set("allCategory", jsonStr);
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().write(jsonStr);
			}
			//���redis���л�������ݣ�ֱ�ӽ�redis�е����ݷ��ظ��ͻ���
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(allCategory);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return null;
	}
}
