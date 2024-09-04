package com.hkprogrammer.api.core.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
			user.put("enabled", true);

			Map<String, Object> credentials = new HashMap<>();
			credentials.put("type", "password");
			credentials.put("value", password);
			credentials.put("temporary", false);

			user.put("credentials", List.of(credentials));

			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);

			// Cria o usuário e obtém a URI do novo usuário
			var response = restTemplate.postForEntity(url, entity, String.class);
			String locationHeader = response.getHeaders().getLocation().toString();

			// Extrair o ID do usuário recém-criado da resposta
			String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);

			// Atribuir ROLE_MAPPING ao usuário
			assignRoleToUser(userId, "USER", accessToken);

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
			throw new AuthCredentialException("Erro ao obter o token de acesso");
		}
	}

	public String refreshAccessToken(String refreshToken) {
		try {
			HttpHeaders headers = new HttpHeaders();
			RestTemplate rt = new RestTemplate();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
			formData.add("grant_type", "refresh_token");
			formData.add("client_id", clientId);
			formData.add("client_secret", clientSecret);
			formData.add("refresh_token", refreshToken);

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(formData,
					headers);

			var result = rt.postForEntity(urlLogin, entity, String.class);
			return result.getBody();
		} catch (Exception e) {
			String message = "Erro ao obter o refreshToken";
			log.error(message.toUpperCase().concat(": ") + e.getMessage());
			throw new AuthCredentialException("Erro ao obter o refreshToken");
		}
	}

	@SuppressWarnings("rawtypes")
	private void assignRoleToUser(String userId, String roleName, String accessToken) {
		try {
			// URL para obter o ID do papel
			String roleUrl = String.format("%s/admin/realms/%s/roles/%s", keycloakAuthServerUrl, realm, roleName);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(accessToken);

			// Buscar o papel pelo nome
			ResponseEntity<Map> roleResponse = restTemplate.exchange(roleUrl, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
			String roleId = (String) roleResponse.getBody().get("id");

			String roleMappingUrl = String.format("%s/admin/realms/%s/users/%s/role-mappings/realm", keycloakAuthServerUrl, realm, userId);

			// Adicionar o papel ao usuário
			List<Map<String, String>> roles = List.of(
					Map.of(
							"id", roleId,
							"name", roleName
					)
			);

			HttpEntity<List<Map<String, String>>> roleEntity = new HttpEntity<>(roles, headers);
			restTemplate.postForEntity(roleMappingUrl, roleEntity, String.class);
		} catch (Exception e) {
			log.error("ERRO AO ATRIBUIR PAPEL AO USUÁRIO: ".concat(e.getMessage()));
			throw new GenericException("Erro ao atribuir papel ao usuário");
		}
	}

	public String getEmailFromToken(Authentication authentication) {
		if (authentication instanceof JwtAuthenticationToken) {
			JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
			Jwt jwt = jwtToken.getToken();
			return jwt.getClaim("email");
		} else {
			throw new GenericException("Erro ao recuperar e-mail no token");
		}

	}

}
