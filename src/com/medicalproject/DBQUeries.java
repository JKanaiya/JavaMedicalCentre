package src.com.medicalproject;

import java.sql.*;

import static src.com.medicalproject.LoginInstance.setRole;

public class DBQUeries {
    public static boolean validateUser(int ID, String password){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                + "trustServerCertificate=true;"  , "sa", "31F0rtkn0cks12" );){
            PreparedStatement ps = conn.prepareStatement("Select * from USERS where id = ?");
            ps.setInt(1, ID);
            ResultSet resSet = ps.executeQuery();
            if(resSet.next()){
                setRole(resSet.getString("Role"));
                return password.equals(resSet.getString("Password"));
            }
            else{
                return false;
            }
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
