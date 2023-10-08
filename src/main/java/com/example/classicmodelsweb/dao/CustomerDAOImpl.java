package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.Customer;
import com.example.classicmodelsweb.model.Employee;

@Repository("customerDAO")
public class CustomerDAOImpl implements CustomerDAO {
	private final EntityManager entityManager;

	public CustomerDAOImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public Customer getCustomerRefById(Integer id) {
		// TODO Auto-generated method stub
		return this.entityManager.getReference(Customer.class, id);
	}

	@Override
	public int countSearchCustomer(Integer customerNumber, String customerName, String contactLastName,
			String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state,
			String postalCode, String country, Integer salesRepEmployeeNumber, Double creditLimit, Integer limit,
			Integer offset) {
		// TODO Auto-generated method stub
		
		Long res = (Long)this.entityManager
				.createQuery(
						"select COUNT(c.customerNumber) from Customer c join c.employee e where "
						+ "(:customerNumber is null or :customerNumber = '' or c.customerNumber like CONCAT(:customerNumber,'%')) "
						+ "and (:customerName is null or :customerName = '' or c.customerName like CONCAT('%',:customerName,'%')) "
						+ "and (:contactLastName is null or :contactLastName = '' or c.contactLastName like CONCAT('%',:contactLastName,'%')) "
						+ "and (:contactFirstName is null or :contactFirstName = '' or c.contactFirstName like CONCAT('%',:contactFirstName,'%')) "
						+ "and (:phone is null or :phone = '' or c.phone like CONCAT('%',:phone,'%')) "
						+ "and (:addressLine1 is null or :addressLine1 = '' or c.addressLine1 like CONCAT('%',:addressLine1,'%')) "
						+ "and (:addressLine2 is null or :addressLine2 = '' or c.addressLine2 like CONCAT('%',:addressLine2,'%')) "
						+ "and (:city is null or :city = '' or c.city like CONCAT('%',:city,'%')) "
						+ "and (:state is null or :state = '' or c.state like CONCAT('%',:state,'%')) "
						+ "and (:postalCode is null or :postalCode = '' or c.postalCode like CONCAT('%',:postalCode,'%')) "
						+ "and (:country is null or :country = '' or c.country like CONCAT('%',:country,'%')) "
						+ "and (:salesRepEmployeeNumber is null or :salesRepEmployeeNumber = '' or e.employeeNumber = :salesRepEmployeeNumber) "
						+ "and (:creditLimit is null or :creditLimit = '' or c.creditLimit = :creditLimit)")
				.setParameter("customerNumber", customerNumber)
				.setParameter("customerName", customerName)
				.setParameter("contactLastName", contactLastName)
				.setParameter("contactFirstName", contactFirstName)
				.setParameter("phone", phone)
				.setParameter("addressLine1", addressLine1)
				.setParameter("addressLine2", addressLine2)
				.setParameter("city", city)
				.setParameter("state", state)
				.setParameter("postalCode", postalCode)
				.setParameter("country", country)
				.setParameter("salesRepEmployeeNumber", salesRepEmployeeNumber)
				.setParameter("creditLimit", creditLimit)
				.getSingleResult();
		return res.intValue();
	}

	@Override
	public List<Customer> searchCustomer(Integer customerNumber, String customerName, String contactLastName,
			String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state,
			String postalCode, String country, Integer salesRepEmployeeNumber, Double creditLimit, Integer limit,
			Integer offset) {
		// TODO Auto-generated method stub
		return this.entityManager
				.createQuery("select c from Customer c join fetch c.employee e where "
						+ "(:customerNumber is null or :customerNumber = '' or c.customerNumber like CONCAT(:customerNumber,'%')) "
						+ "and (:customerName is null or :customerName = '' or c.customerName like CONCAT('%',:customerName,'%')) "
						+ "and (:contactLastName is null or :contactLastName = '' or c.contactLastName like CONCAT('%',:contactLastName,'%')) "
						+ "and (:contactFirstName is null or :contactFirstName = '' or c.contactFirstName like CONCAT('%',:contactFirstName,'%')) "
						+ "and (:phone is null or :phone = '' or c.phone like CONCAT('%',:phone,'%')) "
						+ "and (:addressLine1 is null or :addressLine1 = '' or c.addressLine1 like CONCAT('%',:addressLine1,'%')) "
						+ "and (:addressLine2 is null or :addressLine2 = '' or c.addressLine2 like CONCAT('%',:addressLine2,'%')) "
						+ "and (:city is null or :city = '' or c.city like CONCAT('%',:city,'%')) "
						+ "and (:state is null or :state = '' or c.state like CONCAT('%',:state,'%')) "
						+ "and (:postalCode is null or :postalCode = '' or c.postalCode like CONCAT('%',:postalCode,'%')) "
						+ "and (:country is null or :country = '' or c.country like CONCAT('%',:country,'%')) "
						+ "and (:salesRepEmployeeNumber is null or :salesRepEmployeeNumber = '' or e.employeeNumber = :salesRepEmployeeNumber) "
						+ "and (:creditLimit is null or :creditLimit = '' or c.creditLimit = :creditLimit)", Customer.class)
				.setParameter("customerNumber", customerNumber)
				.setParameter("customerName", customerName)
				.setParameter("contactLastName", contactLastName)
				.setParameter("contactFirstName", contactFirstName)
				.setParameter("phone", phone)
				.setParameter("addressLine1", addressLine1)
				.setParameter("addressLine2", addressLine2)
				.setParameter("city", city)
				.setParameter("state", state)
				.setParameter("postalCode", postalCode)
				.setParameter("country", country)
				.setParameter("salesRepEmployeeNumber", salesRepEmployeeNumber)
				.setParameter("creditLimit", creditLimit)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	public Customer findCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return this.entityManager
				.createQuery("select c from Customer c "
						+ "join fetch c.employee "
						+ "where c.customerNumber=:id", Customer.class)
				.setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		Customer customer2 = this.entityManager.getReference(Customer.class, customer.getCustomerNumber());
		Employee employee = this.entityManager.getReference(Employee.class, customer.getEmployee().getEmployeeNumber());
		
		customer2.setAddressLine1(customer.getAddressLine1());
		customer2.setAddressLine2(customer.getAddressLine2());
		customer2.setCity(customer.getCity());
		customer2.setContactFirstName(customer.getContactFirstName());
		customer2.setContactLastName(customer.getContactLastName());
		customer2.setCountry(customer.getCountry());
		customer2.setCreditLimit(customer.getCreditLimit());
		customer2.setCustomerName(customer.getCustomerName());
		customer2.setEmployee(employee);
		customer2.setPhone(customer.getPhone());
		customer2.setPostalCode(customer.getPostalCode());
		customer2.setState(customer.getState());
		
		return customer2;
	}

	@Override
	public boolean checkCustomerExist(Integer id) {
		// TODO Auto-generated method stub
		return ((Long)this.entityManager
				.createQuery("select COUNT(c.customerNumber) from Customer c where c.customerNumber=:id")
				.setParameter("id", id)
				.getSingleResult())>0;
	}

	@Override
	public void deleteCustomer(Integer id) {
		// TODO Auto-generated method stub
		Customer customer = this.entityManager.getReference(Customer.class, id);
		this.entityManager.remove(customer);
	}

	@Override
	public Customer createCustAccount(Customer customer) {
		// TODO Auto-generated method stub
		this.entityManager.persist(customer);;
		return customer;
	}
}
