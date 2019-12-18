package Controllers;

import Server.Main;
import jdk.jfr.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class ScheduleController {

    public static Connection db = null;

    //ADD SCHEDULE FUNCTION -------------------------------NOT TESTED--------------------------------------------
    public static void newSchedule(String ScheduleName, Integer CategoryID) {
        try {

            int scheduleID = 0;

            PreparedStatement ps = db.prepareStatement("SELECT ScheduleID, ScheduleName, CategoryID FROM Schedules");

            ResultSet results = ps .executeQuery();
            while (results.next()) {
                scheduleID = results.getInt(1);
                String scheduleName = results.getString(2);
                int categoryID = results.getInt(3);
                System.out.println(scheduleID + " " + scheduleName + " " + categoryID);
            }

            //This is the statement that will be sent into the table
            ps = db.prepareStatement("INSERT INTO Schedules (ScheduleID, ScheduleName, CategoryID) VALUES (?, ?, ?)");

            int newID = scheduleID + 1;

            ps.setInt(1, newID);
            ps.setString(2, ScheduleName);
            ps.setInt(3, CategoryID);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new schedule " + exception.getMessage());
        }
    }


    //READ FROM SCHEDULES -------------------------------NOT TESTED-----------------------------------------------------
    public static void readSchedules() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT ScheduleID, ScheduleName FROM Schedules");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int ScheduleID = results.getInt(1);
                String ScheduleName = results.getString(2);
                System.out.println(ScheduleID + " " + ScheduleName);
            }

        } catch (Exception exception) {
            System.out.println("Error when reading schedules " + exception.getMessage());
        }
    }


    //UPDATE SCHEDULE FUNCTION ---------------------------NOT TESTED---------------------------------------------------
    public static void editSchedule(String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Schedules SET ScheduleName = ?, ");
            ps.setString(1,newName);

            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error changing schedule name " + exception.getMessage());
        }
    }


    //DESTROY SCHEDULE FUNCTION ----------------------------NOT TESTED-------------------------------------------------
    public static void delSchedule(String scheduleName) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Schedules WHERE ScheduleName = ?");
            ps.setString(1, scheduleName);
            ps.executeUpdate();


        } catch (Exception exception) {
            System.out.println("Error removing schedule: "+ exception.getMessage());
        }
    }

}
