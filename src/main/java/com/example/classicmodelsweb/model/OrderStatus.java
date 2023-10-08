package com.example.classicmodelsweb.model;

public enum OrderStatus {
	Shipped("Shipped"),
	Cancelled("Cancelled"),
	OnHold("OnHold"),
	Resolved("Resolved"),
	Disputed("Disputed"),
	InProcess("InProcess");

	OrderStatus(String string) {
		// TODO Auto-generated constructor stub
	}
}
