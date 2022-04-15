package com.cha.product.jwt;

import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("$(app.jwt.secret)")
	private String jwtSecret;

	@Value("$(app.jwt.token.prefix)")
	private String jwtTokenPrefix;

	@Value("$(app.jwt.header.string)")
	private String jwtHeaderString;

	@Value("$(app.jwt.expiration-in-ms)")
	private Long jwtExpirationInMs;
	
	private final static String SPRING_DEFAULT_ROLE_PREFIX = "ROLE_";

	public String generateToken(Authentication auth) {
		//stream().map() lets you convert an object to something else
		String authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining());
		
		return Jwts.builder().setSubject(auth.getName())
				.claim("roles", authorities)
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public Authentication getAuthentication(HttpServletRequest req) {
		String token = resolveToken(req);
		if (nonNull(token)) {
			Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			String userName = claims.getSubject();
			final List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
					.map(role -> role.startsWith(SPRING_DEFAULT_ROLE_PREFIX) ? role : SPRING_DEFAULT_ROLE_PREFIX + role)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			
			return nonNull(userName) ? new UsernamePasswordAuthenticationToken(userName, null, authorities) : null;
		}
		return null;
	}
	
	//we will check the expire date of the token
	public boolean validateToken(HttpServletRequest req) {
		String token = resolveToken(req);
		if (nonNull(token)) {
			Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return claims.getExpiration().after(new Date());
		}
		return false;
	}
	
	private String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(jwtHeaderString);
		if (nonNull(bearerToken) && bearerToken.startsWith(jwtTokenPrefix)) {
			return bearerToken.substring(jwtTokenPrefix.length() + 1, bearerToken.length());
		}
		return null;
	}
}
