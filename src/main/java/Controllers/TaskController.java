package Controllers;

import Server.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TaskController {


    //CREATE TASK FUNCTION ----------------------------COMPLETE----------------------------------------------------
    public static void newTask(String taskName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT TaskID, TaskName FROM Tasks");

            int TaskID;
            String TaskName;

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                TaskID = results.getInt(1);
                TaskName = results.getString(2);
                System.out.println(TaskID + " " + TaskName);
            }

            ps = Main.db.prepareStatement("SELECT TaskID FROM Tasks");
            results = ps.executeQuery();
            TaskID = results.getInt(1);

            int newID = TaskID + 1;
            String inpTaskDue = "None";
            Boolean inpTaskDone = false;
            int inpPriority = 0;


            //This is the statement that will be sent into the table ---------------------------------------------------
            ps = Main.db.prepareStatement("INSERT INTO Tasks (TaskID, TaskName, TaskDue, TaskDone, PriorityID) VALUES (?, ?, ?, ?, ?)");

            ps.setInt(1, newID);
            ps.setString(2, taskName);
            ps.setString(3, inpTaskDue);
            ps.setBoolean(4, inpTaskDone);
            ps.setInt(5, inpPriority);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new task " + exception.getMessage());
        }
    }

    //READ FROM TASKS ------------------------------COMPLETE----------------------------------------------------
    public static void readTasks() {

        try {

            //These are the fields which will be retrieved and outputted:
            PreparedStatement ps = Main.db.prepareStatement("SELECT TaskID, TaskName FROM Tasks");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int TaskID = results.getInt(1);
                String TaskName = results.getString(2);
                System.out.println(TaskID + " " + TaskName);
            }

        } catch (Exception exception) {
            System.out.println("Error reading tasks " + exception.getMessage());
        }

    }


    //UPDATE TASK FUNCTION -----------------------------INCOMPLETE/UNTESTED--------------------------------------------------------
    public static void editTask(String TaskName, String NewName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Tasks SET TaskName = ? WHERE TaskName = ?");
            ps.setString(1,NewName);
            ps.setString(2,TaskName);

            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error when editing task name " + exception.getMessage());
        }
    }


    //DESTROY TASK FUNCTION ----------------------------COMPLETE--------------------------------------------------------
    public static void delTask(String taskName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tasks WHERE TaskName = ?");
            ps.setString(1, taskName);
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error removing task: "+ exception.getMessage());
        }
    }
}
