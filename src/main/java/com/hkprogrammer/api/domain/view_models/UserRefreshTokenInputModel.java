package com.hkprogrammer.api.domain.view_models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRefreshTokenInputModel {

	@NotNull
	private Integer user;
	
	private Integer supplier;
	
	@NotBlank
	private String accessToken;
	
	@NotBlank
	private String refreshToken;
	
	
}
