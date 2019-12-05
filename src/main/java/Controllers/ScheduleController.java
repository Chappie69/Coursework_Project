package Controllers;

import Server.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class ScheduleController {

    public static Connection db = null;

    //ADD SCHEDULE FUNCTION -------------------------------------------------------------------------------------------
    public static void newSchedule(String scheduleName) {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            int scheduleID = 0;

            PreparedStatement ps = db.prepareStatement("SELECT ScheduleID, ScheduleName, TableID, CategoryID FROM Schedules");

            ResultSet results = ps .executeQuery();
            while (results.next()) {
                scheduleID = results.getInt(1);
                String ScheduleName = results.getString(2);
                String tableID = results.getString(3);
                int categoryID = results.getInt(4);
                System.out.println(scheduleID + " " + ScheduleName + " " + tableID + " " + categoryID);
            }

            //This is the statement that will be sent into the table
            ps = db.prepareStatement("INSERT INTO Schedules (ScheduleID, ScheduleName, CreatorID, TableID, CategoryID) VALUES (?, ?, ?, ?, ?)");

            int newID = scheduleID + 1;
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
            System.out.println("Error adding new schedule" + exception.getMessage());
        }
    }


    //REMOVE SCHEDULE FUNCTION ----------------------------------------------------------------------------------------
    public static void delSchedule() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when deleting schedule" + exception.getMessage());
        }
    }


    //EDIT SCHEDULE FUNCTION ------------------------------------------------------------------------------------------
    public static void editSchedule() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when editing schedule"+ exception.getMessage());
        }
    }
}
