package com.buyfurn.Buyfurn.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (userService.verifyEmail(email)) {
        
            return new ResponseEntity<String>(HttpStatus.FOUND);
        }
        userService.generateOtp(email);
        
        return new ResponseEntity<String>(userService.generateOtp(email),HttpStatus.OK);
    }
    
    @PostMapping("/verify-otp")
    public boolean verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        return userService.validateOtp(email, otp);
    }
}
