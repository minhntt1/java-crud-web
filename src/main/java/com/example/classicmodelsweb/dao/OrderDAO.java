package com.example.classicmodelsweb.dao;

import java.util.List;

import com.example.classicmodelsweb.model.Order;

public interface OrderDAO {
	List<Order> searchOrders(Integer orderNumber, Integer cid, int offset, int limit, String status);
	int countSearchOrders(Integer orderNumber, Integer cid, String status);
	Order getOrderRefById(int id);
	void persistOrder(Order order);
	void removeOrder(int oid);
	int countSearchOrdersByOid(int oid);
}
