package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.Order;

@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
	private EntityManager entityManager;
	
	@Autowired
	public OrderDAOImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	@Override
	public int countSearchOrdersByOid(int oid) {
		// TODO Auto-generated method stub
		return ((Long)this.entityManager
				.createQuery("select COUNT(o.orderNumber) from Order o where o.orderNumber=:id")
				.setParameter("id",oid)
				.getSingleResult()).intValue();
	}

	@Override
	public void removeOrder(int oid) {
		// TODO Auto-generated method stub
		this.entityManager.remove(this.entityManager.getReference(Order.class, oid));
	}

	@Override
	public Order getOrderRefById(int id) {
		// TODO Auto-generated method stub
		return this.entityManager.getReference(Order.class, id);
	}

	@Override
	public List<Order> searchOrders(Integer orderNumber, Integer cid, int offset, int limit, String status) {
		// TODO Auto-generated method stub
		return this.entityManager
				.createQuery(
						"select o from Order o join fetch o.customer as c where (:oid is null or :oid = '' or o.orderNumber = :oid) and (:id is null or :id = '' or c.customerNumber = :id) "
						+ " and (:status is null or :status = '' or o.orderStatus = :status)", Order.class)
				.setParameter("oid", orderNumber==null?null:orderNumber.toString())
				.setParameter("id", cid==null?null:cid.toString())
				.setParameter("status", status)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}
	
	@Override
	public void persistOrder(Order order) {
		// TODO Auto-generated method stub
		this.entityManager.persist(order);
	}

	@Override
	public int countSearchOrders(Integer orderNumber, Integer cid, String status) {
		// TODO Auto-generated method stub
		return ((Long)this.entityManager
				.createQuery("select COUNT(o.orderNumber) from Order o join o.customer as c where (:oid is null or :oid = '' or o.orderNumber = :oid) and (:id is null or :id = '' or c.customerNumber = :id) "
						+ " and (:status is null or :status = '' or o.orderStatus = :status)")
				.setParameter("oid", orderNumber==null?null:orderNumber.toString())
				.setParameter("id", cid==null?null:cid.toString())
				.setParameter("status", status)
				.getSingleResult()).intValue();
	}
}
