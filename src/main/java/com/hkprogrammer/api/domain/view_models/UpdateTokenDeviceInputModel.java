package com.hkprogrammer.api.domain.view_models;

import com.hkprogrammer.api.domain.models.enums.Platform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTokenDeviceInputModel {
	
	@NotNull
	private Integer userId;

	@NotBlank
	private String token;
	
	@NotNull
	private Platform platform;
	
}
