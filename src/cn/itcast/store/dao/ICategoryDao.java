package cn.itcast.store.dao;

import java.util.List;

import cn.itcast.store.domain.Category;

public interface ICategoryDao {

	List<Category> getAllCats() throws Exception;

	void addCategory(Category c) throws Exception;

}
