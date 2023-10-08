package com.example.classicmodelsweb.service;

import java.util.Date;
import java.util.List;

import com.example.classicmodelsweb.model.Order;
import com.example.classicmodelsweb.model.OrderDetail;
import com.example.classicmodelsweb.model.OrderStatus;
import com.example.classicmodelsweb.model.Product;

public interface OrderService {
	int countOrdersByUser(Integer orderNumber, String usr, String status);
	List<Order> searchOrdersByUser(Integer orderNumber, String usr, String status, Integer offset, Integer limit);
	void createOrder(Date orderDate, Date reqDate, Date shippedDate, OrderStatus orderStatus, String comments, int custNo);
	void createOrderDetail(int orderId, String prodCode, int quantity);
	void removeOrder(int oid);
	Product getProdToAdd(String id);
	int countSearchOrders(Integer orderNumber, int cid, String status);
	int countSearchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP);
	int getIdCusFromUserName(String usr);
	int countSearchOrdersByOid(int oid);
	List<OrderDetail> getListOrderDetail(int oid);
	public int countTotalProdByOrderId(int oid);
	void updateOrderStatus(int oid,OrderStatus orderStatus);
	List<String> getAllCust();
}
