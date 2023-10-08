package com.example.classicmodelsweb.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.classicmodelsweb.dao.CustomerDAO;
import com.example.classicmodelsweb.dao.LoginAccDAO;
import com.example.classicmodelsweb.dao.OrderDAO;
import com.example.classicmodelsweb.dao.OrderDetailDAO;
import com.example.classicmodelsweb.dao.ProductDAO;
import com.example.classicmodelsweb.model.Customer;
import com.example.classicmodelsweb.model.Order;
import com.example.classicmodelsweb.model.OrderDetail;
import com.example.classicmodelsweb.model.OrderStatus;
import com.example.classicmodelsweb.model.Product;

@Service("orderSvc")
public class OrderServiceImpl implements OrderService {
	private OrderDAO dao;
	private LoginAccDAO accDAO;
	private CustomerDAO customerDAO;
	private OrderDetailDAO orderDetailDAO;
	private ProductDAO productDAO;

	public OrderServiceImpl(@Qualifier("orderDAO") OrderDAO dao, 
			@Qualifier("loginAccDAO") LoginAccDAO loginAccDAO,
			@Qualifier("customerDAO") CustomerDAO customerDAO,
			@Qualifier("orderDetailDAO") OrderDetailDAO orderDetailDAO,
			@Qualifier("productDAO") ProductDAO productDAO) {
		super();
		this.dao = dao;
		this.accDAO = loginAccDAO;
		this.customerDAO = customerDAO;
		this.orderDetailDAO = orderDetailDAO;
		this.productDAO = productDAO;
	}

	@Override
	public int getIdCusFromUserName(String usr) {
		// TODO Auto-generated method stub
		return this.accDAO.getIdCusFromUserName(usr);
	}

	
	@Override
	public List<OrderDetail> getListOrderDetail(int oid) {
		// TODO Auto-generated method stub
		return this.orderDetailDAO.getList(oid);
	}

	@Override
	public int countTotalProdByOrderId(int oid) {
		return this.orderDetailDAO.countTotalProd(oid);
	}
		
	@Override
	public int countSearchOrdersByOid(int oid) {
		// TODO Auto-generated method stub
		return this.dao.countSearchOrdersByOid(oid);
	}

	@Override
	public Product getProdToAdd(String id) {
		// TODO Auto-generated method stub
		return this.productDAO.getProdDetail(id);
	}

	@Override
	@Transactional
	public void createOrderDetail(int orderId, String prodCode, int quantity) {
		// TODO Auto-generated method stub
		Product product = this.productDAO.getProdDetail(prodCode);
		Order order = this.dao.getOrderRefById(orderId);
		OrderDetail detail = new OrderDetail(order, product, quantity, product.getBuyPrice()*(double)quantity, (short)1);
		this.orderDetailDAO.persist(detail);
	}

	@Override
	@Transactional
	public void createOrder(Date orderDate, Date reqDate, Date shippedDate, OrderStatus orderStatus, String comments,
			int custNo) {
		// TODO Auto-generated method stub
		Customer customer = this.customerDAO.getCustomerRefById(custNo);
		Order order = new Order(0, orderDate, reqDate, shippedDate, orderStatus, comments, customer, null);
		this.dao.persistOrder(order);
	}

	@Override
	@Transactional
	public void removeOrder(int oid) {
		// TODO Auto-generated method stub
		this.dao.removeOrder(oid);
	}

	public int countSearchCustomer(Integer customerNumber, String customerName, String contactLastName,
			String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state,
			String postalCode, String country, Integer salesRepEmployeeNumber, Double creditLimit, Integer limit,
			Integer offset) {
		// TODO Auto-generated method stub
		return this.customerDAO.countSearchCustomer(customerNumber, customerName, contactLastName, contactFirstName,
				phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber,
				creditLimit, limit, offset);
	}

	public List<Customer> searchCustomer(Integer customerNumber, String customerName, String contactLastName,
			String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state,
			String postalCode, String country, Integer salesRepEmployeeNumber, Double creditLimit, Integer limit,
			Integer offset) {
		// TODO Auto-generated method stub
		return this.customerDAO.searchCustomer(customerNumber, customerName, contactLastName, contactFirstName, phone,
				addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit,
				limit, offset);
	}
	
	@Override
	public int countSearchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP) {
		// TODO Auto-generated method stub
		return this.productDAO.countSearchProd(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP);
	}

	@Override
	public int countSearchOrders(Integer orderNumber, int cid, String status) {
		return this.dao.countSearchOrders(orderNumber,cid, status);
	}

	public Customer findCustomerById(Integer id) {
		// TODO Auto-generated method stub
		return this.customerDAO.findCustomerById(id);
	}

	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return this.customerDAO.updateCustomer(customer);
	}

	public boolean checkCustomerExist(Integer id) {
		// TODO Auto-generated method stub
		return this.customerDAO.checkCustomerExist(id);
	}

	public void deleteCustomer(Integer id) {
		// TODO Auto-generated method stub
		this.customerDAO.deleteCustomer(id);
	}

	public Customer createCustAccount(Customer customer) {
		// TODO Auto-generated method stub
		return this.customerDAO.createCustAccount(customer);
	}

	public int countOrdersByUser(Integer orderNumber, String usr, String status) {
		// TODO Auto-generated method stub
		Integer cid = usr==null||usr.equals("")?null:this.accDAO.getIdCusFromUserName(usr);
		return this.dao.countSearchOrders(orderNumber, cid, status);
	}
	
	@Override
	public List<String> getAllCust() {
		// TODO Auto-generated method stub
		return this.accDAO.getAllCustomers();
	}

	@Override
	public List<Order> searchOrdersByUser(Integer orderNumber, String usr, String status, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		Integer cid = usr==null||usr.equals("")?null:this.accDAO.getIdCusFromUserName(usr);
		return this.dao.searchOrders(orderNumber, cid, offset, limit, status);
	}

	@Override
	@Transactional
	public void updateOrderStatus(int oid,OrderStatus orderStatus) {
		Order order=this.dao.getOrderRefById(oid);
		order.setOrderStatus(orderStatus);
	}
}
