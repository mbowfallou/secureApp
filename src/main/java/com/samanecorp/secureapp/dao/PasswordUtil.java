package com.samanecorp.secureapp.dao;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
	
	// Méthode pour hacher un mot de passe
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Méthode pour vérifier un mot de passe haché
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
