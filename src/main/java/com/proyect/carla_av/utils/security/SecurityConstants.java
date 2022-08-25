package com.proyect.carla_av.utils.security;


public class SecurityConstants {
	
	public static final String SECRET = "K3763RA71ONN33DS570B31NVAU77";
    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; 
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
	
   
   
    public SecurityConstants() {
    	super();
    }



	public static byte[] getSecret() {
		return SECRET.getBytes();
	}
	
}

