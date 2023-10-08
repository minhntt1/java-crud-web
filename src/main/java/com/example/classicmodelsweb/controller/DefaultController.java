package com.example.classicmodelsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.classicmodelsweb.service.StatisticService;

@Controller
@RequestMapping
public class DefaultController {
	private StatisticService service;

	@Autowired
	public DefaultController(final StatisticService dao) {
		// TODO Auto-generated constructor stub
		this.service = dao;
	}

	@GetMapping("/login")
	public String showLogin(Authentication authentication,Model model) {
		model.addAttribute("authUser", authentication!=null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication!=null ? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee" : "Customer") : "");
		return "login";
	}

	@GetMapping
	public String showHome(Model model, Authentication authentication) {
		model.addAttribute("authUser", authentication!=null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication!=null ? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee" : "Customer") : "");
		model.addAttribute("stats", this.service.getStat());
		return "home";
	}
}
