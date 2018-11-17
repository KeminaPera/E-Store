package com.itheima.e_store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ��������ý���
 * 
 * @author Administrator
 */
public class BeanFactory {

	/**
	 * ͨ��������ֺͽ���application.xml�����ļ���������Ӧ��ʵ��
	 * @param name
	 * @return
	 */
	public static Object createDaoImpl(String name) {
		try {
			//ͨ�������������application.xml
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			//ͨ��sax����xml�ļ�
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			//��ȡ�����ڵ�
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				//��ȡ����Ԫ�ص�����id������
				String idName = element.attributeValue("id");
				//�����Ƿ���ͬ
				if (idName.equals(name)) {
					String className = element.attributeValue("class");
					//������ͬ��ͨ�����䴴������
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
