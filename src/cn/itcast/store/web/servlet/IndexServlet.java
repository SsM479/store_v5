package cn.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Product;
import cn.itcast.store.service.IProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImpl;
import cn.itcast.store.web.base.BaseServlet;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*
		// 调用业务层功能：获取全部分类信息，返回集合
		List<Category> list = categoryService.getAllCats();
		// 将返回的集合放入request
		request.setAttribute("allCats", list);
		*/
		
		// 调用业务层查询最新商品，查询最热商品，返回2个集合
		IProductService productService = new ProductServiceImpl();
		List<Product> list01 = productService.findHots();
		List<Product> list02 = productService.findNews();
		// 将2个集合放入到request
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);
		// 转发到真实的首页
		return "/jsp/index.jsp";
	}

}
