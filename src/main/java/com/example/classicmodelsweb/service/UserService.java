package com.example.classicmodelsweb.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.classicmodelsweb.dao.CustomerDAO;
import com.example.classicmodelsweb.dao.EmployeeDAO;
import com.example.classicmodelsweb.dao.LoginAccDAO;
import com.example.classicmodelsweb.model.Customer;
import com.example.classicmodelsweb.model.Employee;

@Service("userSvc")
public class UserService {
	private CustomerDAO customerDAO;
	private LoginAccDAO accDAO;
	private EmployeeDAO employeeDAO;
	
	public UserService(@Qualifier("customerDAO") CustomerDAO customerDAO, @Qualifier("loginAccDAO") LoginAccDAO accDAO, @Qualifier("employeeDAO") EmployeeDAO employeeDAO) {
		super();
		this.customerDAO = customerDAO;
		this.accDAO = accDAO;
		this.employeeDAO = employeeDAO;
	}
	
	public boolean checkPass(String user, String pass) {
		// TODO Auto-generated method stub
		return this.accDAO.checkPass(user, pass);
	}

	public void updateStatus(String userName, String pass, boolean status) {
		// TODO Auto-generated method stub
		this.accDAO.updateStatus(userName, pass, status);
	}

	public boolean checkEmpExists(int id) {
		// TODO Auto-generated method stub
		return this.employeeDAO.checkEmpExists(id);
	}

	public List<Employee> getAllEmp() {
		// TODO Auto-generated method stub
		return this.employeeDAO.getAllEmp();
	}

	public int getIdCusFromUserName(String username) {
		// TODO Auto-generated method stub
		return this.accDAO.getIdCusFromUserName(username);
	}

	public int getIdEmpFromUserName(String username) {
		// TODO Auto-generated method stub
		return this.accDAO.getIdEmpFromUserName(username);
	}

	public boolean getActiveStatusFromUserName(String username) {
		// TODO Auto-generated method stub
		return this.accDAO.getActiveStatusFromUserName(username);
	}

	public int countSearchCustomer(Integer customerNumber, String customerName, String contactLastName,
			String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state,
			String postalCode, String country, Integer salesRepEmployeeNumber, Double creditLimit, Integer limit,
			Integer offset) {
		// TODO Auto-generated method stub
		return this.customerDAO.countSearchCustomer(customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit, limit, offset);
	}

	public List<Customer> searchCustomer(Integer customerNumber, String customerName, String contactLastName,
			String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state,
			String postalCode, String country, Integer salesRepEmployeeNumber, Double creditLimit, Integer limit,
			Integer offset) {
		// TODO Auto-generated method stub
		return this.customerDAO.searchCustomer(customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit, limit, offset);
	}

	public Customer findCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return this.customerDAO.findCustomerById(id);
	}

	@Transactional
	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return this.customerDAO.updateCustomer(customer);
	}

	public boolean checkCustomerExist(Integer id) {
		// TODO Auto-generated method stub
		return this.customerDAO.checkCustomerExist(id);
	}

	@Transactional
	public void deleteCustomer(Integer id) {
		// TODO Auto-generated method stub
		this.customerDAO.deleteCustomer(id);
	}

	@Transactional
	public Customer createCustAccount(Customer customer) {
		// TODO Auto-generated method stub
		return this.customerDAO.createCustAccount(customer);
	}
}
