package Controllers;

import Server.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TimetableController {

    public static Connection db = null;

    //ADD TIMETABLE FUNCTION ----------------------------------NOT TESTED-----------------------------------------------
    public static void newTable(String tableName) {
        try {

            int tableID;
            PreparedStatement ps = Main.db.prepareStatement("SELECT TableID, FROM Tables");

            ResultSet results = ps.executeQuery();
            tableID = results.getInt(1);

            //This is the statement that will be sent into the table
            ps = Main.db.prepareStatement("INSERT INTO Tables (TableID, TableName) VALUES (?, ?)");

            int newID = tableID + 1;

            ps.setInt(1, newID);
            ps.setString(2, tableName);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new Timetable: " + exception.getMessage());
        }
    }


    //READ FROM TIMETABLES ----------------------------------NOT TESTED-------------------------------------------------
    public static void readTables() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT TableID, TableName FROM Timetables");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int tableID = results.getInt(1);
                String tableName = results.getString(2);
                System.out.println(tableID + " " + tableName);
            }

        } catch (Exception exception) {
            System.out.println("Error reading tables " + exception.getMessage());
        }
    }


    //EDIT TIMETABLE FUNCTION ----------------------------------NOT TESTED----------------------------------------------
    public static void editTable(String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Timetables SET TableName = ?, ");
            ps.setString(1,newName);

            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error changing timetable name " + exception.getMessage());
        }
    }


    //REMOVE TIMETABLE FUNCTION ----------------------------------NOT TESTED--------------------------------------------
    public static void delTable(String tableName) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Timetables WHERE TableName = ?");
            ps.setString(1, tableName);
            ps.executeUpdate();


        } catch (Exception exception) {
            System.out.println("Error removing timetable "+ exception.getMessage());
        }
    }
}