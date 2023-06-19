package com.ecommerceapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerceapplication.model.Category;
import com.ecommerceapplication.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	// add category into database
	public void addcategory(Category category) {
		categoryRepository.save(category);
	}

	// get category from the database
	public List<Category> getallcategory() {

		List<Category> allcategories = categoryRepository.findAll();
		return allcategories;

	}

	// delete category by id
	public void deletecatbyid(int id) {
		categoryRepository.deleteById(id);
	}

	// get data by id
	public Category getbyid(int id) {
		Optional<Category> categorydata = categoryRepository.findById(id);
		Category category = categorydata.get();
		return category;
	}
}
