package com.proyect.carla_av.controller;

import org.springframework.web.bind.annotation.RestController;

import com.proyect.carla_av.model.LoginResponse;
import com.proyect.carla_av.model.Person;
import com.proyect.carla_av.service.LoginService;
import com.proyect.carla_av.utils.security.SecurityConstants;

import io.jsonwebtoken.Claims;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.POST,
		RequestMethod.OPTIONS })
public class LoginController {
	
	@Autowired
	public LoginService service;
	
	/**
	 * It allows us to capture the token
	 * 
	 * @param request
	 * @return
	 */
	@ModelAttribute("claims")
	public Claims getUserClaims(HttpServletRequest request) {
		return (Claims) request.getAttribute("claims");
	}

	/**
	 * Receives the user credentials and if the credentials are correct, returns an
	 * authentication token.
	 * 
	 * @param credentials information encryped (email, password)
	 * @param request 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/login/")
	public ResponseEntity<?> login(@RequestBody Credential credentials, HttpServletRequest request) {
		LoginResponse response = service.validateCredentials(credentials);
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstants.HEADER_STRING, response.getToken());
		ResponseEntity<LoginResponse> responseEnt = new ResponseEntity<LoginResponse>(response, headers, HttpStatus.OK);
		return responseEnt;
	}
	
	/**
	 * This is a test function that allows us to verify if a token is valid.
	 * @param claims
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value="/secure/validate-token/")
	public Person validateToken(@ModelAttribute("claims") Claims claims) {
		Long userId = Long.valueOf(claims.getSubject());
		Optional<Person> user= service.findUserById(userId);
		if(user.isEmpty()) {
			return null;
		}
		return user.get();
	}
}
