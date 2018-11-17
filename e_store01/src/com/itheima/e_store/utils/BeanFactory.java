package com.itheima.e_store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 该类的作用解耦
 * 
 * @author Administrator
 */
public class BeanFactory {

	/**
	 * 通过类的名字和解析application.xml配置文件创建出对应的实现
	 * @param name
	 * @return
	 */
	public static Object createDaoImpl(String name) {
		try {
			//通过类加载器加载application.xml
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			//通过sax解析xml文件
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			//获取到根节点
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				//获取到该元素的属性id的名称
				String idName = element.attributeValue("id");
				//名字是否相同
				if (idName.equals(name)) {
					String className = element.attributeValue("class");
					//名字相同，通过反射创建对象
					System.out.println(className);
					Class<?> cla = Class.forName(className);
					Object clazz = cla.newInstance();
					return clazz;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
