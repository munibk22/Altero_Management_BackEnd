package com.munib.coding.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.munib.coding.daos.UserRepository;
import com.munib.coding.jwt.JwtUtil;
import com.munib.coding.models.AuthRequest;
import com.munib.coding.models.AuthResponse;
import com.munib.coding.models.Users;
import com.munib.coding.services.UserDetailsServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired 
	UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@PostMapping("/api/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
					authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password",e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthResponse(jwt));
		
	}
	
	@PostMapping("/user/register")
	public ResponseEntity<?> register(@RequestBody Users user) {
		//First check if user alrdy exists
		Optional<Users> existingUser = userRepository.findByUsername(user.getUsername());
		System.out.println(existingUser);
		if(existingUser.isPresent())
			return ResponseEntity.badRequest().body("User already exists");
		
		if(user.getRole()==null)
			user.setRole("USER");		
		//This uses PasswordEncoder to encode or hash the plain-text password.  
		//Bcrypt hashing algorithms to securely encode the password.
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//Save to db
		userRepository.save(user);
		
		return ResponseEntity.ok("User registered successfully");
	}
	
	@GetMapping("/api/public/hello")
	public String hello() {
		String msg = "Hello From Altero Management Controller";
		System.out.println(msg);
		return msg;
	}
	
	@GetMapping("/user/home")
	public String homePage() {
		String msg = "Home Page For Altero Management Controller";
		System.out.println(msg);
		return msg;
	}
}
