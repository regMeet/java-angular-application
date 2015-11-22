package com.company.project.security;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("passwordEncoder")
public class PasswordEncoderimpl implements PasswordEncoder {
	/**
	 * 
	 * it is the algorithm the transfer password to encrypted password
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println("encoding password " + rawPassword);
		return null;
	}

	/**
	 * encPass is the password in your database 
	 * rawPass is the password user entering 
	 * then you can write it like
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
	    if (StringUtils.isNotBlank(encodedPassword)) {
	        return checkPassword(rawPassword.toString(), encodedPassword);
	    } else {
	        return false;
	    }
	}

	public static String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}

	public static boolean checkPassword(String plaintext, String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}
}