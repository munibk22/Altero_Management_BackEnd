//package com.munib.coding.controller;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.munib.coding.jwt.JwtTokenUtil;
//import com.munib.coding.models.AuthRequest;
//import com.munib.coding.models.AuthResponse;
//import com.munib.coding.models.Users;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//	private final AuthenticationManager authenticationManager;
//	private final JwtTokenUtil jwtTokenUtil;
//	
//	public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
//		this.authenticationManager = authenticationManager;
//		this.jwtTokenUtil = jwtTokenUtil;
//	}
//	
//	
//   
//          
//    
//	
//	@PostMapping("signin")
//	public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
//		try {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));	
//		Users user = (Users) authentication.getPrincipal();
//		String token = jwtTokenUtil.generateAccessToken(user.getUsername());
//		
//		return ResponseEntity.ok()
//				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//				.body(new AuthResponse(token));
//		
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
//		}
//	}
//}
