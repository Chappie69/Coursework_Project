package Controllers;

import Server.Main;
import java.sql.*;
import java.util.Scanner;


public class UserDataController {

    public static Connection db = null;

    //CREATE NEW USER FUNCTION
    public static void newUser() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            int userID = 0;
            String user;
            //Will continue while true
            Boolean go = true;

            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password FROM UserData");


            ResultSet results = ps.executeQuery();
            while (results.next()) {
                userID = results.getInt(1);
                String username = results.getString(2);
                String password = results.getString(3);
                System.out.println(userID + " " + username + " " + password);
            }

            //This is the statement that will be sent into the table
            ps = db.prepareStatement("INSERT INTO UserData (UserId, Username, Password) VALUES (?, ?, ?)");

            int newID = userID + 1;
            String inpUser = in.nextLine();
            String inpPass = in.nextLine();

            ps.setInt(1, newID);
            ps.setString(2, inpUser);
            ps.setString(3, inpPass);

            //Executes prepared statement
            ps.executeUpdate();
            //Close database
            Main.closeDatabase();

        } catch (Exception exception) {
            System.out.println("Error adding new user");
        }
    }


    //READ FROM USERS
    public static void readUsers() {

        Main.openDatabase("proj_database.db");

        try {

            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username FROM Users");
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

    //EDIT EXISTING USER --------------------------THIS IS NOT DONE YET ---------------------------------------
    public static void editUser() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            int userID = 0;
            String user;
            Boolean go = true;

            user = in.nextLine();
            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password FROM UserData WHERE Username = ?");
            ps.setString(1,user);

            System.out.println("User " +user+ " selected, what would you like to change?");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                userID = results.getInt(1);
                String username = results.getString(2);
                String password = results.getString(3);
                System.out.println(userID + " " + username + " " + password);
            }

            ps = db.prepareStatement("DELETE FROM UserData (UserId, Username, Password) VALUES (?, ?, ?)");

            int newID = userID + 1;
            String inpUser = in.nextLine();
            String inpPass = in.nextLine();

            ps.setInt(1, newID);
            ps.setString(2, inpUser);
            ps.setString(3, inpPass);

            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new user");
        }

        Main.closeDatabase();
    }

    //DELETE EXISTING USER FUNCTION
    public static void delUser() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            int userID = 0;
            String user;
            Boolean go = true;

            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password FROM UserData");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                userID = results.getInt(1);
                String username = results.getString(2);
                String password = results.getString(3);
                System.out.println(userID + " " + username + " " + password);
            }

            ps = db.prepareStatement("DELETE FROM UserData (UserId, Username, Password) VALUES (?, ?, ?)");

            int newID = userID + 1;
            String inpUser = in.nextLine();
            String inpPass = in.nextLine();

            ps.setInt(1, newID);
            ps.setString(2, inpUser);
            ps.setString(3, inpPass);

            ps.executeUpdate();
            Main.closeDatabase();

        } catch (Exception exception) {
            System.out.println("Error adding new user: "+ exception.getMessage());
        }
    }
}
