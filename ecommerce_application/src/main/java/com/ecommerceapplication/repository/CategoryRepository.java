package com.ecommerceapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerceapplication.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
