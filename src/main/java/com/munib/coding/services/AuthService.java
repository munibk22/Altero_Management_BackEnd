package com.munib.coding.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.munib.coding.daos.UserRepository;
import com.munib.coding.models.Users;

@Service
public class AuthService {
	
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Users register(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
