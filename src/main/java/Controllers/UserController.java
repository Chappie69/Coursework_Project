package Controllers;

import Server.Main;
import java.sql.*;


public class UserController {

    //CREATE NEW USER FUNCTION ------------------------------(in)COMPLETE-----------------------------------------------
    public static void newUser(String username, String password) {
        try {

            int userID;
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password FROM Users");

            ResultSet results = ps.executeQuery();
            userID = results.getInt(1);

            //This is the statement that will be sent into the table
            ps = Main.db.prepareStatement("INSERT INTO Users (UserID, Username, Password) VALUES (?, ?, ?)");

            int newID = userID + 1;

            ps.setInt(1, newID);
            ps.setString(2, username);
            ps.setString(3, password);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new user: " + exception.getMessage());
        }
    }


    //READ FROM USERS --------------------------------------------------------------------------------------------------
    public static void readUsers() {

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
    }

    //EDIT EXISTING USER -------------------------- Not tested -----------------------------------------------
    public static void editUser(String username, String password) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Password = ?, ");
            ps.setString(1,password);

            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error changing password " + exception.getMessage());
        }

    }

    //DELETE EXISTING USER FUNCTION ------------------------------------------------------------------------------------
    public static void delUser(String username) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
            ps.setString(1, username);
            ps.executeUpdate();


        } catch (Exception exception) {
            System.out.println("Error removing user: "+ exception.getMessage());
        }
    }
}
