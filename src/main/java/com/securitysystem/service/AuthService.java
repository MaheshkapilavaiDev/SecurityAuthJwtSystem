package com.securitysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securitysystem.dto.LoginRequest;
import com.securitysystem.dto.RegisterRequest;
import com.securitysystem.dto.TokenResponse;
import com.securitysystem.entity.User;
import com.securitysystem.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public TokenResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

		String accessToken = jwtService.generateToken(user);

		String refreshToken = jwtService.generateRefreshToken(user);

		return new TokenResponse(accessToken, refreshToken);
	}

	public String register(RegisterRequest request) {

		User user = new User();

		user.setUsername(request.getUsername());

		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user.setTenantId(request.getTenantId());

		userRepository.save(user);

		return "User Registered Successfully";
	}
}
