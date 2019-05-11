package cn.itcast.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ICategoryService;
import cn.itcast.store.service.IProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImpl;
import cn.itcast.store.service.serviceImp.ProductServiceImpl;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.utils.UploadUtils;
import cn.itcast.store.web.base.BaseServlet;


@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	
	IProductService productService = new ProductServiceImpl();
	ICategoryService categoryService = new CategoryServiceImpl();
       
	public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取当前页
		int curNum = Integer.parseInt(req.getParameter("num"));
		//调用业务层查询全部商品信息返回PageModel
		PageModel pm = productService.findAllProductsWithPage(curNum);
		//将PageModel放入request
		req.setAttribute("page", pm);
		//转发到/admin/product/list.jsp
		return "/admin/product/list.jsp";
	}
	
	public String addProductUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取全部分类
		List<Category> list = categoryService.getAllCats();
		//将全部分类放入request
		req.setAttribute("allCats", list);
		//转发
		return "/admin/product/add.jsp";
	}
	
	public String addProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//存储表单中的数据
		Map<String,String> map = new HashMap<String,String>();
		//携带表单中的数据向service,dao传递
		Product product = new Product();
		
		try {
			//利用req.getInputStream();获取请求体中的全部数据，进行拆分和封装
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(req);
			//遍历集合
			for(FileItem item : list) {
				if(item.isFormField()) {
					//如果当前的FileItem对象是普通项，将普通项上name属性的值作为键,将获取到的内容作为值,放入MAP中{username<==>tom,password<==>1234}
					map.put(item.getFieldName(),item.getString());
				}else {
					//如果当前的FileItem对象是上传项
					// {username<==>tom,password<==>1234,userhead<===>image/11.bmp}
					//item.getInputStream();//推荐通过此API获取图片数据
					//获取到要保存文件的名称  
					//获取到原始的元件名称
					String oldFileName = item.getName();
					System.out.println("oldFileName: " + oldFileName);
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					System.out.println("newFileName: " + newFileName);
					//通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
					InputStream is = item.getInputStream();
					//获取到当前项目下products
					String realPath = getServletContext().getRealPath("/products/3");
					String dir = UploadUtils.getDir(newFileName);
					System.out.println("dir: " + dir);
					String path = realPath + dir;
					//内存中声明一个目录
					File newDir = new File(path);
					if(!newDir.exists()) {
						newDir.mkdirs();
					}
					//在服务端创建一个空文件(后缀必须和上传到服务端的文件名后缀一致)
					File finalFile = new File(newDir,newFileName);
					if(!finalFile.exists()) {
						finalFile.createNewFile();
					}
					//建立和空文件对应的输出流
					OutputStream os = new FileOutputStream(finalFile);
					//将输入流中的数据刷到输出流中
					IOUtils.copy(is, os);
					//释放资源
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					//向map中存入一个键值对的数据 userhead<===> /image/11.bmp
					map.put("pimage", "/products/3/" + dir + "/" + newFileName);
				}
			}
			//利用BeanUtils将MAP中的数据填充到user对象上
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			//调用servcie_dao将user上携带的数据存入数据仓库,重定向到查询全部商品信息路径
			productService.saveProduct(product);
			//重定向到查询全部商品信息路径
			resp.sendRedirect("/store_v5/AdminProductServlet?method=findAllProductsWithPage&num=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
