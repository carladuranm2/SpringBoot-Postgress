package com.proyect.carla_av.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyect.carla_av.model.LoginResponse;
import com.proyect.carla_av.model.Person;
import com.proyect.carla_av.repository.UserRepository;
import com.proyect.carla_av.utils.security.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginService {
	
	public static final String USER_NOT_FOUND="User %1$s was not found.";
	
	public static final String CREDENTIALS_NOT_MATCH="The supplied credentials do not match. ";
	
	
	public static final String USER_INACTIVE="The user %1$s is not active. ";
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Checks that the login is correct and finally accepts or rejects the login.
	 * @param credentials
	 * @return
	 */
	public LoginResponse validateCredentials(Credential credentials) {
		byte[] usernameStream = Base64.getDecoder().decode(credentials.getUsername().getBytes(StandardCharsets.UTF_8));
		byte[] passwordStream = Base64.getDecoder().decode(credentials.getPassword().getBytes(StandardCharsets.UTF_8));
		
		String username = new String (usernameStream);
		String password = new String(passwordStream);
				
		String errorMessage = String.format(USER_NOT_FOUND, username);
		Person user = null;
		user = userRepository.findUserByEmail(username);
		LoginResponse lgResponse = new LoginResponse();
		if(user!=null) {
			return passwordMatches(user, password);
		}
		lgResponse.setStatus(LoginResponse.INVALID_USERNAME);
		lgResponse.setErrorMessage(errorMessage);
		return lgResponse;
	}
	
	/**
	 * verifies that the passwords match
	 * @param usuario username
	 * @param password password
	 * @return
	 */
	private LoginResponse passwordMatches(Person usuario, String password) {
		LoginResponse loginResponse=new LoginResponse();
		if (passwordEncoder.matches(password, usuario.getPassword())){			
			String token=appendJSonWebToken(usuario);
			loginResponse.setToken(token);
			loginResponse.setStatus(LoginResponse.OK);
			loginResponse.setUserId(usuario.getId());
			loginResponse.setUsername(usuario.getName());
			loginResponse.setUserEmail(usuario.getEmail());
			loginResponse.setPer(usuario.getRol());
			return loginResponse;
		}
		loginResponse.setStatus(LoginResponse.INVALID_CREDENTIALS);
		loginResponse.setErrorMessage(String.format(CREDENTIALS_NOT_MATCH));
		return loginResponse;
	}
	
	/**
	 * If the credentials are correct, we have to create a new token to return it to the client.
	 * @param user
	 * @return
	 */
	private String appendJSonWebToken (Person user) {
		Date date=getExpirationDate();
		String token=Jwts.builder().setSubject(String.valueOf(user.getId()))
					  .setExpiration(date)
					  .signWith(SignatureAlgorithm.HS512, SecurityConstants.getSecret())
					  .compact();
		return token;
	}
	
	/**
	 * Build an new expiration date of the Token
	 * @return
	 */
	private Date getExpirationDate () {
		Date date = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
		return date;
	}
	
	public Optional<Person> findUserById(Long id) {
		return userRepository.findById(id);
	}
}
