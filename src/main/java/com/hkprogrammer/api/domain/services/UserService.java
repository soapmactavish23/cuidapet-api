package com.hkprogrammer.api.domain.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hkprogrammer.api.core.security.AuthKeycloakService;
import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.repositories.UserRepository;
import com.hkprogrammer.api.domain.view_models.AuthLogin;
import com.hkprogrammer.api.domain.view_models.UserInputSocialModelDTO;
import com.hkprogrammer.api.domain.view_models.UserSaveInputModelDTO;

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
		authKeycloakService.createUser(user.getEmail(), user.getPassword());
		return repository.save(user);
	}
	
	public Map<String, String> loginWithEmailAndPassword(AuthLogin authLogin) {
		return authKeycloakService.token(authLogin);
	}
	
	public String loginWithSocial(UserInputSocialModelDTO dto) {
		User user = findByEmail(dto.getEmail());
		if(user == null) {
			user = dto.convertToUser();
			authKeycloakService.createUser(user.getEmail(), user.getPassword());
			user = repository.save(user);
			return loginWithEmailAndPassword(new AuthLogin(user.getEmail(), user.getEmail()));
		} else {
			return loginWithEmailAndPassword(new AuthLogin(user.getEmail(), user.getEmail())); 
		}
	}
	
	private User findByEmail(String email) {
		return repository.findByEmail(email).orElse(null);
	}

}
