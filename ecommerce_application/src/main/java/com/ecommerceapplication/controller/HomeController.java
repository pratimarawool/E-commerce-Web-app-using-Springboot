package com.ecommerceapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerceapplication.global.GlobalData;
import com.ecommerceapplication.repository.ProductRepository;
import com.ecommerceapplication.service.CategoryService;
import com.ecommerceapplication.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	// view home page
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "base";
	}

	@GetMapping("/")
	public String default1(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}

	// view shopping page
	@GetMapping("/shop")
	public String viewshop(Model model) {
		model.addAttribute("categories", categoryService.getallcategory());
		model.addAttribute("products", productService.getallproduct());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}

	// view and shop products by category
	@GetMapping("/shop/category/{id}")
	public String shopbycategory(@PathVariable("id") int id, Model model) {
		model.addAttribute("categories", categoryService.getallcategory());
		model.addAttribute("products", productService.getAllProductsByCategory(id));
		return "shop";
	}

	@GetMapping("/shop/viewproduct/{id}")
	public String viewproduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "viewProduct";
	}
}
