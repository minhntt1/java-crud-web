package com.example.classicmodelsweb.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderNumber")
	private int orderNumber;
	
	@Column(name = "orderDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	
	@Column(name = "requiredDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date requiredDate;
	
	@Column(name = "shippedDate")	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date shippedDate;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Column(name = "comments")	
	private String comments;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
	@JoinColumn(name="customerNumber")
	private Customer customer;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "order")
	private List<OrderDetail> details;
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int orderNumber, Date orderDate, Date requiredDate, Date shippedDate, OrderStatus orderStatus,
			String comments, Customer customer, List<OrderDetail> details) {
		super();
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.shippedDate = shippedDate;
		this.orderStatus = orderStatus;
		this.comments = comments;
		this.customer = customer;
		this.details = details;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
