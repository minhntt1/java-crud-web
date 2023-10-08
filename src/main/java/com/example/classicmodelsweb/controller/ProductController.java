package com.example.classicmodelsweb.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.classicmodelsweb.model.Product;
import com.example.classicmodelsweb.model.ProductLine;
import com.example.classicmodelsweb.service.ProductService;
import com.example.classicmodelsweb.utils.Pagination;

@Controller
@RequestMapping("/Product")
public class ProductController {
	private static Product productSample = new Product();
	private final ProductService dao;

	@Autowired
	public ProductController(ProductService dao) {
		super();
		this.dao = dao;
	}

	@GetMapping("/view/{id}")
	public String getProdDetail(Authentication authentication, Model model, @PathVariable(name = "id") String id) {
//		this.dao.
		model.addAttribute("product", this.dao.getProdDetail(id));
		model.addAttribute("authUser", authentication!=null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication!=null ? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee" : "Customer") : "");
		return "product_view_detail";
	}

	@GetMapping("/view")
	public String getProductList(
			Authentication authentication,
			@RequestParam(required = false, name = "productCode") String productCode,
			@RequestParam(required = false, name = "productName") String productName,
			@RequestParam(required = false, name = "productLine") String productLine,
			@RequestParam(required = false, name = "productScale") String productScale,
			@RequestParam(required = false, name = "productVendor") String productVendor,
			@RequestParam(required = false, name = "productDescription") String productDescription,
			@RequestParam(required = false, name = "quantityInStock") Integer quantityInStock,
			@RequestParam(required = false, name = "buyPrice") Double buyPrice,
			@RequestParam(required = false, name = "MSRP") Double MSRP,
			@RequestParam(required = false, name = "resPerPage") Integer resPerPage,
			@RequestParam(required = false, name = "page") Integer page, 
			@RequestParam(required = false, name = "deleted") String deleted, Model model, Pagination pagination) {

		if (resPerPage == null)
			resPerPage = 20;
		if (page == null)
			page = 1;

		if (page <= 0)
			throw new NumberFormatException(String.format("page in /Product/view %d is not valid, must be >= 1", page));
		else if (resPerPage <= 0)
			throw new NumberFormatException(String.format("resPerPage in /Product/view %d is not valid, must be >= 1", page));

		int totalRes = this.dao.countSearchProd(productCode, productName, productLine, productScale, productVendor,
				productDescription, quantityInStock, buyPrice, MSRP);// tong so ban ghi trong csdl

		pagination.setDefDispPages(5);
		pagination.setPage(page);
		pagination.setResPerPage(resPerPage);
		pagination.setTotalRes(totalRes);
		int[] pagination1 = pagination.get();

		List<ProductLine> lines = this.dao.getAllLines();

		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("productLine", productLine);
		model.addAttribute("productLineList", lines);
		model.addAttribute("productScale", productScale);
		model.addAttribute("productVendor", productVendor);
		model.addAttribute("productDescription", productDescription);
		model.addAttribute("quantityInStock", quantityInStock);
		model.addAttribute("buyPrice", buyPrice);
		model.addAttribute("MSRP", MSRP);
		model.addAttribute("currPage", page);
		model.addAttribute("resPerPage", resPerPage);

		model.addAttribute("totalRecords", totalRes);
		model.addAttribute("pagination", pagination1);
		model.addAttribute("prods",this.dao.searchProd(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP, (page - 1) * resPerPage, resPerPage));
		model.addAttribute("authUser", authentication!=null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication!=null ? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee" : "Customer") : "");
		return "product_view";
	}

	@PostMapping("/add")
	public String doAddProd(Authentication authentication, @ModelAttribute Product product, Model model, RedirectAttributes redirectAttributes) {
		product = this.dao.generateProdWithCode(product);

		if (product.getLine() == null || !this.dao.hasProdLineId(product.getLine().getProductLine()))
			throw new EntityNotFoundException("Productline id "+product.getLine().getProductLine()+" is not found/empty");

		this.dao.addProduct(product);

		redirectAttributes.addAttribute("added",product.getProductCode());
		return "redirect:/Product/add";
	}

	@GetMapping("/add")
	public String showAddProd(Authentication authentication, @RequestParam(required = false) String added, Model model) {
		model.addAttribute("productLineList", dao.getAllLines());
		model.addAttribute("product", ProductController.productSample);
		model.addAttribute("act", "add");
		model.addAttribute("authUser", authentication!=null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication!=null ? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee" : "Customer") : "");
		return "product_add_update";
	}
	
	@GetMapping("/update/{id}")
	public String updateProd(Authentication authentication, @PathVariable String id, @RequestParam(required = false) String updated, Model model) {
		if(id==null)
			throw new RuntimeException("id must not be null");

		Product product = this.dao.getProdDetail(id);
		
		if(product==null)
			throw new EntityNotFoundException("Product/ProductLine ID "+id+" not found");
		
		model.addAttribute("productLineList", dao.getAllLines());
		model.addAttribute("product", product);
		model.addAttribute("act", "update");
		model.addAttribute("authUser", authentication!=null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication!=null ? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee" : "Customer") : "");
		return "product_add_update";
	}
	
	@PostMapping("/update/{id}")
	public String doUpdateProd(Authentication authentication, @PathVariable String id, @ModelAttribute Product product, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if(id==null)
			throw new RuntimeException("id must not be null");
		
		if(!this.dao.checkHasProdId(id) || product.getLine()==null || !this.dao.hasProdLineId(product.getLine().getProductLine()))
			throw new EntityNotFoundException("Product/ProductLine ID "+id+" not found");
		
		product.setProductCode(id);
		product=this.dao.updateProduct(product);
		
		redirectAttributes.addAttribute("updated", "");
		
		return "redirect:/Product/update/"+id;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProd(@PathVariable String id, Model model, RedirectAttributes attributes) throws Exception {
		if(!this.dao.checkHasProdId(id))
			throw new EntityNotFoundException("Product/ProductLine ID "+id+" not found");
		
		this.dao.deleteProduct(id);
		attributes.addAttribute("deleted", "");
		return "redirect:/Product/view";
	}
}
