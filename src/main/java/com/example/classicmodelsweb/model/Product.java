package com.example.classicmodelsweb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@Column(name = "productCode")
	private String productCode;
	
	@Column(name = "productName")
	private String productName;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "productLine")
	private ProductLine line;
	
	@Column(name = "productScale")
	private String productScale;
	
	@Column(name = "productVendor")
	private String productVendor;
	
	@Column(name = "productDescription")
	private String productDescription;
	
	@Column(name = "quantityInStock")
	private int quantityInStock;
	
	@Column(name = "buyPrice")
	private double buyPrice;
	
	@Column(name = "MSRP")
	private double MSRP;
	
	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "product")
	private List<OrderDetail> details;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String productCode, String productName, ProductLine line, String productScale, String productVendor,
			String productDescription, int quantityInStock, double buyPrice, double mSRP,
			List<OrderDetail> details) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.line = line;
		this.productScale = productScale;
		this.productVendor = productVendor;
		this.productDescription = productDescription;
		this.quantityInStock = quantityInStock;
		this.buyPrice = buyPrice;
		this.MSRP = mSRP;
		this.details = details;
	}

	public ProductLine getLine() {
		return line;
	}

	public void setLine(ProductLine line) {
		this.line = line;
	}

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductScale() {
		return productScale;
	}

	public void setProductScale(String productScale) {
		this.productScale = productScale;
	}

	public String getProductVendor() {
		return productVendor;
	}

	public void setProductVendor(String productVendor) {
		this.productVendor = productVendor;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public double getMSRP() {
		return this.MSRP;
	}

	public void setMSRP(double mSRP) {
		this.MSRP = mSRP;
	}

	@Override
	public String toString() {
		return String.format(
				"Product [productCode=%s, productName=%s, productScale=%s, productVendor=%s, productDescription=%s, quantityInStock=%s, buyPrice=%s, MSRP=%s]",
				productCode, productName, productScale, productVendor, productDescription, quantityInStock, buyPrice,
				MSRP);
	}
}
