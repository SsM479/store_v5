package cn.itcast.store.web.servlet;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.ICategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImpl;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 在redis中获取全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if(null == jsonStr || "".equals(jsonStr)) {
			// 调用业务层获取全部分类
			ICategoryService categoryService = new CategoryServiceImpl();
			List<Category> list = categoryService.getAllCats();
			// 将全部分类转换为JSON格式的数据
			jsonStr = JSONArray.fromObject(list).toString();
			// 将获取到的JSON格式的数据存入redis
			jedis.set("allCats", jsonStr);
			System.out.println("redis缓存中没有数据");
		}else {
			System.out.println("redis缓存中有数据");
		}
		// 将全部分类信息响应到客户端
		// 告诉浏览器本次响应的数据时JSON格式的字符串
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().print(jsonStr);
		JedisUtils.closeJedis(jedis);
		
		return null;
	}

}
