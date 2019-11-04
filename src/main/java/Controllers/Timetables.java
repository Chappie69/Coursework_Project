package Controllers;

import Server.Main;
import java.sql.Connection;
import java.util.Scanner;

public class Timetables {

    public static Connection db = null;

    //ADD TIMETABLE FUNCTION
    public static void newTable() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            System.out.println("Enter timetable name");

    //CATCH ERRORS
        } catch (Exception exception) {
            System.out.println("Error adding new timetable");
        }
    }


    //REMOVE TIMETABLE FUNCTION
    public static void delTable() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            System.out.println("Enter timetable name which you wish to delete");

    //CATCH ERRORS
        } catch (Exception exception) {
            System.out.println("Error when deleting timetable");
        }
    }


    //EDIT TIMETABLE FUNCTION
    public static void editTable() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            System.out.println("Enter timetable name to edit");

    //CATCH ERRORS
        } catch (Exception exception) {
            System.out.println("Error when editing timetable");
        }
    }
}