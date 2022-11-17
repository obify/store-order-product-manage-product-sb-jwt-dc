package com.assignment.api.security.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class AuditorAwareImpl implements AuditorAware {

	@Override
	public Optional getCurrentAuditor() {
		String name = "SYSTEM";
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			name = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return Optional.ofNullable(name);
	}
}
