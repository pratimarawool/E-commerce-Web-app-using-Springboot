package com.ecommerceapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerceapplication.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
