package com.securitysystem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securitysystem.entity.RefreshToken;
import com.securitysystem.entity.User;
import com.securitysystem.repository.RefreshTokenRepository;


@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository repository;

	public RefreshToken saveToken(User user, String token) {

		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(user);

		refreshToken.setToken(token);

		refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

		return repository.save(refreshToken);
	}

	public boolean validateToken(String token) {

		RefreshToken refreshToken = repository.findByToken(token).orElseThrow();

		return !refreshToken.getRevoked() && refreshToken.getExpiryDate().isAfter(LocalDateTime.now());
	}

	public void revokeToken(String token) {

		RefreshToken refreshToken = repository.findByToken(token).orElseThrow();

		refreshToken.setRevoked(true);

		repository.save(refreshToken);
	}
}
