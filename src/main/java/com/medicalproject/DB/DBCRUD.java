package com.medicalproject.DB;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static com.medicalproject.LoginInstance.role;
import static com.medicalproject.LoginInstance.setRole;
import static com.medicalproject.TimeControl.convertLDTToTimestamp;

/**
 *  Java Program to hold all the CRUD Database Interactions.
 * @author J.Kanaiya
 */

public class DBCRUD {
    static DataSource dataSource = createDataSource();
    /**
     * Method to get a map ( DoctorID - Name ) of Doctors that will realistically be available in the dateTime.
     * @param Specialization
     * @param dateTime
     * @return  Map ( DoctorID - Name ) of realistically available Doctors
     */
    public static Map<Integer, String> getSpecializedMap(String Specialization, LocalDateTime dateTime){
        Map<Integer, String> idName = new HashMap<>(); // Initialize the map to hold the ID and Name of the Doctors to return
//         Declare String with Parameters for PreparedStatement.
        String sqlPS = "SELECT DoctorID, Name FROM Doctors WHERE Specialization = ? AND DoctorID NOT IN (" +
                "SELECT DoctorID FROM Appointments WHERE Specialization = ? AND AppointmentTime BETWEEN ? AND ?)";
//         Use a try...with resources to :
//         1. get a Connection from the dataSource
//         2. Initialize the PreparedStatement
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlPS)){
//          Set the appt parameters for the PreparedStatement
            ps.setString(1, Specialization);
            ps.setString(2, Specialization);
            ps.setTimestamp(3, convertLDTToTimestamp(dateTime.minusHours(1)));
            ps.setTimestamp(4, convertLDTToTimestamp(dateTime.plusHours(1)));
//          Call the database and prep the resSet for use
            ResultSet resSet = ps.executeQuery();
//          Iterate to place the DoctorID, Name in the Map for each available
            while(resSet.next()){
                idName.put(resSet.getInt("DoctorID"), resSet.getString("Name"));
            }
        }
        catch (SQLException sqle) {
            System.err.println("Error: " + sqle.getLocalizedMessage());
            sqle.printStackTrace();
        }
        return idName;
    }
    public static boolean validateUser(int ID, String password){
        String sqlPS = "Select * from USERS where id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)){

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
//            warn the user that there was an error and to try again
        }
    }
    public static void registerUser(int ID, String password, String role){
        String sqlPS = "INSERT into USERS(ID, Password, Role) VALUES (? , ? , ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {

            ps.setInt(1, ID);
            ps.setString(2, password);
            ps.setString(3, role);

            int insertCount = ps.executeUpdate();
            // add a trigger here to show a success or failure if the execution was successful
            if(insertCount == 1 ){
                System.out.println("Successful Registration of User into Users Table");
            }
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public static void registerDoctor(int ID, String password, String Specialization, String Name, int PhoneNumber, String Email){
        String role = "Doctor";
        registerUser(ID, password, role);
        String sqlPS = "INSERT into DOCTORS(ID, Name, Specialization, PhoneNo, Email) VALUES (?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)){

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
    public static void registerStaff(int StaffID, String password, String Name, int PhoneNumber, String Email){
        String role = "Staff";
        registerUser(StaffID, password, role);
        if(role.equals("Admin")){
            String sqlPS = "INSERT INTO STAFF(StaffID, Name, PhoneNumber, Email) VALUES ( ?,?,?,? )";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlPS)) {

                ps.setInt(1, StaffID);
                ps.setString(2, Name);
                ps.setInt(3, PhoneNumber);
                ps.setString(4, Email);

                int insertCount = ps.executeUpdate();

                if(insertCount == 1){
                    System.out.println("Successful Removal of STAFF from STAFF Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
    public static void registerAdmin(int ID, String password){
        String role = "Admin";
        registerUser(ID, password, role);
//        optionally add more details to the admin ( Phone number, email )
    }
    public static void removeUser(int ID){
        String sqlPS = "DELETE from USERS where ID = ? )";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlPS)) {

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
        String sqlPS = "DELETE from DOCTORS where ID = ? )";
        if(role.equals("Admin")){
            try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlPS)) {

                ps.setInt(1, ID);

                int insertCount = ps.executeUpdate();

                if(insertCount == 1){
                    System.out.println("Successful Removal of Doctor from Doctors Table");
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
            String sqlPS = "DELETE from STAFF where StaffID = ? )";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlPS)) {

                ps.setInt(1, ID);

                int insertCount = ps.executeUpdate();

                if(insertCount == 1){
                    System.out.println("Successful Removal of STAFF from STAFF Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        removeUser(ID);
    }
    public static void updateDoctor(int DoctorID, String Specialization, int PhoneNumber,  String Email){
        if(role.equals("Admin")){
            String sqlPS = "UPDATE Doctors SET Specialization = ?, PhoneNumber = ?, Email = ? WHERE DoctorID = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlPS)) {

                ps.setString(1, Specialization);
                ps.setInt(2, PhoneNumber);
                ps.setString(3, Email);
                ps.setInt(4, DoctorID);

                int insertCount = ps.executeUpdate();

                if(insertCount == 1){
                    System.out.println("Successful Update of Doctors from Doctors Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
    public static void updateStaff(int StaffID, int PhoneNumber, String Email){
        if(role.equals("Admin")){
            String sqlPS = "UPDATE Staff SET PhoneNumber = ?, Email = ? WHERE StaffID = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlPS)) {

                ps.setInt(1, PhoneNumber);
                ps.setString(2, Email);
                ps.setInt(3, StaffID);

                int insertCount = ps.executeUpdate();

                if(insertCount == 1){
                    System.out.println("Successful Update of Staff from Staff Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
    public static void updatePatient(int PatientID, String Address, int Age,  String BloodGroup, Double Weight, Double Height){
        if(role.equals("Admin")){
            String sqlPS = "UPDATE Patients SET Address = ?, Age = ?, BloodGroup = ? , Weight = ? , Height = ? WHERE PatientID = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sqlPS)) {

                ps.setString(1, Address);
                ps.setInt(2, Age);
                ps.setString(3, BloodGroup);
                ps.setDouble(4, Weight);
                ps.setDouble(5, Height);
                ps.setInt(6, PatientID);

                int insertCount = ps.executeUpdate();

                if(insertCount == 1){
                    System.out.println("Successful Update of Patient from Patients Table");
                }
            }
            catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
    public static void addAppointment(int PatientID, int DoctorID, String reason, LocalDateTime dateTime, String Specialization){
        Timestamp convertedTimestamp = convertLDTToTimestamp(dateTime);
        String sqlPS = "INSERT INTO Appointments(PatientID, DoctorID, AppointmentTime, Reason, Specialization) VALUES(?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sqlPS)) {

            ps.setInt(1,PatientID);
            ps.setInt(2 ,DoctorID);
            ps.setTimestamp(3 , convertedTimestamp);
            ps.setString(4 ,reason);
            ps.setString(5 ,Specialization);

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
