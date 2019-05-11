package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.IUserDao;
import cn.itcast.store.dao.daoImp.UserDaoImpl;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.IUserService;
import cn.itcast.store.utils.BeanFactory;

public class UserServiceImpl implements IUserService {
	
	//IUserDao userDao = new UserDaoImpl();
	IUserDao userDao = (IUserDao)BeanFactory.createObject("UserDao");
	@Override
	public void userRegist(User user)  throws SQLException{
		//实现注册功能
		userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		// 对DB发送select * from user where code=?
		User user = userDao.userActive(code);
		
		if(null != user) {
			// 可以根据激活码查询到一个用户
			// 修改用户的状态,清楚激活码
			user.setState(1);
			user.setCode(null);
			// 对数据库执行一次真实的更新操作
			userDao.updateUser(user);
			return true;
		}else {
			// 未查询到用户
			return false;
		}
	}

	@Override
	public User userLogin(User user) throws SQLException {
		User uu = userDao.userLogin(user);
		if(null == uu) {
			// 密码不正确
			throw new RuntimeException("密码错误！" + user.getUsername() + "," + user.getPassword());
		}else if(uu.getState() == 0) {
			throw new RuntimeException("账户未激活！");
		}else {
			return uu;
		}
	}
	
}
