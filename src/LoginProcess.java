package src;

import java.io.PrintStream;
import java.util.Scanner;

import static src.DBQUeries.validateUser;
import static src.com.medicalproject.Classes.UserAuth.hashPassword;

public class LoginProcess {
    public static void main(String[] args) {
        try(Scanner scan = new Scanner(System.in)){
            System.out.println("Please enter your Login ID and Password");
            int ID = scan.nextInt();
            String password = scan.nextLine();
            password = hashPassword(password);
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
