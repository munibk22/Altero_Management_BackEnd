package com.munib.coding.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.munib.coding.daos.UserRepository;
import com.munib.coding.models.Users;

@Service	
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> user = Optional.ofNullable(userRepository.findByUsername(username)).orElse(null);
		if(!user.isPresent()) 
			throw new UsernameNotFoundException("User not found");
		Users userObj = user.get();
//		return new User(user.getUsername(),
//				user.getPassword(), new ArrayList<>());
		return User.builder()
				.username(userObj.getUsername())
				.password(userObj.getPassword())
				.roles("USER")
				.build();
	}

	private String getRoles(Users user) {
		if( user.getRole() == null)
			return  "USER";
		return "USER";
	}
	
	

}
