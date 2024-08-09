package com.hkprogrammer.api.domain.models;

import com.hkprogrammer.api.domain.models.enums.RegisterType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Email
	@Size(max = 45)
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private RegisterType registerType;
	
	private String iosToken;
	
	private String androidToken;
	
	private String refreshToken;
	
	private String socialKey;
	
	private String imageAvatar;
	
	//TODO: depois fazer relacionamento com fornecedor 
	private Integer supplierId;
	
}
