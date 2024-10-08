package com.hkprogrammer.api.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.hkprogrammer.api.core.config.Constants;
import com.hkprogrammer.api.core.security.Sha256;
import com.hkprogrammer.api.domain.models.enums.RegisterType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(schema = Constants.SCHEME, name = Constants.TABLE_USER)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Email
	@Size(max = 45)
	private String email;
	
	@Column(name = "senha")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Column(name = "tipo_cadastro")
	@Enumerated(EnumType.STRING)
	private RegisterType registerType;
	
	@Column(name = "ios_token")
	private String iosToken;
	
	@Column(name = "android_token")
	private String androidToken;
	
	@Column(name = "refresh_token")
	private String refreshToken;
	
	@Column(name = "social_id")
	private String socialKey;
	
	@Column(name = "img_avatar")
	private String imageAvatar;
	
	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Supplier supplier;
	
	@PrePersist
	public void prePersist() {
		if(this.id == null) {
			this.password = Sha256.encrypt(this.password, Sha256.generateKey());
		}
	}
	
}
