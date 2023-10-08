package com.example.classicmodelsweb.dao;

import java.util.List;

public interface LoginAccDAO {
	int getIdCusFromUserName(String username);
	int getIdEmpFromUserName(String username);
	boolean getActiveStatusFromUserName(String username);
	void updateStatus(String userName, String pass, boolean status);
	boolean checkPass(String user, String pass);
	List<String> getAllCustomers();
}
