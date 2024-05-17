package br.com.anthonycruz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.anthonycruz.data.dto.v1.security.AccountCredentialsDTO;
import br.com.anthonycruz.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService service;

	private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
		return data == null
		|| data.getUsername() == null
		|| data.getUsername().isBlank()
		|| data.getPassword() == null
		|| data.getPassword().isBlank();
	}
	
	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null
		|| refreshToken.isBlank()
		|| username == null
		|| username.isBlank();
	}

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticate a user and returns a token")
	@PostMapping(value = "/signin")
	public ResponseEntity signIn(@RequestBody AccountCredentialsDTO data) {
		if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

		var token = service.signIn(data);
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

		return token;
	}

	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable String username, @RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

		var token = service.refreshToken(username, refreshToken);
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

		return token;
	}
}
