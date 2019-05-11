package cn.itcast.store.test;

public class TestMapListHandler {
	//根据订单id查询订单下所有的订单项以及对应的商品信息
	
	//SQL分析过程
	//SELECT * FROM orderItem,product   //笛卡尔积
	//SELECT * FROM orderItem o,product p WHERE o.pid=p.pid  //笛卡尔积基础上筛选有意义的数据
	//最终的结果
	//SELECT * FROM orderItem o,product p WHERE o.pid=p.pid AND oid=
	public void test00(){
		
	}
}
