package Controllers;

import Server.Main;
import java.sql.*;
import java.util.Scanner;


public class UsersController {

    //CREATE NEW USER FUNCTION ------------------------------(in)COMPLETE---------------------------------------------------
    public static void newUser() {
        try {

            int userID = 0;
            String username;
            String password;
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                userID = results.getInt(1);
                username = results.getString(2);
                password = results.getString(3);
                System.out.println(userID + " " + username + " " + password);
            }

            //This is the statement that will be sent into the table
            ps = Main.db.prepareStatement("INSERT INTO Users (UserID, Username, Password) VALUES (?, ?, ?)");

            int newID = userID + 1;
            String inpUser = "mmmmyyeyeeeee";
            String inpPass = "jjjjjj";

            ps.setInt(1, newID);
            ps.setString(2, inpUser);
            ps.setString(3, inpPass);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new user: " + exception.getMessage());
        }
    }


    //READ FROM USERS -------------------------------------------------------------------------------------------------
    public static void readUsers() {

        Main.openDatabase("proj_database.db");

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username FROM Users");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int userID = results.getInt(1);
                String username = results.getString(2);
                System.out.println(userID + " " + username);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
        Main.closeDatabase();
    }

    //EDIT EXISTING USER -------------------------- THIS IS NOT DONE YET ----------------------------------------------
    public static void editUser() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            int userID = 0;
            String user;
            Boolean go = true;

            user = in.nextLine();
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password FROM Users WHERE Username = ?");
            ps.setString(1,user);

            System.out.println("User " +user+ " selected, what would you like to change?");

            ResultSet results = ps.executeQuery();
            userID = results.getInt(1);
            String username = results.getString(2);
            String password = results.getString(3);
            System.out.println(userID + " " + username);

            ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserID = ?");

            int newID = userID + 1;
            String inpUser = in.nextLine();
            String inpPass = in.nextLine();

            ps.setInt(1, newID);


            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new user");
        }

    }

    //DELETE EXISTING USER FUNCTION -----------------------------------------------------------------------------------
    public static void delUser(String username) {
        try {
            Main.openDatabase("proj_database.db");

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
            ps.setString(1, username);
            ps.executeUpdate();

            Main.closeDatabase();

        } catch (Exception exception) {
            System.out.println("Error adding new user: "+ exception.getMessage());
        }
    }
}
