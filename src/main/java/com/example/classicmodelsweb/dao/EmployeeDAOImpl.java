package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.Employee;

@Repository("employeeDAO")
public class EmployeeDAOImpl implements EmployeeDAO{
	@Autowired
	private EntityManager entityManager;

	
	@Override
	public Employee getEmpRefById(int id) {
		// TODO Auto-generated method stub
		return this.entityManager.getReference(Employee.class, id);
	}

	@Override
	public boolean checkEmpExists(int id) {
		// TODO Auto-generated method stub
		return ((Long)this.entityManager
				.createQuery("select COUNT(e.employeeNumber) from Employee e where e.employeeNumber=:id")
				.setParameter("id", id)
				.getSingleResult())>0;
	}

	@Override
	public List<Employee> getAllEmp() {
		// TODO Auto-generated method stub
		return this.entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
	}
}
