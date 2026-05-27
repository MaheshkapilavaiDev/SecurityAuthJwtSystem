package com.securitysystem.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.securitysystem.entity.Role;
import com.securitysystem.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String SECRET = "mySecretKeymySecretKeymySecretKeymySecretKey";

	// ACCESS TOKEN
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();

		claims.put("tenant_id", user.getTenantId());

		claims.put("roles", user.getRoles().stream().map(Role::getRoleName).toList());

		return createToken(claims, user.getUsername(), 1000 * 60 * 60 // 1 hour
		);
	}

	// REFRESH TOKEN
	public String generateRefreshToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		
		claims.put(
	            "tenant_id",
	            user.getTenantId()
	    );


		claims.put("type", "REFRESH_TOKEN");

		return createToken(claims, user.getUsername(), 1000L * 60 * 60 * 24 * 7 // 7 days
		);
	}

	// COMMON TOKEN CREATION
	private String createToken(Map<String, Object> claims, String username, long expirationTime) {

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	// EXTRACT USERNAME
	public String extractUsername(String token) {

		return extractAllClaims(token).getSubject();
	}

	// EXTRACT CLAIMS
	public Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	// VALIDATE TOKEN
	public boolean validateToken(String token, UserDetails userDetails) {

		final String username = extractUsername(token);

		return username.equals(userDetails.getUsername());
	}

	// SECRET KEY
	private Key getSignKey() {

		byte[] keyBytes = Decoders.BASE64.decode(SECRET);

		return Keys.hmacShaKeyFor(keyBytes);
	}
}