package com.securitysystem.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.securitysystem.entity.Role;
import com.securitysystem.entity.User;
import com.securitysystem.repository.RoleRepository;
import com.securitysystem.repository.UserRepository;

@Component
public class DefaultAdminInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public DefaultAdminInitializer(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {

		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {

		Optional<User> existingAdmin = userRepository.findByUsername("admin");

		if (existingAdmin.isPresent()) {
			return;
		}

		Role adminRole = roleRepository.findByRoleName("ADMIN").orElseGet(() -> {
			Role role = new Role();
			role.setRoleName("ADMIN");
			return roleRepository.save(role);
		});

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin123"));

		Set<Role> roles = new HashSet<>();
		roles.add(adminRole);

		admin.setRoles(roles);

		userRepository.save(admin);

		System.out.println("Default admin created");
	}
}
