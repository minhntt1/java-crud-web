package com.example.classicmodelsweb.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.classicmodelsweb.model.Customer;
import com.example.classicmodelsweb.model.Payment;
import com.example.classicmodelsweb.service.PaymentService;
import com.example.classicmodelsweb.utils.Pagination;

@Controller
@RequestMapping("/Payment")
public class PaymentController {
	private PaymentService paymentService;
	
	@Autowired
	public PaymentController(PaymentService paymentService) {
		// TODO Auto-generated constructor stub
		this.paymentService=paymentService;
	}
	
	@PostMapping("/viewByCustomer")
	public String custMakePayment(@ModelAttribute Payment payment, RedirectAttributes attributes, Authentication authentication) {
		this.paymentService.persistPayment(authentication.getName(), payment.getCheckNumber(), new Date(), payment.getAmount());
		attributes.addAttribute("created", payment.getCheckNumber());
		return "redirect:/Payment/viewByCustomer";
	}
	
	@GetMapping("/view")
	public String viewPayments(
			@RequestParam(required = false) String checkNum, 
			@RequestParam(required = false) String minDate, 
			@RequestParam(required = false) String maxDate,
			@RequestParam(required = false) Double minAmount,
			@RequestParam(required = false) Double maxAmount,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer resPerPage,
			Model model, 
			Authentication authentication,
			Pagination pagination) {
		
		if(resPerPage==null)
			resPerPage=10;
		if(page==null)
			page=1;
		
		if (page <= 0)
			throw new NumberFormatException(String.format("page %d is not valid, must be >= 1", page));
		else if (resPerPage <= 0)
			throw new NumberFormatException(String.format("resPerPage %d is not valid, must be >= 1", page));

		List<Payment> payments=this.paymentService.searchPayments(authentication.getName(), checkNum, minDate, maxDate, minAmount, maxAmount, resPerPage, resPerPage*(page-1));
		
		pagination.setDefDispPages(5);
		pagination.setPage(page);
		pagination.setResPerPage(resPerPage);
		pagination.setTotalRes(payments.size());
		
		int[]pagination1=pagination.get();
		
		model.addAttribute("page", page);
		model.addAttribute("resPerPage", resPerPage);
		model.addAttribute("checkNum",checkNum);
		model.addAttribute("minDate",minDate);
		model.addAttribute("maxDate",maxDate);
		
		model.addAttribute("minAmount",minAmount);
		model.addAttribute("maxAmount",maxAmount);
		
		model.addAttribute("payments",payments);
		model.addAttribute("pagination",pagination1);
		model.addAttribute("totalRecords",payments.size());
		
		model.addAttribute("paymentTmpl", new Payment(new Customer(this.paymentService.getIdCusFromUserName(authentication.getName())), null, new Date(), 0));
		
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "payment_view_create";
	}
	
	@GetMapping("/viewByCustomer")
	public String viewPaymentsByCustomer(
			@RequestParam(required = false) String checkNum, 
			@RequestParam(required = false) String minDate, 
			@RequestParam(required = false) String maxDate,
			@RequestParam(required = false) Double minAmount,
			@RequestParam(required = false) Double maxAmount,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer resPerPage,
			Model model, 
			Authentication authentication,
			Pagination pagination) {
		
		if(resPerPage==null)
			resPerPage=10;
		if(page==null)
			page=1;
		
		if (page <= 0)
			throw new NumberFormatException(String.format("page %d is not valid, must be >= 1", page));
		else if (resPerPage <= 0)
			throw new NumberFormatException(String.format("resPerPage %d is not valid, must be >= 1", page));

		List<Payment> payments=this.paymentService.searchPayments(authentication.getName(), checkNum, minDate, maxDate, minAmount, maxAmount, resPerPage, resPerPage*(page-1));
		
		pagination.setDefDispPages(5);
		pagination.setPage(page);
		pagination.setResPerPage(resPerPage);
		pagination.setTotalRes(payments.size());
		
		int[]pagination1=pagination.get();
		
		model.addAttribute("page", page);
		model.addAttribute("resPerPage", resPerPage);
		model.addAttribute("checkNum",checkNum);
		model.addAttribute("minDate",minDate);
		model.addAttribute("maxDate",maxDate);
		
		model.addAttribute("minAmount",minAmount);
		model.addAttribute("maxAmount",maxAmount);
		
		model.addAttribute("payments",payments);
		model.addAttribute("pagination",pagination1);
		model.addAttribute("totalRecords",payments.size());
		
		model.addAttribute("paymentTmpl", new Payment(new Customer(this.paymentService.getIdCusFromUserName(authentication.getName())), null, new Date(), 0));
		
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "payment_view_create";
	}
}
