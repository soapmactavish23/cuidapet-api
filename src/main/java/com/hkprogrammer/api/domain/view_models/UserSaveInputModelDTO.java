package com.hkprogrammer.api.domain.view_models;

import com.hkprogrammer.api.domain.models.Supplier;
import com.hkprogrammer.api.domain.models.User;
import com.hkprogrammer.api.domain.models.enums.RegisterType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveInputModelDTO {

	@Email
	private String email;
	
	@NotBlank
	@Size(min = 3, max = 45)
	private String password;
	
	private Integer supplierId;
	
	public User convertUser() {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setRegisterType(RegisterType.APP);
		user.setSupplierId(supplierId);
		return user;
	}
	
}
