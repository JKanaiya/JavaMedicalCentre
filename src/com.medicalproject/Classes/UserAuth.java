package src.com.medicalproject.Classes;

import org.mindrot.jbcrypt.BCrypt;

public class UserAuth {

    /**
     * hash password for security reasons
     * @param password
     * @return hashedPassword
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public String checkRole(String person){
//        check if user exists in Doctor Table
        if(person == "Doctor"){

        }
        return "Doctor";
//        check if user exists in Staff Table
        return "Staff";
//        check if user exists in Admin Table
        return "Admin";
    }
}
