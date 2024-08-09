package com.hkprogrammer.api.domain.services;

import org.springframework.stereotype.Service;

import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.models.dto.UserSaveInputModelDTO;
import com.hkprogrammer.api.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	
	@Transactional
	public User createUser(UserSaveInputModelDTO dto) {
		User user = dto.convertUser();
		return repository.save(user);
	}

}
