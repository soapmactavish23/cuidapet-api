package com.hkprogrammer.api.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hkprogrammer.api.core.exception.AuthCredentialException;
import com.hkprogrammer.api.domain.models.dto.AuthLogin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthKeycloakService {

	@Value("${keycloak.client-id}")
	private String clientId;

	@Value("${keycloak.grant-type}")
	private String grantType;

	@Value("${keycloak.url-login}")
	private String urlLogin;

	public String token(AuthLogin authLogin) {

		try {
			
			HttpHeaders headers = new HttpHeaders();
			RestTemplate rt = new RestTemplate();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
			formData.add("client_id", clientId);
			formData.add("username", authLogin.getEmail());
			formData.add("password", authLogin.getPassword());
			formData.add("grant_type", grantType);

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(formData,
					headers);

			var result = rt.postForEntity(urlLogin, entity, String.class);

			return result.getBody();
			
		} catch (Exception e) {
			log.error("ERRO AO AUTENTICAR: ".concat(e.getMessage()));
			throw new AuthCredentialException("Usuário ou senha inválidos");
		}

	}

}
