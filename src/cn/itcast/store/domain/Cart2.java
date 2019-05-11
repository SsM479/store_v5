package cn.itcast.store.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart2 {
	// 个数不确定的购物项
	private List<CartItem> list = new ArrayList();
	double total; //总计/积分
	
	
	//添加购物项到购物车
	//当用户点击加入购物车按钮，可以将当前要购买的商品id，商品数量发送到服务端，服务端根据商品id查询到商品信息
	//有了商品信息Product对象，有了要购买商品数量，当前的购物项也就可以获取到了
	public void addCartItemToCart(CartItem newCartItem) {
		//将当前的购物项加入购物车之前，判断之前是否买过这类商品
		//如果没有买过 list.add(cartItem)
		//如果买过:获取到原先的数量，获取到本次的属相，相加之后设置到原先购物项上
		
		//设置变量，默认为false，没有加入该商品
		boolean flag = false;
		CartItem oldCartItem = null;
		for (CartItem listCartItem : list) {
			if(listCartItem.getProduct().getPid().equals(newCartItem.getProduct().getPid())) {
				//购物车有该商品
				flag=true;
				oldCartItem = listCartItem;
			}
		}
		
		if(flag==false) {
			list.add(newCartItem);
		}else {
			//获取到原先的数量，获取到本次的属相，相加之后设置到原先购物项上
			oldCartItem.setNum(oldCartItem.getNum() + newCartItem.getNum());
		}
		
	}
	
	//移除购物项
	//用户点击移除购物项链接，可以将当前的购物类别的商品id发送到服务端
	public void removeCartItem(String pid) {
		//遍历list，看每个CartItem上的product对象上的id是否符合服务端获取到的pid，如果相等，删除当前的购物项
		for (CartItem cartItem : list) {
			if(cartItem.getProduct().getPid().equals(pid)) {
				//删除当前的cartItem
				//直接调用List.remove(cartItem);无法删除当前的cartItem,需要通过迭代器删除当前的购物项
			}
		}
	}
	//清空购物车
	public void clearCart() {
		list.clear();
	}
	
	
	
}
