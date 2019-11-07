package Controllers;

import Server.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TaskController {

    public static Connection db = null;

    //ADD TASK FUNCTION
    public static void newTask() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            System.out.println("Enter task name");

            PreparedStatement ps = db.prepareStatement("SELECT ListID, Username, Password FROM UserData");


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
            System.out.println("Error adding new task");
        }
    }


    //REMOVE TASK FUNCTION
    public static void delTask() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when deleting task");
        }
    }


    //EDIT TASK FUNCTION
    public static void editTask() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when editing task");
        }
    }
}
