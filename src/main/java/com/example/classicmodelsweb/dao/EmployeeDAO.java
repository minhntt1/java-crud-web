package com.example.classicmodelsweb.dao;

import java.util.List;

import com.example.classicmodelsweb.model.Employee;

public interface EmployeeDAO {
	List<Employee> getAllEmp();
	boolean checkEmpExists(int id);
	Employee getEmpRefById(int id);
}
