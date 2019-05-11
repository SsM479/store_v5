package cn.itcast.store.domain;

public class CartItem {
	private Product product; // 携带图片路径，商品名称，商品价格
	private int num; // 该类别商品数量
	private double subTotal; // 小计,该类商品总价

	public CartItem() {
		super();
	}

	public CartItem(Product product, int num, double subTotal) {
		super();
		this.product = product;
		this.num = num;
		this.subTotal = subTotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getSubTotal() {
		return product.getShop_price() * num;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	

}
