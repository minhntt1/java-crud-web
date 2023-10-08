package com.example.classicmodelsweb.controller;

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

import com.example.classicmodelsweb.model.Customer;
import com.example.classicmodelsweb.service.UserService;

@Controller
@RequestMapping("/Customer")
public class CustomerController {
	private UserService service;

	@Autowired
	public CustomerController(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping("/updateProfile")
	public String doUpdateMyProfile(@ModelAttribute Customer customer, Model model, Authentication authentication,
			RedirectAttributes attributes) {
		if (!service.checkCustomerExist(customer.getCustomerNumber())
				|| !service.checkEmpExists(customer.getEmployee().getEmployeeNumber()))
			throw new EntityNotFoundException(String.format("Customer id %d not found/Employee id %d not found",
					customer.getCustomerNumber(), customer.getEmployee().getEmployeeNumber()));

		customer = this.service.updateCustomer(customer);

		attributes.addAttribute("success", "");
		return "redirect:/Customer/updateProfile";
	}

	@GetMapping("/updateProfile")
	public String updateMyProfile(Model model, Authentication authentication,
			@RequestParam(required = false) String success) {
		model.addAttribute("listEmps", this.service.getAllEmp());
		model.addAttribute("customer",
				this.service.findCustomerById(this.service.getIdCusFromUserName(authentication.getName())));
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "customer_updateprofile";
	}

	@GetMapping("/viewProfile")
	public String showMyProfile(Model model, Authentication authentication) {
		model.addAttribute("custUsername", authentication.getName());
		model.addAttribute("activeStatus",
				this.service.getActiveStatusFromUserName(authentication.getName()) ? "Active" : "Disabled");
		model.addAttribute("customer",
				this.service.findCustomerById(this.service.getIdCusFromUserName(authentication.getName())));
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "customer_viewprofile";
	}

	@GetMapping("/viewProfile/{userName}")
	public String showProfile(@PathVariable String userName, Model model, Authentication authentication) {
		model.addAttribute("custUsername", userName);
		model.addAttribute("activeStatus", this.service.getActiveStatusFromUserName(userName) ? "Active" : "Disabled");
		model.addAttribute("customer", this.service.findCustomerById(this.service.getIdCusFromUserName(userName)));
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "customer_viewprofile";
	}
}
