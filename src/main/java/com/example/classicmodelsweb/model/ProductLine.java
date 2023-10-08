package com.example.classicmodelsweb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "productlines")
public class ProductLine {
	@Id
	@Column(name="productLine")
	private String productLine;
	
	@Column(name="textDescription")
	private String textDescription;
	
	@Column(name="htmlDescription")
	private String htmlDescription;
	
	@Column(name="image")
	private String image;
	
	@OneToMany(cascade = CascadeType.DETACH)
	private List<Product> products;
	
	public ProductLine() {
		// TODO Auto-generated constructor stub
	}

	public ProductLine(String productLine, String textDescription, String htmlDescription, String image) {
		super();
		this.productLine = productLine;
		this.textDescription = textDescription;
		this.htmlDescription = htmlDescription;
		this.image = image;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getTextDescription() {
		return textDescription;
	}

	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

	public String getHtmlDescription() {
		return htmlDescription;
	}

	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return String.format(
				"ProductLine [productLine=%s, textDescription=%s, htmlDescription=%s, image=%s]",
				productLine, textDescription, htmlDescription, image);
	}
}
