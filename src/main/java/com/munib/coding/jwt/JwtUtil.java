package com.munib.coding.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we will need the secret key
    private Claims extractAllClaims(String token) {
//    	return Jwts.parserBuilder()  // Create JwtParserBuilder
//    	        .setSigningKey(SECRET_KEY)  // Set the signing key
//    	        .build()  // Build JwtParser
//    	        .parseClaimsJws(token)  // Parse the token
//    	        .getBody();  // Get the body
    	return null;
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    	return Jwts.builder()
//				.setSubject("munib22")
//				.setIssuedAt(new Date())
//				.setExpiration(new Date(System.currentTimeMillis() + 604800000)) //1 Week exp
//				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//				.compact();
        @SuppressWarnings("deprecation")
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Create a JWT token
        String jws = Jwts.builder()
            .setSubject(userDetails.getUsername())  // Set the subject (username)
            .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issued date
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Set the expiration date
            .signWith(key)  // Sign the token with the generated key
            .compact();  // Build the token

        return jws;
    }

    // While creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS256 algorithm and secret key.
    // 3. According to JWS Compact Serialization (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //    compaction of the JWT to a URL-safe string 
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

