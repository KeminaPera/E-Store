package com.itheima.e_store.utils;

import javax.servlet.http.Cookie;

/**
 * �������й�Cookie�Ĺ�����
 * 
 * @author Administrator
 *
 */
public class CookieUtils {

	/**
	 * �÷�����ͨ��cookie����������ȡcookie
	 * 
	 * @param cookies
	 *            cookie����
	 * @param name
	 *            Ҫ����cookie������
	 * @return Cookie
	 */
	public static Cookie getCookieByName(Cookie[] cookies, String name) {

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				// ������������ҵ�����Ϊname��cookie�򷵻ظ�cookie
				if (name.equals(cookieName)) {
					return cookie;
				}
			}
			// ������������黹û���ҵ�����Ϊname��cookie������null
			return null;
		}
		// �����������cookie����Ϊnull��ֱ�ӷ���null
		return null;
	}
}
