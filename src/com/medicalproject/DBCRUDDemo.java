package src.com.medicalproject;

import static src.com.medicalproject.DBQueries.registerDoctor;

public class DBCRUDDemo {
    public static void main(String[] args) {
        registerDoctor(567, "pass3", "Time Travel", "Doctor Who", 1234567, "theDoctor@gmail.com");
    }
}
