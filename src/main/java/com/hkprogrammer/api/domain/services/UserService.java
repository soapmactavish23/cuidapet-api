package com.hkprogrammer.api.domain.services;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	@GetMapping
	public User update(User user) {
		User userSaved = findById(user.getId());
		
		BeanUtils.copyProperties(user, userSaved, "id");
		
		return repository.save(userSaved);
	}
	
	public String loginWithEmailAndPassword(AuthLogin authLogin) {
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

	public Boolean existsUserByEmail(String email) {
		return repository.existsUserByEmail(email);
	}
	
	public User findByEmail(String email) {
		return repository.findByEmail(email).orElse(null);
	}

	public User findByEmailOrElseThrow(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	private User findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

	@Transactional
	public String confirmLogin(UserConfirmInputModel inputModel) {
		User user = findById(inputModel.getUserId());
		String newRefreshToken = authKeycloakService.refreshAccessToken(inputModel.getRefreshToken());
		user.setRefreshToken(newRefreshToken);
		user.setIosToken(inputModel.getIosDeviceToken());
		user.setAndroidToken(inputModel.getAndroidDeviceToken());

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

	public User findBySupplier(Integer id) {
		return repository.findBySupplierId(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}

}
