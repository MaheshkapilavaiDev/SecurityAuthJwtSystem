package com.securitysystem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securitysystem.entity.AuditLog;
import com.securitysystem.repository.AuditLogRepository;

@Service
public class AuditService {

	@Autowired
	private AuditLogRepository repository;

	public void log(String username, String action) {

		AuditLog log = new AuditLog();

		log.setUsername(username);

		log.setAction(action);

		log.setTimestamp(LocalDateTime.now());

		repository.save(log);
	}
}