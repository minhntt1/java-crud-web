package com.example.classicmodelsweb.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.classicmodelsweb.dao.ProductDAO;
import com.example.classicmodelsweb.dao.ProductLineDAO;
import com.example.classicmodelsweb.model.Product;
import com.example.classicmodelsweb.model.ProductLine;

@Service("productSvc")
public class ProductServiceImpl implements ProductService {
	private ProductDAO dao;
	private ProductLineDAO lineDAO;
	
	@Override
	public Product getProductRefById(String id) {
		// TODO Auto-generated method stub
		return this.dao.getProductRefById(id);
	}

	@Override
	public ProductLine getProdLineRefById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	public ProductServiceImpl(@Qualifier("productDAO") ProductDAO dao, @Qualifier("productLineDAO") ProductLineDAO lineDAO) {
		// TODO Auto-generated constructor stub
		this.dao = dao;
		this.lineDAO = lineDAO;
	}
	
	public boolean checkHasProdId(String id) {
		// TODO Auto-generated method stub
		return this.dao.checkHasProdId(id);
	}

	@Transactional
	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		this.dao.deleteProduct(id);
	}

	@Transactional
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return this.dao.updateProduct(product);
	}

	public boolean hasProdLineId(String id) {
		// TODO Auto-generated method stub
		return this.lineDAO.hasProdLineId(id);
	}

	public Product generateProdWithCode(Product product) {
		StringBuilder code = new StringBuilder(product.getProductName().replaceAll("[^a-zA-Z0-9]+", "").toUpperCase());
		int countCode = this.countSearchProd(code.toString(), null, null, null, null, null, null, null, null);
		
		if(countCode>0)
			product.setProductCode(code.append(String.valueOf(countCode)).toString());
		else
			product.setProductCode(code.toString());
		// TODO Auto-generated method stub
		return product;
	}

	public Product getProdDetail(String id) {
		// TODO Auto-generated method stub
		return this.dao.getProdDetail(id);
	}

	public int countSearchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP) {
		// TODO Auto-generated method stub
		return this.dao.countSearchProd(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP);
	}

	public List<Product> searchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP,
			Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return this.dao.searchProd(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP, offset, limit);
	}

	public List<ProductLine> getAllLines() {
		// TODO Auto-generated method stub
		return this.lineDAO.getAllLines();
	}

	public ProductLine getByLineId(String id) {
		// TODO Auto-generated method stub
		return this.lineDAO.getByLineId(id);
	}

	@Transactional
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		this.dao.addProduct(product);
	}
}
