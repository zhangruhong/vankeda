package com.wugao.vankeda.infrastructure.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class EncryptUtil {

	private static final String SITE_WIDE_SECRET = "i#am$the@secret";

	private static final PasswordEncoder encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET);

	public static String encrypt(CharSequence rawPassword) {
		return encoder.encode(rawPassword);
	}

	public static boolean match(CharSequence rawPassword, String password) {
		return encoder.matches(rawPassword, password);
	}
	
	public static void main(String[] args) {
		String un = "password";
		String pwd = encrypt(un);
		System.out.println(pwd);
		System.out.println(match(un, "31e3f9c8e8a2f4ecceb8c1c73edf7d45dcdc530cc1596ceecfd0384d152234930b9043698e666013"));
	}

}
