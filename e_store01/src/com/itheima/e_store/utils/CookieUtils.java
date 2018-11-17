package com.itheima.e_store.utils;

import javax.servlet.http.Cookie;

/**
 * 该类是有关Cookie的工具类
 * 
 * @author Administrator
 *
 */
public class CookieUtils {

	/**
	 * 该方法是通过cookie的名字来获取cookie
	 * 
	 * @param cookies
	 *            cookie数组
	 * @param name
	 *            要查找cookie的名字
	 * @return Cookie
	 */
	public static Cookie getCookieByName(Cookie[] cookies, String name) {

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				// 遍历数组如果找到名字为name的cookie则返回该cookie
				if (name.equals(cookieName)) {
					return cookie;
				}
			}
			// 如果遍历完数组还没有找到名字为name的cookie，返回null
			return null;
		}
		// 如果传过来的cookie数组为null则直接返回null
		return null;
	}
}
