package com.buyfurn.Buyfurn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buyfurn.Buyfurn.model.User;
import com.buyfurn.Buyfurn.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;

	public User createUser(User user) {
		user.setPasword(encoder.encode(user.getPasword()));
		user.setRoles(List.of("USER"));
		userRepository.save(user);
		return user;
	}

	public User updateUserRole(Long id, List<String> role) {
		User user = userRepository.findById(id).get();
		user.setRoles(role);
//		user.setPasword(encoder.encode(user.getPasword()));
		userRepository.save(user);
		return user;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User getUser(String username) {
		return userRepository.findByEmail(username);
	}
	
	
}
