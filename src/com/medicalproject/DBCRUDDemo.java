package src.com.medicalproject;

import static src.com.medicalproject.DBQueries.registerDoctor;

public class DBCRUDDemo {
    public static void main(String[] args) {
//        registerDoctor(567, "pass3", "Time Travel", "Doctor Who", 1234567, "theDoctor@gmail.com");
        TimeControl tc = new TimeControl();
        tc.getSpecializedList("Orthopedics", TimeControl.convertToLocalDateTime("14:00", "24/02/2022"));
    }
}
