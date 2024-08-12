package com.hkprogrammer.api.domain.view_models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLogin {

	@Email
	private String email;
	
	@NotBlank
	private String password;
	
}
