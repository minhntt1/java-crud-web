package com.example.classicmodelsweb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.classicmodelsweb.model.Product;
import com.example.classicmodelsweb.model.ProductLine;

@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO {
	private EntityManager entityManager;

	@Autowired
	public ProductDAOImpl(final EntityManager entityManager) {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}
	
	@Override
	public Product getProductRefById(String id) {
		// TODO Auto-generated method stub
		return this.entityManager.getReference(Product.class,id);
	}

	@Override
	public boolean checkHasProdId(String id) {
		// TODO Auto-generated method stub
		return ((Long)this.entityManager
				.createQuery("select COUNT(p.productCode) from Product p where p.productCode=:id")
				.setParameter("id", id)
				.getSingleResult()
				)>0;//DUNG QUERY TRONG ENTITYMANAGER VOI COUNT THI TRA VE KIEU LONG
	}

	@Override
	public void deleteProduct(String string) {
		// TODO Auto-generated method stub
		Product product = this.entityManager.getReference(Product.class, string);
		this.entityManager.remove(product);
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		Product product2 = this.entityManager.getReference(Product.class, product.getProductCode());
		ProductLine productLineRef = this.entityManager.getReference(ProductLine.class, product.getLine().getProductLine());
		
		product2.setBuyPrice(product.getBuyPrice());
		product2.setDetails(product.getDetails());
		product2.setLine(productLineRef);
		product2.setMSRP(product.getMSRP());
		product2.setProductDescription(product.getProductDescription());
		product2.setProductName(product.getProductName());
		product2.setProductScale(product.getProductScale());
		product2.setProductVendor(product.getProductVendor());
		product2.setQuantityInStock(product.getQuantityInStock());
		
		return product2;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		this.entityManager.persist(product);
	}

	@Override
	public List<Product> searchProd(
			String productCode,
			String productName,
			String productLine,
			String productScale,
			String productVendor,
			String productDescription,
			Integer quantityInStock,
			Double buyPrice,
			Double MSRP,
			Integer	offset,
			Integer limit) {
		// TODO Auto-generated method stub
		
		return this.entityManager
				.createQuery("select p from Product p join fetch p.line l where "
						+ "(:code is null or :code='' or p.productCode like CONCAT(:code,'%')) "
						+ "and (:name is null or :name='' or p.productName like CONCAT('%',:name,'%')) "
						+ "and (:line is null or :line='' or l.productLine = :line) "
						+ "and (:scale is null or :scale='' or p.productScale like CONCAT('%',:scale,'%')) "
						+ "and (:vendor is null or :vendor='' or p.productVendor like CONCAT('%',:vendor,'%')) "
						+ "and (:descript is null or :descript='' or p.productDescription like CONCAT('%',:descript,'%')) "
						+ "and (:quantity is null or :quantity='' or p.quantityInStock=:quantity) "
						+ "and (:price is null or :price='' or p.buyPrice=:price) "
						+ "and (:msrp is null or :msrp='' or p.MSRP=:msrp)", Product.class)
				.setParameter("code", productCode)
				.setParameter("name", productName)
				.setParameter("line", productLine)
				.setParameter("scale", productScale)
				.setParameter("vendor", productVendor)
				.setParameter("descript", productDescription)
				.setParameter("quantity", quantityInStock)
				.setParameter("price", buyPrice)
				.setParameter("msrp", MSRP)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	public int countSearchProd(String productCode, String productName, String productLine, String productScale,
			String productVendor, String productDescription, Integer quantityInStock, Double buyPrice, Double MSRP) {
		Long obj = (Long) this.entityManager
				.createQuery(
						"select COUNT(p.productCode) from Product p join p.line l where "
						+ "(:code is null or :code='' or p.productCode like CONCAT(:code,'%')) "
						+ "and (:name is null or :name='' or p.productName like CONCAT('%',:name,'%')) "
						+ "and (:line is null or :line='' or l.productLine = :line) "
						+ "and (:scale is null or :scale='' or p.productScale like CONCAT('%',:scale,'%')) "
						+ "and (:vendor is null or :vendor='' or p.productVendor like CONCAT('%',:vendor,'%')) "
						+ "and (:descript is null or :descript='' or p.productDescription like CONCAT('%',:descript,'%')) "
						+ "and (:quantity is null or :quantity='' or p.quantityInStock=:quantity) "
						+ "and (:price is null or :price='' or p.buyPrice=:price) "
						+ "and (:msrp is null or :msrp='' or p.MSRP=:msrp)")
				.setParameter("code", productCode)
				.setParameter("name", productName)
				.setParameter("line", productLine)
				.setParameter("scale", productScale)
				.setParameter("vendor", productVendor)
				.setParameter("descript", productDescription)
				.setParameter("quantity", quantityInStock)
				.setParameter("price", buyPrice)
				.setParameter("msrp", MSRP)
				.getSingleResult();//NOTE VOI TRUONG HOP COUNT THI KO CAN JOIN FETCH NHU GET KIEU O TREN KIA (p join fetch p.line)

		return obj.intValue();
	}

	@Override
	public Product getProdDetail(String id) {
		// TODO Auto-generated method stub
		return this.entityManager
				.createQuery("select p from Product p "
						+ "join fetch p.line "
						+ "where p.id=:id", Product.class)
				.setParameter("id", id)
				.getSingleResult();
	}
}
