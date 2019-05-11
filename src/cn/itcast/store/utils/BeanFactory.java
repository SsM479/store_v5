package cn.itcast.store.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.itcast.store.dao.IUserDao;
import cn.itcast.store.domain.User;

public class BeanFactory {
	
	//解析XML
	public static Object createObject(String name){
		try {
			//通过传递过来的name获取application.xml中name对应的class值
			//获取到Document对象
			SAXReader read = new SAXReader();
			//如何获取application.xml文件的输入流(application.xml必须位于src下)
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document doc = read.read(is);
			//通过Docuemnt对象获取根节点
			Element rootElement = doc.getRootElement();
			//通过根节点获取到根节点下所有子节点bean，返回集合
			List<Element> list = rootElement.elements();
			//遍历集合，判断每个元素上的id的值是否和当前的name一致
			for(Element ele : list) {
				//ele相当于beans节点下的每个bean
				//获取到当前节点的id属性值
				String id = ele.attributeValue("id");
				if(id.equals(name)) {
					//如果一致，获取到当前元素上class属性值
					String str = ele.attributeValue("class");
					//通过反射床架安对象并且返回
					Class clazz = Class.forName(str);
					//利用class值通过反射创建对象返回
					return clazz.newInstance();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws SQLException {
		IUserDao ud = (IUserDao)BeanFactory.createObject("UserDao");
		User user = new User();
		user.setUsername("aaa");
		user.setPassword("aaa");
		User uu = ud.userLogin(user);
		System.out.println(uu);
		
	}
}
