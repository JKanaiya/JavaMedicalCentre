package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBQUeries {
    public static boolean validateUser(int ID, String password){
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MedicalJavaDb", "sa", "JavaPassword" );){
            System.out.println("valid connection");
            return true;
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
