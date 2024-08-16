package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.view_models.UserConfirmInputModel;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.services.UserService;
import com.hkprogrammer.api.domain.view_models.AuthLogin;
import com.hkprogrammer.api.domain.view_models.UserInputSocialModelDTO;
import com.hkprogrammer.api.domain.view_models.UserSaveInputModelDTO;

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
	
	@PostMapping("/login/email-and-password")
	public ResponseEntity<String> loginWithEmailAndPassword(@RequestBody @Valid AuthLogin authLogin) {
		String token = service.loginWithEmailAndPassword(authLogin);
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/login/social")
	public ResponseEntity<String> loginSocial(@RequestBody @Valid UserInputSocialModelDTO dto) {
		String token = service.loginWithSocial(dto);
		return ResponseEntity.ok(token);
	}

	@PatchMapping("/confirm")
	public ResponseEntity<String> confirmLogin(@RequestBody @Valid UserConfirmInputModel dto) {
		String newAccessToken = service.confirmLogin(dto);
		return ResponseEntity.ok(newAccessToken);
	}
	
}
