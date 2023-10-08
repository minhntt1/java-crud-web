package com.example.classicmodelsweb.dao;

import java.util.List;

import com.example.classicmodelsweb.model.ProductLine;

public interface ProductLineDAO {
	List<ProductLine> getAllLines();
	ProductLine getByLineId(String id);
	boolean hasProdLineId(String id);
	ProductLine getProdLineRefById(String id);
}
