package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.ProductLine;

@Repository("productLineDAO")
public class ProductLineDAOImpl implements ProductLineDAO {
	private final EntityManager entityManager;
	
	@Override
	public ProductLine getProdLineRefById(String id) {
		// TODO Auto-generated method stub
		return this.entityManager.getReference(ProductLine.class,id);
	}

	@Autowired
	public ProductLineDAOImpl(EntityManager entityManager) {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	
	@Override
	public boolean hasProdLineId(String id) {
		// TODO Auto-generated method stub
		return ((Long)this.entityManager.createQuery("select COUNT(p.productLine) from ProductLine p where p.productLine=:id")
				.setParameter("id", id)
				.getSingleResult())>0;
	}

	@Override
	public List<ProductLine> getAllLines() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("select pl from ProductLine pl", ProductLine.class)
				.getResultList();
	}

	@Override
	public ProductLine getByLineId(String id) {
		// TODO Auto-generated method stub
		return this.entityManager.find(ProductLine.class, id);
	}
}
