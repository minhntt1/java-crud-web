package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.Payment;
import com.example.classicmodelsweb.model.PaymentId;

@Repository
public class PaymentDAO {
	private EntityManager entityManager;
	
	@Autowired
	public PaymentDAO(EntityManager entityManager) {
		// TODO Auto-generated constructor stub
		this.entityManager=entityManager;
	}
	
	public void deletePayment(PaymentId id) {
		this.entityManager.remove(this.getPaymentRef(id));
	}
	
	public void persistPayment(Payment payment) {
		this.entityManager.persist(payment);
	}
	
	public Payment getPaymentRef(PaymentId id) {
		return this.entityManager.getReference(Payment.class, id);
	}
	
	public List<Payment> searchPayments(Integer custNo, String checkNo, String minDate, String maxDate, Double minAm, Double maxAm, int limit, int offset){
		return this.entityManager
				.createQuery("select p from Payment p "
						+ "where "
						+ "(:custNo='' or :custNo is null or p.customer.customerNumber=:custNo) and "
						+ "(:checkNo='' or :checkNo is null or p.checkNumber like CONCAT('%',:checkNo,'%')) and "
						+ "(:minDate='' or :minDate is null or p.paymentDate>=:minDate) and "
						+ "(:maxDate='' or :maxDate is null or p.paymentDate<=:maxDate) and "
						+ "(:minAm='' or :minAm is null or p.amount>=:minAm) and "
						+ "(:maxAm='' or :maxAm is null or p.amount<=:maxAm)", Payment.class)
				.setParameter("custNo", custNo==null?"":custNo.toString())
				.setParameter("checkNo", checkNo)
				.setParameter("minDate", minDate)
				.setParameter("maxDate", maxDate)
				.setParameter("minAm", minAm==null?"":minAm.toString())
				.setParameter("maxAm", maxAm==null?"":maxAm.toString())
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}
}
