package com.proyect.carla_av.model;


public class LoginResponse {
	
	public static final String OK="ok";
	
	public static final String INVALID_USERNAME="invaliduser";

	public static final String INVALID_CREDENTIALS = "invalidCredentials";
	
	public static final String INACTIVE_USER = "inactiveUser";
	
	private Long userId;
	
	private String status;
	
	private String errorMessage;
	
	private String token;
	
	private String username;
	
	private String userEmail;
	
	private String per;
	
	public String getPer() {
		return per;
	}

	public void setPer(String per) {
		this.per = per;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	

}
