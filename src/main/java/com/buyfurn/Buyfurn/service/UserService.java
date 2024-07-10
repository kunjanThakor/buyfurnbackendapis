package com.buyfurn.Buyfurn.service;

import java.io.IOException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.buyfurn.Buyfurn.model.User;
import com.buyfurn.Buyfurn.model.UserImage;
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
	
	private final Map<String, String> otpStorage = new HashMap<>();
	private final SecureRandom random = new SecureRandom();

	public String generateOtp(String email) {
		String otp = String.valueOf(100000 + random.nextInt(900000)); // Generate 6-digit OTP
		otpStorage.put(email, otp);
		System.out.println(otpStorage);
		return otp;
	}

	public boolean validateOtp(String email, String otp) {
		String storedOtp = otpStorage.get(email);
		if (storedOtp != null && storedOtp.equals(otp)) {
			otpStorage.remove(email); 
			return true;
		}
		return false;
	}

	public void clearOtp(String email) {
		otpStorage.remove(email);
	}

	public boolean verifyEmail(String email) {
		User user=  userRepository.findByEmail(email);
		
		if(user!=null) {
			return true;
		}
		else
		{
			return false;
		}

	}

	public String deleteUser(Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		userRepository.delete(user);
		return "User Deleted";
	}

	 public User updateUser(User user, MultipartFile image) throws IOException {
	        User existingUser = userRepository.findById(user.getId())
	                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

	        existingUser.setName(user.getName());
	        existingUser.setEmail(user.getEmail());
	        existingUser.setPasword(user.getPasword());
	        existingUser.setRoles(user.getRoles());
	        existingUser.setAddress(user.getAddress());
	        existingUser.setContactNumber(user.getContactNumber());

	        if (image != null && !image.isEmpty()) {
	            UserImage userImage = uploadImage(image);
	            existingUser.setUserImage(userImage);
	        }

	        userRepository.save(existingUser);
	        return existingUser;
	    }

	
	private UserImage uploadImage(MultipartFile image) throws IOException {
		UserImage userImage=new UserImage(image.getOriginalFilename(), image.getContentType(), image.getBytes());
		return userImage;
	}
}
