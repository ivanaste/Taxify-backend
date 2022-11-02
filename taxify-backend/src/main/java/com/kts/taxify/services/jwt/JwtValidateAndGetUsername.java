package com.kts.taxify.services.jwt;

import com.kts.taxify.configProperties.CustomProperties;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtValidateAndGetUsername {
	private final CustomProperties customProperties;

	public String execute(final String token) {
		try {

			//ovde si izmenila exceptione
			return Jwts.parser().setSigningKey(customProperties.getJwtSecret().getBytes()).parseClaimsJws(token).getBody().getSubject();
		} catch (final ExpiredJwtException ex) {
			throw new RuntimeException();
		} catch (final SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new RuntimeException();
		}
	}
}
