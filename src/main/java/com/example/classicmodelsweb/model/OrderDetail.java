package com.example.classicmodelsweb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(OrderDetailId.class)
@Table(name="orderdetails")
public class OrderDetail {
	@Id
	@ManyToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name="orderNumber")
	private Order order;
	
	@Id
	@ManyToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name="productCode")
	private Product product;
	
	@Column(name="quantityOrdered")
	private int quantityOrdered;

	@Column(name="priceEach")
	private double priceEach;

	@Column(name="orderLineNumber")
	private short orderLineNumber;
	
	public OrderDetail(){
		
	}

	public OrderDetail(Order order, Product product, int quantityOrdered, double priceEach, short orderLineNumber) {
		super();
		this.order = order;
		this.product = product;
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
		this.orderLineNumber = orderLineNumber;
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

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public double getPriceEach() {
		return priceEach;
	}

	public void setPriceEach(double priceEach) {
		this.priceEach = priceEach;
	}

	public short getOrderLineNumber() {
		return orderLineNumber;
	}

	public void setOrderLineNumber(short orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}

	@Override
	public String toString() {
		return String.format("OrderDetail [order=%s, product=%s, quantityOrdered=%s, priceEach=%s, orderLineNumber=%s]",
				order, product, quantityOrdered, priceEach, orderLineNumber);
	}
}
