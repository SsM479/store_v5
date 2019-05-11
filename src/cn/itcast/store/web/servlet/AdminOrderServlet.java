package cn.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Order;
import cn.itcast.store.service.IOrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImpl;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	
	IOrderService orderService = new OrderServiceImpl();
	
	public String findOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取状态
		String st = req.getParameter("state");
		List<Order> list = null;
		if(null == st || "".equals(st)) {
			//如果获取不到状态，获取全部订单
			list = orderService.findAllOrders();
		}else {
			//可以获取到状态，查询不同状态的订单
			list = orderService.findAllOrders(st);
		}
		//将全部订单放入request
		req.setAttribute("allOrders", list);
		//转发到/admin/order/list.jsp
		return "/admin/order/list.jsp";
	}
	
	public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//服务端获取到订单id
		String oid = req.getParameter("id");
		//查询这个订单下所有的订单项以及订单项对应的商品信息，返回集合
		Order order = orderService.findOrderByOid(oid);
		//将返回的集合转换为JSON格式字符串，
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		//响应到客户端
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().println(jsonStr);
		return null;
	}
	
	public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取订单oid
		String oid = req.getParameter("oid");
		//通过订单id查询订单
		Order order = orderService.findOrderByOid(oid);
		//修改订单状态
		order.setState(3);
		//更新订单信息
		orderService.updateOrder(order);
		//重定向到查询已发货订单
		resp.sendRedirect("/store_v5/AdminOrderServlet?method=findOrders&state=3");
		return null;
	}

}
