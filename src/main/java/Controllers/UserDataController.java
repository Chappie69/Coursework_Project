package Controllers;

import Server.Main;
import java.sql.*;
import java.util.Scanner;

public class UserDataController {

    public static Connection db = null;

    //ADD NEW USER FUNCTION
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

            //This is the statement that will me sent into the table
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

    //EDIT EXISTING USER
    public static void editUser() {
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
            System.out.println("Error adding new user");
        }
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
            System.out.println("Error adding new user");
        }
    }
}