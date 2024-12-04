package com.medicalproject;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeControl {

    public LocalDateTime convertSimpleToLocalDateTime(String time, String date) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parse date and time inputs
        LocalTime formatTime = LocalTime.parse(time, timeFormatter);
        LocalDate formatDate = LocalDate.parse(date, dateFormatter);

        // Combine LocalDate and LocalTime into LocalDateTime
        return  LocalDateTime.of(formatDate, formatTime);
    }

    public static LocalDateTime convertToLocalDateTime(String time, String date){
        LocalTime formatTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate formatDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Combine LocalDate and LocalTime into LocalDateTime
        return LocalDateTime.of(formatDate, formatTime);
    }

    public static Timestamp convertLDTToTimestamp(LocalDateTime dateTime){
        return Timestamp.valueOf(dateTime);
    }
}
