package src.com.medicalproject;

public class LoginDemos {
    public static void main(String[] args) {
//        LoginInstance li = new LoginInstance();
//        li.attemptLogin();
        Appointments ap =  new Appointments();
        ap.createAppointment("17:00","23/02/2026", "Orthopedics");
    }
}
