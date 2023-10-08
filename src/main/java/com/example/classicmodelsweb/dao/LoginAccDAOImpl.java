package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("loginAccDAO")
public class LoginAccDAOImpl implements LoginAccDAO {
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public boolean checkPass(String user, String pass) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCustomers() {
		// TODO Auto-generated method stub
		return this.entityManager
				.createNativeQuery("select sl.username from sec_login sl where sl.id_employee is null")
				.getResultList();
	}


	@Override
	public int getIdCusFromUserName(String username) {
		// TODO Auto-generated method stub
		return ((Integer)this.entityManager
				.createNativeQuery("select sl.id_customer from sec_login sl where sl.username = :usr and sl.id_employee is null")
				.setParameter("usr", username)
				.getSingleResult()).intValue();
	}

	@Override
	public int getIdEmpFromUserName(String username) {
		// TODO Auto-generated method stub
		return ((Integer)this.entityManager
				.createNativeQuery("select sl.id_employee from sec_login sl where sl.username = :usr and sl.id_customer is null")
				.setParameter("usr", username)
				.getSingleResult()).intValue();
	}

	@Override
	public boolean getActiveStatusFromUserName(String username) {
		// TODO Auto-generated method stub
		return ((Byte)this.entityManager
				.createNativeQuery("select sl.active from sec_login sl where sl.username = :usr")
				.setParameter("usr", username)
				.getSingleResult()).intValue()>0;
	}

	@Override
	public void updateStatus(String userName, String pass, boolean status) {
		// TODO Auto-generated method stub
		this.entityManager
		.createNativeQuery("update "
				+ "	sec_login sl "
				+ "set "
				+ "	sl.password = :pass "
				+ "	sl.active = :status "
				+ "where "
				+ "	sl.username = :user")
		.setParameter("user", userName)
		.setParameter("pass", pass)
		.setParameter("status", status?1:0)
		.executeUpdate();
	}
}
