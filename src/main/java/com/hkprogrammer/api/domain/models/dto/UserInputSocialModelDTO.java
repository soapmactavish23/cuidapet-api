package com.hkprogrammer.api.domain.models.dto;

import com.hkprogrammer.api.domain.models.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserInputSocialModelDTO {

	@Email
	private String email;

	@NotBlank
	private String avatar;

	@NotBlank
	private String socialType;

	@NotBlank
	private String socialKey;

	public User convertToUser() {
		User user = new User();
		user.setEmail(email);
		user.setImageAvatar(avatar);
		user.setSocialKey(socialKey);
		user.setSocialKey(socialKey);
		return user;
	}

}
