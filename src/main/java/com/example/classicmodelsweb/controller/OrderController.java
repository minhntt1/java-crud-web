package com.example.classicmodelsweb.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.classicmodelsweb.dto.OrderAdminForm;
import com.example.classicmodelsweb.model.Order;
import com.example.classicmodelsweb.model.OrderDetail;
import com.example.classicmodelsweb.model.OrderStatus;
import com.example.classicmodelsweb.model.Product;
import com.example.classicmodelsweb.service.OrderService;
import com.example.classicmodelsweb.utils.Pagination;

@Controller
@RequestMapping("/Order")
public class OrderController {
	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@PostMapping("/createOrder")
	public String createOrder(@ModelAttribute Order order, RedirectAttributes attributes,
			Authentication authentication) {
		this.orderService.createOrder(new Date(), order.getRequiredDate(), order.getShippedDate(),
				OrderStatus.InProcess, order.getComments(),
				this.orderService.getIdCusFromUserName(authentication.getName()));
		return "redirect:/Order/view";
	}

	@PostMapping("/addProductToPending/{prodCode}")
	public String doAddToPending(@ModelAttribute OrderDetail detail, @PathVariable String prodCode, Model model,
			Authentication authentication, RedirectAttributes attributes) {
		int countProd = this.orderService.countSearchProd(prodCode, "", "", "", "", "", null, null, null);

		if (countProd == 0)
			throw new EntityNotFoundException(
					String.format("can't find product/order %d,%s", detail.getOrder().getOrderNumber(), prodCode));

		orderService.createOrderDetail(detail.getOrder().getOrderNumber(), prodCode, detail.getQuantityOrdered());

		return "redirect:/Order/view";
	}

	@GetMapping("/viewDetail/{number}")
	public String viewOdDetail(@PathVariable Integer number, Model model, Authentication authentication) {
		int count = this.orderService.countSearchOrdersByOid(number);

		if (count == 0)
			throw new EntityNotFoundException("order not found");

		List<OrderDetail> details = this.orderService.getListOrderDetail(number);
		
		int diffProds = details.stream().collect(Collectors.groupingBy(x->x.getProduct().getProductCode())).size();
		double totalPrice = details.stream().collect(Collectors.summingDouble(x->x.getPriceEach()));
		
		model.addAttribute("diffProds", diffProds);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("odNumber", number);
		model.addAttribute("details", details);
		
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");

		return "order_view_detail";
	}

	@GetMapping("/addProductToPending/{prodCode}")
	public String addToPending(@PathVariable String prodCode, Model model, Authentication authentication) {
		Product product = orderService.getProdToAdd(prodCode);

		if (product == null)
			throw new EntityNotFoundException("product not found");

		List<Order> pendingOrders = this.orderService.searchOrdersByUser(null,authentication.getName(),
				OrderStatus.InProcess.name(), 0,
				this.orderService.countOrdersByUser(null,authentication.getName(), OrderStatus.InProcess.name()));

		model.addAttribute("orderDetailTmpl", new OrderDetail(null, product, 0, 0, (short) 0));
		model.addAttribute("orderTmpl", new Order());
		model.addAttribute("product", product);
		model.addAttribute("porders", pendingOrders);

		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");

		return "order_addprod_to_inprocess";
	}
	
	@PostMapping("/viewAdmin")
	public String doGetOrdersAdmin(@ModelAttribute OrderAdminForm adminForm, @RequestHeader String referer) {
		List<Order> list=adminForm.getArrayList();
		list.forEach(x->{
			this.orderService.updateOrderStatus(x.getOrderNumber(), x.getOrderStatus());
		});
		return "redirect:"+referer;
	}

	@GetMapping("/viewAdmin")
	public String getOrdersAdmin(
			@RequestParam(required = false) Integer orderNumber,
			@RequestParam(required = false) String user,
			@RequestParam(required = false) String status, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer resPerPage, Model model, Authentication authentication,
			Pagination pagination) {
		if (resPerPage == null)
			resPerPage = 10;
		if (page == null)
			page = 1;

		if (page <= 0)
			throw new NumberFormatException(String.format("page %d is not valid, must be >= 1", page));
		else if (resPerPage <= 0)
			throw new NumberFormatException(String.format("resPerPage %d is not valid, must be >= 1", page));

		int cntRes = this.orderService.countOrdersByUser(orderNumber, user, status);
		List<Order> list = this.orderService.searchOrdersByUser(orderNumber, user, status,
				(page - 1) * resPerPage, resPerPage);
		List<String> cust = this.orderService.getAllCust();
		
		OrderAdminForm adminForm=new OrderAdminForm();
		adminForm.setArrayList(list);
		
		pagination.setDefDispPages(5);
		pagination.setPage(page);
		pagination.setResPerPage(resPerPage);
		pagination.setTotalRes(cntRes);
		int[] pagination1 = pagination.get();

		model.addAttribute("currUser",user);
		model.addAttribute("currStatus",status);
		model.addAttribute("currOrderNumber",orderNumber);
		model.addAttribute("resPerPage", resPerPage);
		model.addAttribute("customers", cust);
		model.addAttribute("totalRecords", cntRes);
		model.addAttribute("pagination", pagination1);
		model.addAttribute("orderForm", adminForm);
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "order_view_admin";
	}
	
	@GetMapping("/view")
	public String getOrders(@RequestParam(required = false) String status, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer resPerPage, Model model, Authentication authentication,
			Pagination pagination) {

		if (resPerPage == null)
			resPerPage = 10;
		if (page == null)
			page = 1;

		if (page <= 0)
			throw new NumberFormatException(String.format("page %d is not valid, must be >= 1", page));
		else if (resPerPage <= 0)
			throw new NumberFormatException(String.format("resPerPage %d is not valid, must be >= 1", page));

		
		List<Order> list = this.orderService.searchOrdersByUser(null,authentication.getName(), status,
				(page - 1) * resPerPage, resPerPage);
		int cntRes = this.orderService.countOrdersByUser(null,authentication.getName(), status);
		
		pagination.setDefDispPages(5);
		pagination.setPage(page);
		pagination.setResPerPage(resPerPage);
		pagination.setTotalRes(cntRes);

		int[] pagination1 = pagination.get();

		model.addAttribute("status", status);
		model.addAttribute("resPerPage", resPerPage);

		model.addAttribute("totalRecords", cntRes);
		model.addAttribute("pagination", pagination1);
		model.addAttribute("orders", list);
		model.addAttribute("authUser", authentication != null ? authentication.getName() : "");
		model.addAttribute("authRoles", authentication != null
				? ((authentication.getAuthorities().toArray()[0].toString()).equals("ROLE_EMPLOYEE") ? "Employee"
						: "Customer")
				: "");
		return "order_view";
	}
}
