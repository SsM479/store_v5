package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.ICategoryService;
import cn.itcast.store.service.IUserService;
import cn.itcast.store.service.serviceImp.CategoryServiceImpl;
import cn.itcast.store.service.serviceImp.UserServiceImpl;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	
	IUserService userService = new UserServiceImpl();

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/jsp/login.jsp";
	}
	
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 接收表单参数
		Map<String,String[]> map = request.getParameterMap();
		
		User user = new User();
		MyBeanUtils.populate(user, map);
		// 为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		System.out.println(user);
		
		// 调用业务层注册功能
		try {
			userService.userRegist(user);
			// 注册成功，向用户邮箱发送信息，跳转到提示页面
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活！");
			
		} catch (Exception e) {
			// 注册失败,跳转到提示页面
			request.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		return "/jsp/info.jsp";
	}
	
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 获取用户数据(账户/密码)
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		// 调用业务层登录功能
		User user02 = null;
		try {
			user02 = userService.userLogin(user);
			// 用户登录成功，将用户信息放入session中
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/store_v5/index.jsp");
			return null;
		} catch (Exception e) {
			// 用户登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			// 向request中放入登录失败的信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}
	
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 清楚session
		request.getSession().invalidate();
		// 重新定向到首页
		response.sendRedirect("/store_v5/index.jsp");
		return null;
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 获取激活码
		String code = request.getParameter("code");
		// 调用业务层激活功能
		IUserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		// 进行激活信息提示
		if(flag == true) {
			// 用户激活成功，向request放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "/jsp/login.jsp";
		}else {
			// 用户激活失败，向request放入提示信息，转发到提示页面
			request.setAttribute("msg", "用户激活失败，请重新激活！");
			return "/jsp/info.jsp";
		}
	}
}
