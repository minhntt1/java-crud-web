package com.example.classicmodelsweb.service;

import java.math.BigInteger;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statsSvc")
public class StatisticServiceImpl implements StatisticService{
	private EntityManager entityManager;
	
	@Autowired
	public StatisticServiceImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public int[] getStat() {
		// TODO Auto-generated method stub
		Object[] res = (Object[])this.entityManager
				.createNativeQuery("select 1 as id,"
						+ "(select count(c.customerNumber) from customers c) as totalCust, "
						+ "(select count(e.employeenumber) from employees e) as totalEmp, "
						+ "(select count(p.productCode) from products p) as totalProds, "
						+ "(select count(o.orderNumber) from orders o) as totalOd "
						+ "from dual")
				.getSingleResult();
		
		return new int[]{((BigInteger)res[0]).intValue(),
				((BigInteger)res[1]).intValue(),
				((BigInteger)res[2]).intValue(),
				((BigInteger)res[3]).intValue(),
				((BigInteger)res[4]).intValue()};
	}
}
