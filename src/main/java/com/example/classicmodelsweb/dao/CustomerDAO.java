package com.example.classicmodelsweb.dao;

import java.util.List;

import com.example.classicmodelsweb.model.Customer;

public interface CustomerDAO {
	int countSearchCustomer(
			Integer customerNumber,
			String customerName,
			String contactLastName,
			String contactFirstName,
			String phone,
			String addressLine1,
			String addressLine2,
			String city,
			String state,
			String postalCode,
			String country,
			Integer salesRepEmployeeNumber,
			Double creditLimit,
			Integer limit,
			Integer offset);
	
	List<Customer> searchCustomer(
			Integer customerNumber,
			String customerName,
			String contactLastName,
			String contactFirstName,
			String phone,
			String addressLine1,
			String addressLine2,
			String city,
			String state,
			String postalCode,
			String country,
			Integer salesRepEmployeeNumber,
			Double creditLimit,
			Integer limit,
			Integer offset);
	
	Customer findCustomerById(Integer id);
	
	Customer updateCustomer(Customer customer);
	
	boolean checkCustomerExist(Integer id);
	
	void deleteCustomer(Integer id);
	
	Customer createCustAccount(Customer customer);
	
	Customer getCustomerRefById(Integer id);
}
