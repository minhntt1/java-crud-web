package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.OrderDetail;

@Repository("orderDetailDAO")
public class OrderDetailDAO {
	private EntityManager entityManager;
	
	@Autowired
	public OrderDetailDAO(EntityManager entityManager) {
		this.entityManager=entityManager;
		// TODO Auto-generated constructor stub
	}
	
	public void persist(OrderDetail detail) {
		this.entityManager.persist(detail);
	}
	
	public List<OrderDetail> getList(int number){
		return this.entityManager.createQuery("select od from OrderDetail od where od.order.orderNumber=:id", OrderDetail.class)
				.setParameter("id", number)
				.getResultList();
	}
	
	public int countTotalProd(int oid) {
		return ((Long)this.entityManager
				.createQuery("select count(*) from OrderDetail od where od.order.orderNumber=:id")
				.setParameter("id", oid)
				.getSingleResult()).intValue();
	}
}
