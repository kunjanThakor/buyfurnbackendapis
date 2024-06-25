package com.buyfurn.Buyfurn.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyfurn.Buyfurn.model.User;
import com.buyfurn.Buyfurn.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@GetMapping("/_updateuserrole/{id}/{role}")
	public User updateUserRole(@PathVariable Long id, @PathVariable List<String> role) {
		return userService.updateUserRole(id, role);
	}

	@GetMapping("/login")
	public User getLogin(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			return userService.getUser(username);
		} else {
			return null;
		}
	}
	
	@GetMapping("/getall")
	public List<User> getAll(){
		return userService.getAll();
	}
}
