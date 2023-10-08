package com.example.classicmodelsweb.model;

import java.io.Serializable;
import java.util.Objects;

public class PaymentId implements Serializable{
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private String checkNumber;
	public PaymentId() {
		// TODO Auto-generated constructor stub
	}
	public PaymentId(Customer customer, String checkNumber) {
		super();
		this.customer = customer;
		this.checkNumber = checkNumber;
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
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj) return true;
		if(obj==null || this.getClass()!=obj.getClass()) return false;
		PaymentId detailId=(PaymentId)obj;
		return customer.getCustomerNumber()==detailId.getCustomer().getCustomerNumber()
				&& checkNumber.equals(detailId.getCheckNumber());
	}
	public int hashCode() {
		return Objects.hash(customer,checkNumber);
	}
}
