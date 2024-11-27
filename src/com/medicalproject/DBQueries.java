package src.com.medicalproject;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

import static src.com.medicalproject.LoginInstance.role;
import static src.com.medicalproject.LoginInstance.setRole;

public class DBQueries {

    public Timestamp convertLDTToTimestamp(LocalDateTime dateTime){
        return Timestamp.valueOf(dateTime);
    }
    static DataSource dataSource = createDataSource();

    public static boolean validateUser(int ID, String password){
        // try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
        //         + "trustServerCertificate=true;"  , "", "" );){
        try (Connection conn = dataSource.getConnection()) {
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
        // try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
        //         + "trustServerCertificate=true;"  , "", "" );){
        try (Connection conn = dataSource.getConnection()) {
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
        // try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
        //         + "trustServerCertificate=true;"  , "sa", "" );){
        try (Connection conn = dataSource.getConnection()) {
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
            // try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
            //         + "trustServerCertificate=true;"  , "sa", "" );) {
            try (Connection conn = dataSource.getConnection()) {
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
            // try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
            //         + "trustServerCertificate=true;"  , "sa", "" );) {
            try (Connection conn = dataSource.getConnection()) {
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
            // try(Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb;" + "encrypt=true;"
            //         + "trustServerCertificate=true;"  , "sa", "" );) {
            try (Connection conn = dataSource.getConnection()) {
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
     * Method to takes data of the appointment and try to add it to the database
     * @param PatientID
     * @param DoctorID
     * @param reason
     * @param time
     * @param date
     */
    public void addAppointment(int PatientID, int DoctorID, String reason, LocalDateTime dateTime){
        Timestamp convertedTimestamp = convertLDTToTimestamp(dateTime);
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Appointments(PatientID, DoctorID, AppointmentTime, Reason) VALUES(?,?,?,?)");
            ps.setInt(1,PatientID);
            ps.setInt(2 ,DoctorID);
            ps.setTimestamp(3 , convertedTimestamp);
            ps.setString(4 ,reason);
            int insertCount = ps.executeUpdate();
            System.out.println(insertCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static DataSource createDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.addDataSourceProperty("trustServerCertificate", "true");
        ds.addDataSourceProperty("encrypt", "true");
        return ds;
    }
}
