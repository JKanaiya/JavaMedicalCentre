package src.com.medicalproject;

import java.sql.*;

import static src.com.medicalproject.LoginInstance.role;
import static src.com.medicalproject.LoginInstance.setRole;

public class DBQueries {
//    optional data source init
//    DataSource dataSource = createDataSource();
//    to apply, replace DriverManager with 'Connection conn = dataSource.getConnection'
    public static boolean validateUser(int ID, String password){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                + "trustServerCertificate=true;"  , "", "" );){
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
    public static void registerUser(int ID, String password, String role){
        try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                + "trustServerCertificate=true;"  , "", "" );){
            PreparedStatement ps = conn.prepareStatement("INSERT into USERS(ID, Password, Role) VALUES (? , ? , ?)");
            ps.setInt(1, ID);
            ps.setString(2, password);
            ps.setString(3, role);
            int insertCount = ps.executeUpdate();
            System.out.println(insertCount);
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public static void registerDoctor(int ID, String password, String Specialization, String Name, int PhoneNumber, String Email){
        String role = "Doctor";
        registerUser(ID, password, role);
        try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                + "trustServerCertificate=true;"  , "sa", "" );){
            PreparedStatement ps = conn.prepareStatement("INSERT into DOCTORS(ID, Name, Specialization, PhoneNo, Email) VALUES (?,?,?,?,?)");
            ps.setInt(1, ID);
            ps.setString(2, Name);
            ps.setString(3, Specialization);
            ps.setInt(4, PhoneNumber);
            ps.setString(5, Email);
            int insertCount = ps.executeUpdate();
            System.out.println(insertCount);
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public static void registerStaff(int ID, String password){
        String role = "Staff";
        registerUser(ID, password, role);
//        optionally add more details to the staff ( Phone number, email )
    }
    public static void registerAdmin(int ID, String password){
        String role = "Admin";
        registerUser(ID, password, role);
//        optionally add more details to the admin ( Phone number, email )
    }
    public static void removeUser(int ID){
            try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                    + "trustServerCertificate=true;"  , "sa", "" );) {
                PreparedStatement ps = conn.prepareStatement("DELETE from USERS where ID = ? )");
                ps.setInt(1, ID);
                int insertCount = ps.executeUpdate();
                if(insertCount == 1){
                    System.out.println("Successful Deletion");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
    }
    public static void removeDoctor(int ID){
        if(role.equals("Admin")){
            try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                    + "trustServerCertificate=true;"  , "sa", "" );) {
                PreparedStatement ps = conn.prepareStatement("DELETE from DOCTORS where ID = ? )");
                ps.setInt(1, ID);
                int insertCount = ps.executeUpdate();
                if(insertCount == 1){
                    System.out.println("Successful Deletion of Doctor from Doctors Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        removeUser(ID);
    }
    public static void removeStaff(int ID){
        if(role.equals("Admin")){
            try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
                    + "trustServerCertificate=true;"  , "sa", "" );) {
                PreparedStatement ps = conn.prepareStatement("DELETE from STAFF where ID = ? )");
                ps.setInt(1, ID);
                int insertCount = ps.executeUpdate();
                if(insertCount == 1){
                    System.out.println("Successful Deletion of STAFF from STAFF Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        removeUser(ID);
    }
    /**
     * Optional data source for efficiency
     * private static DataSource createDataSource(){
     *   HikariConfig dataS = new HikariConfig();
     *   dataS.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;");
     *   dataS.setUsername("sa");
     *   dataS.setPassword("");
     *   dataS.addDataSourceProperty("trustServerCertificate", "true");
     *   dataS.addDataSourceProperty("encrypt", "250");
     *   return ds
     * }
      */
}
