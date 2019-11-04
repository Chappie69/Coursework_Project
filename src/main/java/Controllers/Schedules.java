package Controllers;

import Server.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class Schedules {

    public static Connection db = null;

    //ADD SCHEDULE FUNCTION
    public static void newSchedule() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");
            String user;

            System.out.println("Enter the name you would like to call your Schedule");
            user = in.nextLine();

            PreparedStatement ps = db.prepareStatement("SELECT ScheduleID FROM Schedules");


            ps = db.prepareStatement("INESRT INTO Schedules (ScheduleID, ScheduleName, ScheduleCreator, CategoryID) FROM UserData");

        } catch (Exception exception) {
            System.out.println("Error adding new schedule");
        }
    }


    //REMOVE SCHEDULE FUNCTION
    public static void delSchedule() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when deleting schedule");
        }
    }


    //EDIT SCHEDULE FUNCTION
    public static void editSchedule() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when editing schedule");
        }
    }
}
