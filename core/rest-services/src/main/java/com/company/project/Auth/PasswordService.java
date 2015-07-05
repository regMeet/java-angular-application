package com.company.project.Auth;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordService {
	public static String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}

	/**
	 * 
	 * @param plaintext from webservices
	 * @param hashed from database
	 * @return
	 */
	public static boolean checkPassword(String plaintext, String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}
}
