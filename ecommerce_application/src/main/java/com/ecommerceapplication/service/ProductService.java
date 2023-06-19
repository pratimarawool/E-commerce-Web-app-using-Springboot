package com.ecommerceapplication.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerceapplication.dto.Productdto;
import com.ecommerceapplication.model.Product;
import com.ecommerceapplication.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryService categoryService;

	// add product into database
	public void addproduct(Product product) {
		productRepository.save(product);
	}

	// get product from the database
	public List<Product> getallproduct() {

		List<Product> allproducts = productRepository.findAll();
		return allproducts;

	}

	// get product by id
	public Product getProductById(long id) {
		Optional<Product> productdata = productRepository.findById(id);
		Product product = productdata.get();
		System.err.println("Erro:" + product.getImageurl());
		return product;
	}

	public List<Product> getAllProductsByCategory(int id) {
		return productRepository.findAllByCategory_Id(id);
	}

	// delete product
	public void deleteproductbyid(long id) {
		productRepository.deleteById(id);
	}

	// product add function
	public Product addproduct(Productdto productDTO, Product product) {
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getbyid(productDTO.getCategoryId()));
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());

		return product;
	}

	// product update function
	public Productdto updateproduct(Productdto productDTO, Product product) {
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageurl(product.getImageurl());
		productDTO.setWeight(product.getWeight());

		return productDTO;
	}

	// upload image
	public void uploadImage(Product product, MultipartFile file) {

		try {
			if (file.isEmpty()) {
				// if the file is empty
				System.err.println("Image is empty");
				product.setImageurl("cake-default.png");

			} else {
				product.setImageurl(file.getOriginalFilename());
				File filesave = new ClassPathResource("/static/images/").getFile();

				Path path = Paths.get(filesave.getAbsolutePath() + File.separator + file.getOriginalFilename());
				System.err.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is uploaded");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// update image
	/*
	 * public void updateImage(Product product,MultipartFile file) { try { if
	 * (!file.isEmpty()) {
	 * 
	 * // get static path File filesave = new
	 * ClassPathResource("/static/images").getFile();
	 * 
	 *//**
		 * delete old photo from database only if the old photo is not the default photo
		 **//*
			 * if (!product.getImageurl().equals("cake-default.png")) {
			 * 
			 * File deleteFile = new File(filesave, product.getImageurl());
			 * deleteFile.delete(); // System.out.println(contact.getcId() +
			 * "ID Contact deleted successfully ");
			 * 
			 * }
			 * 
			 * // upload new photo and save path in database Path path =
			 * Paths.get(filesave.getAbsolutePath() + File.separator +
			 * file.getOriginalFilename()); Files.copy(file.getInputStream(), path,
			 * StandardCopyOption.REPLACE_EXISTING);
			 * product.setImageurl(file.getOriginalFilename());
			 * System.out.println("Image is uploaded");
			 * 
			 * } else { product.setImageurl(product.getImageurl()); // if not photo is being
			 * uploaded by the user restore old photo oin the database } } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
			 */

}
