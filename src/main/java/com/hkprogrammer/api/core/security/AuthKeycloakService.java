package com.hkprogrammer.api.core.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hkprogrammer.api.core.exception.AuthCredentialException;
import com.hkprogrammer.api.core.exception.GenericException;
import com.hkprogrammer.api.domain.view_models.AuthLogin;

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

	@Value("${keycloak.auth-server-url}")
	private String keycloakAuthServerUrl;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.client-id-admin}")
	private String clientIdAdmin;
	
	@Value("${keycloak.client-admin-secret}")
	private String clientSecret;

	private final RestTemplate restTemplate;

	public AuthKeycloakService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

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

	public void createUser(String username, String password) {
		try {
			String accessToken = getAccessToken();

			String url = String.format("%s/admin/realms/%s/users", keycloakAuthServerUrl, realm);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(accessToken);

			Map<String, Object> user = new HashMap<>();
			user.put("username", username);
			user.put("email", username);
			user.put("firstName", username);
			user.put("lastName", username);
			user.put("email", username);
			user.put("enabled", true);

			Map<String, Object> credentials = new HashMap<>();
			credentials.put("type", "password");
			credentials.put("value", password);
			credentials.put("temporary", false);

			user.put("credentials", List.of(credentials));

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);

			restTemplate.postForEntity(url, entity, String.class);
		} catch (Exception e) {
			log.error("ERRO AO REGISTRAR USUÁRIO: ".concat(e.getMessage()));
			throw new GenericException("Erro ao registrar usuário");
		}
	}

	@SuppressWarnings("unchecked")
	private String getAccessToken() {
		String url = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakAuthServerUrl, realm);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "client_credentials");
		body.add("client_id", clientIdAdmin);
		body.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

		try {
			Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
			return (String) response.get("access_token");
		} catch (Exception e) {
			log.error("Erro ao obter o token de acesso: ", e);
			throw new GenericException("Erro ao obter o token de acesso");
		}
	}

	public String refreshAccessToken(String refreshToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "refresh_token");
		body.add("clientId", clientId);
		body.add("client_secret", clientSecret);
		body.add("refresh_token", refreshToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(urlLogin, HttpMethod.POST, request, String.class);

		if(response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new GenericException("Erro ao obter o refreshToken");
		}

	}

}
