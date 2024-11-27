package src.com.medicalproject;
import java.util.Scanner;
import static src.com.medicalproject.DBQueries.validateUser;

public class LoginInstance {

    public static String role;
    public int ID;

    public LoginInstance() {
    }

    public int getID() {
        return ID;
    }

    public String getRole() {
        return role;
    }

    public static void setRole(String role) {
        LoginInstance.role = role;
    }

    public void attemptLogin(){
        try(Scanner scan = new Scanner(System.in)){
            System.out.println("Please enter your Login ID and Password");
            int ID = scan.nextInt();
            String password = scan.nextLine();
            password = String.valueOf(password.hashCode());
            if(validateUser(ID, password)){
                System.out.println("Login Successful.\nWelcome " + getRole());
            }
            else{
                System.out.println("Invalid Login");
            }
        }
        catch (NumberFormatException nfe){
            System.err.println("Invalid input format, please input a valid id");
        }
    }
}
