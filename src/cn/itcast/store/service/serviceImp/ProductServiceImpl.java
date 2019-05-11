package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.IProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImpl;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.IProductService;
import cn.itcast.store.utils.BeanFactory;

public class ProductServiceImpl implements IProductService {

	//IProductDao productDao = new ProductDaoImpl();	
	IProductDao productDao = (IProductDao)BeanFactory.createObject("ProductDao");
	
	@Override
	public List<Product> findHots() throws Exception {
		return productDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		return productDao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		return productDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductByCidWithPage(String cid, int curNum) throws Exception {
		// 创建PageModel对象 目的:计算分页参数
		// 统计当前分类下商品个数 SELECT COUNT(*) FROM product WHERE cid=?
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum,totalRecords,12);
		// 关联集合 SELECT * FROM product WHERE cid=? limit ?,?
		List list = productDao.findProductByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		// 关联Url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid=1");
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		//创建对象
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, 5,totalRecords);
		//关联集合 SELECT * FROM product LIMIT ?,?
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		productDao.saveProduct(product);
	}

}
