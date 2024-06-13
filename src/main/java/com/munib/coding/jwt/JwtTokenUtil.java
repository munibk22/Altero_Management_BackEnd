//package com.munib.coding.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
////utility class for generating and validating JWT tokens:
//@Service
//public class JwtTokenUtil {
//
//	private final String SECRET_KEY = "secret";
//	private final String jwtSecret = "jwt_secret";
//	private final String jwtIssuer = "Altero_Management";
//	
//
//    @Value("${jwt.secret}")
//    private String jwtSecret2;
//	
//	
//	public String generateAccessToken(String username) {
//		return Jwts.builder()
//				.setSubject(username)
//				.setIssuer(jwtIssuer)
//				.setIssuedAt(new Date())
//				.setExpiration(new Date(System.currentTimeMillis() + 604800000)) //1 Week exp
//				.signWith(SignatureAlgorithm.HS512, jwtSecret)
//				.compact();
//	}
//	
//	public boolean validate(String token) {
//		try {
//			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//	//Extract username from token
//	public String getUsername(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//	//Valid tokens
//		public Boolean validateToken(String token,UserDetails userDetails) {
//			final String username = getUsername(token);
//			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//		}
//	
//	//Extract exp date from token
//	public Date getExpirationDate(String token) {
//		return extractClaim(token, Claims::getExpiration);
//	}
//	
//	//Extract specific claims from toekn
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimsResolver.apply(claims);
//	};
//	
//	//Extract all claims form token
//	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//	}
//	
//	//Check if toekn is expired
//	private Boolean isTokenExpired(String token) {
//		return getExpirationDate(token).before(new Date());
//	}
//	
//	//Generate a token for user
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		return createToken(claims, userDetails.getUsername());
//	}
//	
//	
//	//Create new token
//	private String createToken(Map<String,Object> claims,String subject) {
//		return Jwts.builder()
//				.setClaims(claims).setSubject(subject).setIssuedAt(
//				new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*60*10))
//				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();				
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
