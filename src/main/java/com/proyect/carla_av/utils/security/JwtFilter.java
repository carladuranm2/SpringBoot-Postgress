package com.proyect.carla_av.utils.security;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {

	public JwtFilter() {
		super();
	}
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String authHeader = httpRequest.getHeader("Authorization");
			if (authHeader == null) {
				((HttpServletResponse) res).setStatus(222);
				return;
//				throw new ServletException("Missing or invalid Authorization header");
			}
			final String token = authHeader;
			final Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getSecret()).parseClaimsJws(token).getBody();
			System.out.print(claims.getSubject()+"\n");
			request.setAttribute("claims", claims);
			chain.doFilter(req, res);
		} catch (final SignatureException e) {
//			throw new ServletException("Invalid token");
			((HttpServletResponse) res).setStatus(222);
			return;
		}catch(final ExpiredJwtException p) {
			((HttpServletResponse) res).setStatus(222);
			return;
		}catch(final MalformedJwtException p) {
			((HttpServletResponse) res).setStatus(222);
			return;
		}
		
	}

}
