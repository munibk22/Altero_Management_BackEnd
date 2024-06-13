//package com.munib.coding.jwt;
//
//import java.io.IOException;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
////Filter to validate Jwt tokens
//
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
////implements Filter {
//	private final JwtTokenUtil jwtTokenUtil;
//	private final UserDetailsService userDetailsService;
//	
//	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
//		this.jwtTokenUtil = jwtTokenUtil;
//		this.userDetailsService = userDetailsService;
//	}
//	
//	
//	@Override
//	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//		String header = request.getHeader("Authorization");
//		if(header == null || !header.startsWith("Bearer ")) {
//			filterChain.doFilter(request,response);
//			return;
//		}
//		
//		String token = header.substring(7);
//		if(!jwtTokenUtil.validate(token)) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		
//		String username = jwtTokenUtil.getUsername(token);
//		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//	
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		filterChain.doFilter(request,response);
//	}								  
//
//
//
////	@Override
////	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////			throws ServletException, IOException {
////		// TODO Auto-generated method stub
////		
////	}
//
//}
