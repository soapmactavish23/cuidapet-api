package com.hkprogrammer.api.domain.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthLogin {

	@Email
	private String email;
	
	@NotBlank
	private String password;
	
}
