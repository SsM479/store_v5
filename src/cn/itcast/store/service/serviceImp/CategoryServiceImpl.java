package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.ICategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImpl;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.ICategoryService;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements ICategoryService {

	//ICategoryDao categoryDao = new CategoryDaoImpl();
	ICategoryDao categoryDao = (ICategoryDao)BeanFactory.createObject("CategoryDao");
	
	@Override
	public List<Category> getAllCats() throws Exception {
		return categoryDao.getAllCats();
	}

	@Override
	public void addCategory(Category c) throws Exception {
		//本质是向mysql插入一条数据
		categoryDao.addCategory(c);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
