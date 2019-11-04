package Controllers;

import Server.Main;
import java.sql.Connection;
import java.util.Scanner;

public class ToDoLists {

    public static Connection db = null;

    //ADD TASK FUNCTION
    public static void newTask() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            System.out.println("Enter task name");


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
