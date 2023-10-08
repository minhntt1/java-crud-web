package com.example.classicmodelsweb.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="payments")
@IdClass(PaymentId.class)
public class Payment {
	@Id
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="customerNumber")
	private Customer customer;
	
	@Id
	@Column(name = "checkNumber")
	private String checkNumber;

	@Column(name = "paymentDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paymentDate;
	
	@Column(name = "amount")
	private double amount;
	
	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Payment(Customer customer, String checkNumber, Date paymentDate, double amount) {
		super();
		this.customer = customer;
		this.checkNumber = checkNumber;
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return String.format("Payment [customer=%s, checkNumber=%s, paymentDate=%s, amount=%s]", customer, checkNumber, paymentDate, amount);
	}
}
