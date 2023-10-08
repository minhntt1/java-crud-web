package com.example.classicmodelsweb.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.classicmodelsweb.dao.CustomerDAO;
import com.example.classicmodelsweb.dao.LoginAccDAO;
import com.example.classicmodelsweb.dao.PaymentDAO;
import com.example.classicmodelsweb.model.Payment;
import com.example.classicmodelsweb.model.PaymentId;

@Service
public class PaymentService {
	private PaymentDAO dao;
	private CustomerDAO customerDAO;
	private LoginAccDAO accDAO;
	
	@Autowired
	public PaymentService(PaymentDAO dao,CustomerDAO customerDAO,LoginAccDAO accDAO) {
		// TODO Auto-generated constructor stub
		this.dao=dao;
		this.customerDAO=customerDAO;
		this.accDAO=accDAO;
	}
	
	public int getIdCusFromUserName(String user) {
		return this.accDAO.getIdCusFromUserName(user);
	}
	
	@Transactional
	public void deletePayment(String userName, String checkNo) {
		int custNo=accDAO.getIdCusFromUserName(userName);
		PaymentId id=new PaymentId(this.customerDAO.getCustomerRefById(custNo), checkNo);
		this.dao.deletePayment(id); 
	}
	
	@Transactional
	public void persistPayment(String userName, String checkNo, Date date, Double amount) {
		int custNo=this.accDAO.getIdCusFromUserName(userName);
		PaymentId id=new PaymentId(this.customerDAO.getCustomerRefById(custNo), checkNo);
		Payment payment=new Payment(id.getCustomer(), id.getCheckNumber(), date, amount);
		this.dao.persistPayment(payment);
	}
	
	public List<Payment> searchPaymentsById(Integer custNo, String checkNo, String minDate, String maxDate, Double minAm, Double maxAm, int limit, int offset){
		minDate=minDate!=null&&minDate.equals("")||minDate==null?null:minDate;
		maxDate=maxDate!=null&&maxDate.equals("")||maxDate==null?null:maxDate;
		return this.dao.searchPayments(custNo, checkNo, minDate, maxDate, minAm, maxAm, limit, offset);
	}
	
	public List<Payment> searchPayments(String userName, String checkNo, String minDate, String maxDate, Double minAm, Double maxAm, int limit, int offset){
		Integer custNo=userName!=null&&!userName.equals("")?this.accDAO.getIdCusFromUserName(userName):null;
		minDate=minDate!=null&&minDate.equals("")||minDate==null?null:minDate;
		maxDate=maxDate!=null&&maxDate.equals("")||maxDate==null?null:maxDate;
		return this.dao.searchPayments(custNo, checkNo, minDate, maxDate, minAm, maxAm, limit, offset);
	}
}
