package com.nohit.jira_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ApplicationController {
	
	@GetMapping("/trangchu")
	public String index() {
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop() {
		return "shop";
	}
	
	@GetMapping("/category")
	public String category() {
		return "category";
	}
	
	@GetMapping("/single-product")
	public String singleProduct() {
		return "single-product";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("/checkout")
	public String checkout() {
		return "checkout";
	}
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
}
