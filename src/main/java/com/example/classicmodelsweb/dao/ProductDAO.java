package com.example.classicmodelsweb.dao;

import java.util.List;

import com.example.classicmodelsweb.model.Product;

public interface ProductDAO {
	Product getProdDetail(String id);

	int countSearchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP);
	
	List<Product> searchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP, Integer offset, Integer limit);

	boolean checkHasProdId(String id);

	Product updateProduct(Product product);
	
	void deleteProduct(String id);
	
	void addProduct(Product product);
	
	Product getProductRefById(String id);
}
