package com.hkprogrammer.api.domain.services;

import org.springframework.stereotype.Service;

import com.hkprogrammer.api.core.security.AuthKeycloakService;
import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.models.dto.AuthLogin;
import com.hkprogrammer.api.domain.models.dto.UserSaveInputModelDTO;
import com.hkprogrammer.api.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	
	private final AuthKeycloakService authKeycloakService;
	
	@Transactional
	public User createUser(UserSaveInputModelDTO dto) {
		User user = dto.convertUser();
		authKeycloakService.createUser(dto.getEmail(), dto.getPassword());
		return repository.save(user);
	}
	
	public String token(AuthLogin authLogin) {
		return authKeycloakService.token(authLogin);
	}

}
