package com.wugao.vankeda.infrastructure.spring.security;

import org.springframework.security.crypto.password.StandardPasswordEncoder;


public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

	private static final String SITE_WIDE_SECRET = "i#am$the@secret";

	private org.springframework.security.crypto.password.PasswordEncoder encoder= new StandardPasswordEncoder(SITE_WIDE_SECRET);
	
	@Override
	public String encode(CharSequence rawPassword) {
		return encoder.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String password) {
		return encoder.matches(rawPassword, password);
	}

}
