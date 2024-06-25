package com.buyfurn.Buyfurn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.buyfurn.Buyfurn.model.User;
import com.buyfurn.Buyfurn.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if (user != null) {
			return org.springframework.security.core.userdetails.User.builder()
					.username(user.getEmail())
					.password(user.getPasword())
					.roles(user.getRoles().toArray(new String[0]))
					.build();
		}
		
		throw new UsernameNotFoundException(username);
	}

}
