package Controllers;

import Server.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TaskController {

    public static Connection db = null;


    //CREATE TASK FUNCTION ----------------------------NOT TESTED----------------------------------------------------
    public static void newTask(String taskName) {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

            PreparedStatement ps = db.prepareStatement("SELECT TaskID, TaskName FROM Tasks");

            int TaskID;
            String TaskName;

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                TaskID = results.getInt(1);
                TaskName = results.getString(2);
                System.out.println(TaskID + " " + TaskName);
            }

            ps = db.prepareStatement("SELECT TaskID FROM Tasks");
            results = ps.executeQuery();
            TaskID = results.getInt(1);

            //This is the statement that will be sent into the table ---------------------------------------------------
            ps = db.prepareStatement("INSERT INTO Tasks (TaskID, TaskName, TaskDue, TaskDone, PriorityID) VALUES (?, ?, ?, ?, ?)");

            int newID = TaskID + 1;
            String inpTaskName = taskName;
            String inpTaskDue = "None";
            Boolean inpTaskDone = false;
            int inpPriority = 0;

            ps.setInt(1, newID);
            ps.setString(2, inpTaskName);
            ps.setString(3, inpTaskDue);
            ps.setBoolean(4, inpTaskDone);
            ps.setInt(5, inpPriority);

            //Executes prepared statement
            ps.executeUpdate();
            //Close database
            Main.closeDatabase();

        } catch (Exception exception) {
            System.out.println("Error adding new task" + exception.getMessage());
        }
    }

    //READ FROM TASKS ------------------------------INCOMPLETE----------------------------------------------------
    public static void readTasks() {

        Main.openDatabase("proj_database.db");

        try {

            //These are the fields which will be retrieved and outputted:
            PreparedStatement ps = db.prepareStatement("SELECT TaskID, TaskName FROM Tasks");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int TaskID = results.getInt(1);
                String TaskName = results.getString(2);
                System.out.println(TaskID + " " + TaskName);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

        Main.closeDatabase();
    }


    //UPDATE TASK FUNCTION -----------------------------INCOMPLETE--------------------------------------------------------
    public static void editTask() {
        try {

            Scanner in = new Scanner(System.in);
            Main.openDatabase("proj_database.db");

        } catch (Exception exception) {
            System.out.println("Error when editing task");
        }
    }


    //DESTROY TASK FUNCTION ----------------------------COMPLETE--------------------------------------------------------
    public static void delTask(String taskName) {
        try {
            Main.openDatabase("proj_database.db");

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tasks WHERE TaskName = ?");
            ps.setString(1, taskName);
            ps.executeUpdate();

            Main.closeDatabase();

        } catch (Exception exception) {
            System.out.println("Error removing task: "+ exception.getMessage());
        }
    }
}
