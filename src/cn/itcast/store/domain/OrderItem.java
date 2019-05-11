package cn.itcast.store.domain;

public class OrderItem {
	private String itemid;
	private int quantity;
	private double total;

	// 1_对象对应对象
	// 2_product,order可以携带更多的数据
	private Product product;
	private Order oreder;

	public OrderItem() {
		super();
	}

	public OrderItem(String itemid, int quantity, double total, Product product, Order oreder) {
		super();
		this.itemid = itemid;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
		this.oreder = oreder;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOreder() {
		return oreder;
	}

	public void setOreder(Order oreder) {
		this.oreder = oreder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemid == null) ? 0 : itemid.hashCode());
		result = prime * result + ((oreder == null) ? 0 : oreder.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (itemid == null) {
			if (other.itemid != null)
				return false;
		} else if (!itemid.equals(other.itemid))
			return false;
		if (oreder == null) {
			if (other.oreder != null)
				return false;
		} else if (!oreder.equals(other.oreder))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", quantity=" + quantity + ", total=" + total + ", product=" + product
				+ ", oreder=" + oreder + "]";
	}

}
