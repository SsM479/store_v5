package cn.itcast.store.service.serviceImp;

import java.sql.Connection;
import java.util.List;

import cn.itcast.store.dao.IOrderDao;
import cn.itcast.store.dao.daoImp.OrderDaoImpl;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.IOrderService;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.JDBCUtils;

public class OrderServiceImpl implements IOrderService {

	//IOrderDao orderDao = new OrderDaoImpl();
	IOrderDao orderDao = (IOrderDao)BeanFactory.createObject("OrderDao");
	
	public void saveOrder(Order order) throws Exception {
		//保存订单和订单下所有的订单项(同时成功，失败)
		/**
		try {
			JDBCUtils.startTransaction();//开启事务
			orderDao.saveOrder(order);
			for(OrderItem item : order.getList()) {
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();//提交
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();//回滚
		}
		*/
		
		Connection conn = null;
		try {
			//获取链接
			conn = JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			orderDao.saveOrder(conn,order);
			//保存订单项
			for(OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn,item);
			}
			//提交
			conn.commit();
		} catch (Exception e) {
			//回滚
			conn.rollback();
		} finally {
			if(null != conn) {
				conn.close();
				conn = null;
			}
		}
	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		//创建PageModel对象，目的：计算并且携带分页参数
		//SELECT count(*) FROM orders WHERE uid=?
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, 3, totalRecords);
		//关联集合
		//SELECT * FROM orders WHERE uid=? LIMIT ?,?
		List list = orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		return orderDao.findAllOrders();
	}

	@Override
	public List<Order> findAllOrders(String st) throws Exception {
		return orderDao.findAllOrders(st);
	}

}
