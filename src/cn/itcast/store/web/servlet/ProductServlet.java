package cn.itcast.store.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.IProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImpl;
import cn.itcast.store.web.base.BaseServlet;


@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	IProductService productService = new ProductServiceImpl();
	
	
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取商品pid
		String pid = request.getParameter("pid");
		// 根据商品pid查询商品信息
		Product product = productService.findProductByPid(pid);
		// 将获取到的商品放入request
		request.setAttribute("product", product);
		// 转发到/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}
	
	//findProductsByCidWithPage
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取cid,num
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		// 调用业务层功能:以分页形式插叙您当前类别下商品信息
		// 返回PageModel对象(1_当前商品信息2_分页3_url)
		PageModel pm = productService.findProductByCidWithPage(cid,curNum);
		// 将PageModel对象放入request
		request.setAttribute("page", pm);
		// 转发到/jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}
}
