package com.medicalproject;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeControl {

    public static LocalDateTime convertToLocalDateTime(String time, String date){
        LocalTime formatTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate formatDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Combine LocalDate and LocalTime into LocalDateTime
        return LocalDateTime.of(formatDate, formatTime);
    }

    public static String getTimeFromLDT(LocalDateTime ldt){
        DateTimeFormatter timeFormat  = DateTimeFormatter.ofPattern("HH:mm");
        return timeFormat.format(ldt);
    }

    public static String getDateFromLDT(LocalDateTime ldt){
        DateTimeFormatter dateFormat  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateFormat.format(ldt);
    }

    public static Timestamp convertLDTToTimestamp(LocalDateTime dateTime){
        return Timestamp.valueOf(dateTime);
    }
}
