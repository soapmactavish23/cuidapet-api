package com.hkprogrammer.api.domain.view_models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUrlAvatarViewModel {

	@NotNull
	private Integer userId;
	
	@NotBlank
	private String urlAvatar;
	
}
