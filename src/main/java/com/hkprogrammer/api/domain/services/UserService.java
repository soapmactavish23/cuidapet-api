package com.hkprogrammer.api.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.hkprogrammer.api.core.security.AuthKeycloakService;
import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.models.enums.Platform;
import com.hkprogrammer.api.domain.repositories.UserRepository;
import com.hkprogrammer.api.domain.view_models.AuthLogin;
import com.hkprogrammer.api.domain.view_models.UpdateTokenDeviceInputModel;
import com.hkprogrammer.api.domain.view_models.UpdateUrlAvatarViewModel;
import com.hkprogrammer.api.domain.view_models.UserConfirmInputModel;
import com.hkprogrammer.api.domain.view_models.UserInputSocialModelDTO;
import com.hkprogrammer.api.domain.view_models.UserSaveInputModelDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepository repository;
	
	private final AuthKeycloakService authKeycloakService;

	public User createUser(UserSaveInputModelDTO dto) {
		User user = dto.convertUser();
		authKeycloakService.createUser(user.getEmail(), user.getPassword());
		return repository.save(user);
	}
	
	@GetMapping
	public User update(User user) {
		User userSaved = findById(user.getId());
		
		BeanUtils.copyProperties(user, userSaved, "id");
		
		return repository.save(userSaved);
	}

	@Transactional
	public Map<String, Object> loginWithEmailAndPassword(AuthLogin authLogin) {
		Map<String, Object> completeToken = authKeycloakService.token(authLogin);

		User user = findByEmail(authLogin.getEmail());
		user.setRefreshToken(completeToken.get("refresh_token").toString());

		return completeToken;
	}
	
	public Map<String, Object> loginWithSocial(UserInputSocialModelDTO dto) {
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

	public Boolean existsUserByEmail(String email) {
		return repository.existsUserByEmail(email);
	}
	
	public User findByEmail(String email) {
		return repository.findByEmail(email).orElse(null);
	}

	private User findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	@Transactional
	public String confirmLogin(Authentication authentication, UserConfirmInputModel inputModel) {
		User user = findByAuthentication(authentication);
		String newRefreshToken = authKeycloakService.refreshAccessToken(user.getRefreshToken());
		user.setRefreshToken(newRefreshToken);
		user.setIosToken(inputModel.getIosToken());
		user.setAndroidToken(inputModel.getAndroidToken());

		user = repository.save(user);

		return user.getRefreshToken();
	}
	
	public User updateUrlAvatar(UpdateUrlAvatarViewModel inputModel) {
		User user = findById(inputModel.getUserId());
		user.setImageAvatar(inputModel.getUrlAvatar());
		return update(user);
	}
	
	public User updateDeviceToken(UpdateTokenDeviceInputModel inputModel) {
		User user = findById(inputModel.getUserId());
		
		if(inputModel.getPlatform() == Platform.ANDROID) {
			user.setAndroidToken(inputModel.getToken());
			user.setIosToken(null);
		} else {
			user.setAndroidToken(null);
			user.setIosToken(inputModel.getToken());
		}
		
		return update(user);
	}

	public User findByAuthentication(Authentication authentication) {
		String email = authKeycloakService.getEmailFromToken(authentication);
		return findByEmail(email);
	}

}
