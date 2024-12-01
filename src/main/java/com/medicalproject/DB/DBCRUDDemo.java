package com.medicalproject.DB;

import com.medicalproject.TimeControl;

public class DBCRUDDemo {
    public static void main(String[] args) {
//        registerDoctor(567, "pass3", "Time Travel", "Doctor Who", 1234567, "theDoctor@gmail.com");
        System.out.println((DBCRUD.getSpecializedMap("Orthopedics", TimeControl.convertToLocalDateTime("14:00", "24/02/2022"))));
    }
}
