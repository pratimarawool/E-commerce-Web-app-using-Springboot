package com.ecommerceapplication.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerceapplication.dto.Productdto;
import com.ecommerceapplication.helper.Message;
import com.ecommerceapplication.model.Category;
import com.ecommerceapplication.model.Product;
import com.ecommerceapplication.repository.CategoryRepository;
import com.ecommerceapplication.repository.ProductRepository;
import com.ecommerceapplication.service.CategoryService;
import com.ecommerceapplication.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	// Admin Home Page
	@GetMapping("")
	public String adminHome() {
		return "admin/adminHome";
	}

	/** ------------------------ Category ------------------------ **/
	// view categories present
	@GetMapping("/categories-manage")
	public String viewcategories(Model model) {

		List<Category> listofallcat = categoryService.getallcategory();
		model.addAttribute("category", listofallcat);
		return "admin/categories";
	}

	// add categories
	@GetMapping("/categories-manage/add")
	public String addcategories(Model model) {
		model.addAttribute("category", new Category());
		return "admin/categoriesAdd";
	}

	// process-add categories
	@PostMapping("/categories-manage/add-process")
	public String processaddcategories(@Valid @ModelAttribute("category") Category category, BindingResult result,
			HttpSession session) {
		try {
			if (!result.hasErrors()) {
				categoryService.addcategory(category);
				session.setAttribute("message", new Message("Category added successfully!!!", "success"));
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/categoriesAdd";
	}

	// delete category by Id
	@GetMapping("/delete-category/{id}")
	public String deletecategory(@PathVariable("id") int id) {
		categoryService.deletecatbyid(id);
		return "redirect:/admin/categories-manage";
	}

	// update category by Id
	@PostMapping("/update-category/{id}")
	public String updatecategory(@PathVariable("id") int id, Model model) {
		System.out.println(id);
		Category category = categoryService.getbyid(id);
		model.addAttribute("category", category);
		return "admin/updatecategory";
	}

	@PostMapping("/process-update")
	public String processupdate(@Valid @ModelAttribute("category") Category category, BindingResult result,
			HttpSession session) {
		try {
			if (!result.hasErrors()) {
				categoryRepository.save(category);
				session.setAttribute("message", new Message("Category updated successfully!!!", "success"));
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}

		return "admin/updatecategory";
	}

	/** ------------------------ Products ------------------------ **/

	// view products present
	@GetMapping("/products-manage")
	public String viewproducts(Model model) {
		//System.out.println("View's maii" + productService.getallproduct().get(0));
		List<Product> listofallprod = productService.getallproduct();
		listofallprod.forEach(System.out::println);
		model.addAttribute("products", listofallprod);
		return "admin/products";
	}

	// add products
	@GetMapping("/products-manage/add")
	public String addproducts(Model model) {
		model.addAttribute("productDTO", new Productdto());
		model.addAttribute("categories", categoryService.getallcategory());
		return "admin/productsAdd";
	}

	// process-add categories
	@PostMapping("/products-manage/add-process")
	public String processaddproducts(@ModelAttribute("productDTO") Productdto productDTO,
			@RequestParam("imagename") MultipartFile file, HttpSession session) {
		try {
			Product product = new Product();

			productService.addproduct(productDTO, product);

			productService.uploadImage(product, file);

			productService.addproduct(product);

			session.setAttribute("message", new Message("Product added successfully!!!", "success"));

		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/productsAdd";
	}

	// delete category by Id
	@GetMapping("/delete-product/{id}")
	public String deleteproduct(@PathVariable("id") long id) {
		Product product = productService.getProductById(id);
		try {
			File saveFile = new ClassPathResource("/static/images").getFile();
			if (!product.getImageurl().equals("cake-default.png")) {
				File deleteFile = new File(saveFile, product.getImageurl());
				deleteFile.delete();
				System.out.println("Product deleted successfully ");
			}
			productService.deleteproductbyid(id);
		} catch (Exception e) {
			System.out.println("Error!!");
		}

		return "redirect:/admin/products-manage";
	}

	// update category by Id

	@PostMapping("/update-product/{id}")
	public String updateproduct(@PathVariable("id") int id, Model model, HttpSession session, MultipartFile file) {

		Product product = productService.getProductById(id);

		Productdto productDTO = new Productdto();

		productService.updateproduct(productDTO, product);

		model.addAttribute("productDTO", productDTO);
		model.addAttribute("categories", categoryService.getallcategory());

		return "admin/updateproducts";
	}

	// process-add categories
	@PostMapping("/products-manage/updateproduct-process")
	public String processupdateproducts(@ModelAttribute("productDTO") Productdto productDTO,
			@RequestParam("imagename") MultipartFile file, HttpSession session) {
		try {
			Product product = new Product();

			productService.addproduct(productDTO, product);

			productService.uploadImage(product, file);

			productService.addproduct(product);

			session.setAttribute("message", new Message("Product updated successfully!!!", "success"));

		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}
		return "admin/productsAdd";
	}

}
