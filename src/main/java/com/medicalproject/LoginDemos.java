package com.medicalproject;

public class LoginDemos {
    public static void main(String[] args) {
//        LoginInstance li = new LoginInstance();
//        li.attemptLogin();
        Appointments ap =  new Appointments();
        ap.createAppointment("19:00","27/02/2026", "Orthopedics");
    }
}
