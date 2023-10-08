package com.example.classicmodelsweb.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerNumber")
	private int customerNumber;

	@Column(name = "customerName")
	private String customerName;

	@Column(name = "contactLastName")
	private String contactLastName;

	@Column(name = "contactFirstName")
	private String contactFirstName;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "addressLine1")
	private String addressLine1;

	@Column(name = "addressLine2")
	private String addressLine2;
	
	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "postalCode")
	private String postalCode;

	@Column(name = "country")
	private String country;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "salesRepEmployeeNumber")
	private Employee employee;
	
	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "customer")
	private List<Order> orders;

	@OneToMany(cascade = CascadeType.REMOVE,mappedBy = "customer")
	private List<Payment> payments;
	
	@Column(name = "creditLimit")
	private BigDecimal creditLimit;
	
	public Customer(int customerNumber) {
		super();
		this.customerNumber = customerNumber;
	}

	public Customer(String customerName, String contactLastName, String contactFirstName, String phone,
			String addressLine1, String addressLine2, String city, String state, String postalCode, String country,
			Employee employee, BigDecimal creditLimit) {
		super();
		this.customerName = customerName;
		this.contactLastName = contactLastName;
		this.contactFirstName = contactFirstName;
		this.phone = phone;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.employee = employee;
		this.creditLimit = creditLimit;
	}
	
	public Integer getCustomerNumber() {
		return customerNumber;
	}



	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}



	public List<Order> getOrders() {
		return orders;
	}



	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}



	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer [customerNumber=%s, customerName=%s, contactLastName=%s, contactFirstName=%s, phone=%s, addressLine1=%s, addressLine2=%s, city=%s, state=%s, postalCode=%s, country=%s, creditLimit=%s]",
				customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2,
				city, state, postalCode, country, creditLimit);
	}
}
