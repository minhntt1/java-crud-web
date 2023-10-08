package com.example.classicmodelsweb.service;

import com.example.classicmodelsweb.dao.ProductDAO;
import com.example.classicmodelsweb.dao.ProductLineDAO;
import com.example.classicmodelsweb.model.Product;

public interface ProductService extends ProductDAO, ProductLineDAO {
	Product generateProdWithCode(Product product);
}
