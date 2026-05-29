package com.securitysystem.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.securitysystem.entity.Role;
import com.securitysystem.entity.User;
import com.securitysystem.repository.RoleRepository;
import com.securitysystem.repository.UserRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create-admin")
    public String createAdmin(@RequestBody User user) {

        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseThrow();

        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "Admin created successfully";
    }
}