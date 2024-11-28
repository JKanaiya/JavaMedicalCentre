package src.com.medicalproject;

import javax.naming.NoPermissionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static src.com.medicalproject.DBQueries.dataSource;

public class TimeControl {

    public static LocalDateTime convertToLocalDateTime(String time, String date){
        LocalTime formatTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate formatDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Combine LocalDate and LocalTime into LocalDateTime
        return  LocalDateTime.of(formatDate, formatTime);
    }

    ArrayList<Integer> arrL = new ArrayList<>();

    public static Timestamp convertLDTToTimestamp(LocalDateTime dateTime){
        return Timestamp.valueOf(dateTime);
    }

//    change this to return the collection that makes the most sense
    public void getSpecializedList(String Specialization, LocalDateTime dateTime){
        try(Connection conn = dataSource.getConnection()){
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT DoctorID FROM Doctors WHERE Specialization = ? " +
                    "AND DoctorID NOT IN (" +
                    "SELECT DoctorID FROM Appointments WHERE AppointmentTime = ?");
            ps.setString(1, Specialization);
            ps.setTimestamp(2, convertLDTToTimestamp(dateTime));
            ResultSet res = ps.executeQuery();
            while(res.next()){
                arrL.add(res.getInt("DoctorID"));
            }
            checkBufferedAvailability(dateTime);
        }
        catch (NullPointerException npe ){
            System.err.println("Null pointer err: " +  npe.getLocalizedMessage());
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getLocalizedMessage());
        }
    }
    public void checkBufferedAvailability(LocalDateTime dateTime){
//        if(dateTime.isAfter() && dateTime.isBefore()){
//
//        }
        for(int ID : arrL){
            System.out.println(ID);
        }
    }
}
