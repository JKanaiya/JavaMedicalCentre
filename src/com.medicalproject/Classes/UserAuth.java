package src.com.medicalproject.Classes;

import org.mindrot.jbcrypt.BCrypt;

public class UserAuth {

    /**
     * hash password for security reasons
     * @param password
     * @return hashedPassword
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(4));
    }
}
