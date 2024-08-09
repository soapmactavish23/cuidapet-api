package com.hkprogrammer.api.domain.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.models.dto.UserSaveInputModelDTO;
import com.hkprogrammer.api.domain.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {

	private final UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody @Valid UserSaveInputModelDTO dto) {
		User user = service.createUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
}
