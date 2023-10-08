package com.example.classicmodelsweb.dto;

import java.util.List;

import com.example.classicmodelsweb.model.Order;

public class OrderAdminForm {//NOTE CAI LOP FORM NAY PHAI DUNG GETER SETTER NEU KHOI TAO BANG CONSTRUCTOR SE LOI
	private List<Order> arrayList;

	public List<Order> getArrayList() {
		return arrayList;
	}

	public void setArrayList(List<Order> arrayList) {
		this.arrayList = arrayList;
	}
}
