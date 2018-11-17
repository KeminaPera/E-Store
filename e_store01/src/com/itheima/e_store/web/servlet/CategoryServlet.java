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
			// 获取jedis
			jedis = JedisUtils.getJedis();
			//查看redis中是否有key为allCategory的数据
			String allCategory = jedis.get("allCategory");
			// 如果redis中没有缓存的数据
			if (null == allCategory || "".equals(allCategory)) {
				CategoryService service = new CategoryServiceImpl();
				List<CategoryBean> allCats = service.getAllCategory();
				String jsonStr = JSONArray.fromObject(allCats).toString();
				//将查询出来的数据添加到redis中
				jedis.set("allCategory", jsonStr);
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().write(jsonStr);
			}
			//如果redis中有缓存的数据，直接将redis中的数据返回给客户端
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
