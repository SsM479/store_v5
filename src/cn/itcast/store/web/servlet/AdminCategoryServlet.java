package cn.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.ICategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImpl;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	
	ICategoryService categoryService = new CategoryServiceImpl();
	
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return "/admin/category/add.jsp";
	}
	
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取全部分类信息
		List<Category> list = categoryService.getAllCats();
		//全部分类信息放入request
		req.setAttribute("allCats", list);
		//转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";
	}
	
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取分类的名称
		String cname = req.getParameter("cname");
		//创建分类id
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCid(id);
		c.setCname(cname);
		//调用业务层添加分类功能
		categoryService.addCategory(c);
		//重定向到查询全部分类信息
		resp.sendRedirect("/store_v5/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	

}
