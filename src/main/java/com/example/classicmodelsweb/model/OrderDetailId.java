package com.example.classicmodelsweb.model;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Order order;
	private Product product;
	public OrderDetailId(Order order, Product product) {
		super();
		this.order = order;
		this.product = product;
	}
	public OrderDetailId() {
		// TODO Auto-generated constructor stub
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null || this.getClass()!=obj.getClass()) return false;
		OrderDetailId detailId=(OrderDetailId)obj;
		return order.getOrderNumber()==detailId.getOrder().getOrderNumber()
				&& product.getProductCode().equals(detailId.getProduct().getProductCode());
	}
	public int hashCode() {
		return Objects.hash(order,product);
	}
}
