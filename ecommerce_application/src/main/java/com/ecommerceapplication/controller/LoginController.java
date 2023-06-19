package com.ecommerceapplication.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecommerceapplication.global.GlobalData;
import com.ecommerceapplication.helper.Message;
import com.ecommerceapplication.model.Role;
import com.ecommerceapplication.model.User;
import com.ecommerceapplication.repository.RoleRepository;
import com.ecommerceapplication.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}

	@GetMapping("/register")
	public String registerget(Model model) {
		model.addAttribute("title", "Registration Page");
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register-process")
	public String processregisteration(@Valid @ModelAttribute("user") User user, BindingResult result,
			HttpSession session, Model model) {

		try {
			System.err.println("Main Yahhh");
			if (result.hasErrors()) {
				System.out.println("Error : " + result);
				return "register";
			}

			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			List<Role> roles = new ArrayList<>();
			roles.add(roleRepository.findById(2).get());
			user.setRoles(roles);

			System.out.println("Before Register User : " + user);
			User saveResult = userRepository.save(user);
			System.out.println("After Registered User : " + saveResult);

			// after successfully registered, form data must return empty
			User emptyUser = new User();
			model.addAttribute("user", emptyUser);
			session.setAttribute("message", new Message("Registration successfully", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("ohhh..!" + e.getMessage(), "alert-danger"));
			return "register";
		}
		return "register";
	}

}
