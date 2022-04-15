package com.cha.product.jwt;

import static java.util.Objects.nonNull;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtTokenProvider jwtTokenProvider;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
		super(authenticationManager);
		jwtTokenProvider = tokenProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = jwtTokenProvider.getAuthentication(request);
		if (nonNull(authentication) && jwtTokenProvider.validateToken(request)) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}

}
