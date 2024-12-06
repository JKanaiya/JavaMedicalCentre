package com.medicalproject.DB;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import static com.medicalproject.Controllers.LoginController.getRole;
import static com.medicalproject.Controllers.LoginController.setRole;
import static com.medicalproject.TimeControl.*;

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
        if(getRole().equals("Admin")){
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
    public static void removeData(int ID, String Table, String Column){
        String sqlPS = "DELETE from " + Table + " WHERE " + Column + " = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {

            ps.setInt(1, ID);

            int insertCount = ps.executeUpdate();

            if(insertCount == 1){
//                replace with a prompt
                System.out.println("Successful Deletion");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    public static void removeUser(int ID){
        String sqlPS = "DELETE from USERS where ID = ?";
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
        String sqlPS = "DELETE from DOCTORS where DoctorID = ?";
        if(getRole().equals("Admin")){
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
        if(getRole().equals("Admin")){
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
    public static void deleteAppointmentDB(int PatientID, LocalDateTime dateTime){
        String sqlPS = "DELETE from Appointments where PatientID = ? AND AppointmentTime = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setInt(1, PatientID);
            ps.setTimestamp(2, convertLDTToTimestamp(dateTime));
            int deleteCount = ps.executeUpdate();
            if(deleteCount == 1){
//                replace with prompt
                System.out.println("success");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    public static void updateDoctor(int DoctorID, String Specialization, int PhoneNumber,  String Email){
        if(getRole().equals("Admin")){
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
        if(getRole().equals("Admin")){
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
    public static void updatePatientDB(int PatientID, String Address, int Age,  String BloodGroup, Double Weight, Double Height){
        if(getRole().equals("Admin")){
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
    public static void addBill(int BillID, int PatientID, BigDecimal BillAmount, LocalDateTime BillDate,  Boolean BillPaid){
        String sqlPS = "INSERT INTO Bills(BillID, PatientID, BillAmount, BillDate, BillPaid) VALUES(?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setInt(1, BillID);
            ps.setInt(2, PatientID);
            ps.setBigDecimal(3, BillAmount);
            ps.setTimestamp(4, convertLDTToTimestamp(BillDate));
            ps.setBoolean(5, BillPaid);
            int insertCount = ps.executeUpdate();
            System.out.println(insertCount);
        }
        catch(SQLException sqle){
            System.err.println(sqle.getLocalizedMessage());
        }
    }
    public static void updateBillPaidState(int BillID, Boolean BillPaid, String ModeOfPayment){
        String sqlPS = "UPDATE Bills SET BillPaid = ? , ModeOfPayment = ? WHERE BillID = ? ";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setInt(1, BillID);
            ps.setBoolean(2, BillPaid);
            ps.setString(3, ModeOfPayment);
        }
        catch (SQLException sqle){
            System.err.println(sqle.getLocalizedMessage());

        }
    }
    public static String getNameDB(String table, int ID, String column){
        String name = "";
        String sqlPS = "Select Name from " + table + " WHERE " +  column + " = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setInt(1, ID);
            ResultSet resSet = ps.executeQuery();
            if(resSet.next()){
              name =  resSet.getString("Name");
            }
        }
        catch (SQLException sqle){
            System.err.println(sqle.getLocalizedMessage());

        }
        return name;
    }
    public static String getNewID(String table, String column){
        int newID = 0;
        String sqlPS = "SELECT MAX(  " + column + " ) from " + table;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ResultSet resSet = ps.executeQuery();
            if(!resSet.next()){
                newID = 1;
            }
            else{
                newID = resSet.getInt(1);
            }
        }
        catch (SQLException sqle){
            System.err.println(sqle.getLocalizedMessage());

        }
        return Integer.toString(newID + 1);
    }
    public static List<Map<String, String>> searchDoctorDB(String Name){
    List<Map<String, String>> matchedDoctors = new ArrayList<>();
        String sqlPS = "Select * from Doctors WHERE Name LIKE ? Order by Name" ;    // Using List<Map> instead of Map to preserve all records even if names are duplicate
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setString(1, "%" + Name + "%");
             ResultSet resultSet = ps.executeQuery();
             while(resultSet.next()){
                 // Create a map for each doctor's details
                 Map<String, String> doctorDetails = new HashMap<>();

                 // Store each field separately for easier access and display
                 doctorDetails.put("id", String.valueOf(resultSet.getInt("DoctorID")));
                 doctorDetails.put("name", resultSet.getString("Name"));
                 doctorDetails.put("specialization", resultSet.getString("Specialization"));
                 doctorDetails.put("phoneNo", String.valueOf(resultSet.getLong("PhoneNo")));
                 doctorDetails.put("email", resultSet.getString("Email"));

                 matchedDoctors.add(doctorDetails);
             }
        } catch (SQLException sqle) {
            System.err.println(sqle.getLocalizedMessage());
        }
        return matchedDoctors;
    }
    public static List<Map<String, String>> searchPatientDB(String Name) {
        List<Map<String, String>> matchedPatients = new ArrayList<>();
        String sqlPS = "Select * from Patients WHERE Name LIKE ? Order by Name";    // Using List<Map> instead of Map to preserve all records even if names are duplicate
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setString(1, "%" + Name + "%");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                // Create a map for each patient's details
                Map<String, String> patientDetails = new HashMap<>();

                // Store each field separately for easier access and display
                patientDetails.put("id", String.valueOf(resultSet.getInt("PatientID")));
                patientDetails.put("name", resultSet.getString("Name"));
                patientDetails.put("address", resultSet.getString("Address"));
                patientDetails.put("gender", resultSet.getString("Gender"));
                patientDetails.put("age", String.valueOf(resultSet.getInt("Age")));
                patientDetails.put("bloodgroup", resultSet.getString("BloodGroup"));
                patientDetails.put("weight",String.valueOf(resultSet.getDouble("Weight")));
                patientDetails.put("height",String.valueOf(resultSet.getDouble("Height")));

                matchedPatients.add(patientDetails);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getLocalizedMessage());
        }
        return matchedPatients;
    }
    public static List<Map<String, String>> searchBillDB(int ID) {
        List<Map<String, String>> matchedBills = new ArrayList<>();
        String sqlPS = "Select * from Bills WHERE PatientID = ? OR BillID = ?";    // Using List<Map> instead of Map to preserve all records even if names are duplicate
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setInt(1, ID);
            ps.setInt(2, ID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Create a map for each appointment's details
                Map<String, String> billDetails = new HashMap<>();

                // Store each field separately for easier access and display
                billDetails.put("patientID", String.valueOf(resultSet.getInt("PatientID")));
                billDetails.put("billID", String.valueOf(resultSet.getInt("BillID")));
                billDetails.put("amount", String.valueOf(resultSet.getBigDecimal("BillAmount")));
                billDetails.put("date", getDateFromLDT(resultSet.getTimestamp("BillDate").toLocalDateTime()));
                billDetails.put("modeOfPayment", resultSet.getString("ModeOfPayment"));
                billDetails.put("paidState", String.valueOf(resultSet.getBoolean("BillPaid")));

               matchedBills.add(billDetails);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getLocalizedMessage());
        }
        return matchedBills;
    }
    public static List<Map<String, String>> searchAppointmentDB(int ID) {
        List<Map<String, String>> matchedAppointments = new ArrayList<>();
        String sqlPS = "Select * from Appointments WHERE PatientID = ? OR DoctorID = ?";    // Using List<Map> instead of Map to preserve all records even if names are duplicate
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlPS)) {
            ps.setInt(1, ID);
            ps.setInt(2, ID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Create a map for each appointment's details
                Map<String, String> appointmentDetails = new HashMap<>();

                // Store each field separately for easier access and display
                appointmentDetails.put("patientID", String.valueOf(resultSet.getInt("PatientID")));
                appointmentDetails.put("doctorID", String.valueOf(resultSet.getInt("DoctorID")));
                appointmentDetails.put("specialization", resultSet.getString("Specialization"));
                appointmentDetails.put("time", getTimeFromLDT(resultSet.getTimestamp("AppointmentTime").toLocalDateTime()));
                appointmentDetails.put("date", getDateFromLDT(resultSet.getTimestamp("AppointmentTime").toLocalDateTime()));
                appointmentDetails.put("reason", resultSet.getString("Reason"));

                matchedAppointments.add(appointmentDetails);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getLocalizedMessage());
        }
        return matchedAppointments;
    }
    private static DataSource createDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=MedicalJavaDb");
        ds.setUsername("sa");
        ds.setPassword("31F0rtkn0cks12");
        ds.addDataSourceProperty("trustServerCertificate", "true");
        ds.addDataSourceProperty("encrypt", "true");
        return ds;
    }
}
