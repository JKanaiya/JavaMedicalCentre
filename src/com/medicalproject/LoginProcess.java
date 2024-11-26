package src.com.medicalproject;

import java.util.Scanner;

import static src.com.medicalproject.DBQUeries.validateUser;


public class LoginProcess {
    public void attemptLogin(){
        try(Scanner scan = new Scanner(System.in)){
            System.out.println("Please enter your Login ID and Password");
            int ID = scan.nextInt();
            String password = scan.nextLine();
            password = String.valueOf(password.hashCode());
            if(validateUser(ID, password)){
                System.out.println("Login Successful");
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

