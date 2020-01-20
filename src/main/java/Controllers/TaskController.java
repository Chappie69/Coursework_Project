package Controllers;

import Server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("TaskController/")
public class TaskController {


    //CREATE TASK FUNCTION ----------------------------COMPLETE--------------------------------------------------------
    @GET
    @Path("newTask/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTask(String taskName) {
        try {

            int TaskID;

            PreparedStatement ps = Main.db.prepareStatement("SELECT TaskID FROM Tasks");
            ResultSet results = ps.executeQuery();
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
            return("Error adding new task " + exception.getMessage());
        }
        return ("Add new task complete");
    }

    //READ FROM TASKS ------------------------------COMPLETE----------------------------------------------------
    @POST
    @Path("readTasks/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String readTasks() {

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
            return("Error reading tasks " + exception.getMessage());
        }
        return ("Read complete");
    }


    //UPDATE TASK FUNCTION -----------------------------INCOMPLETE/UNTESTED--------------------------------------------------------
    @GET
    @Path("editTask/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editTask(String TaskName, String NewName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Tasks SET TaskName = ? WHERE TaskName = ?");
            ps.setString(1,NewName);
            ps.setString(2,TaskName);

            ps.executeUpdate();

        } catch (Exception exception) {
            return("Error when editing task name " + exception.getMessage());
        }
        return ("Edit complete");
    }


    //DESTROY TASK FUNCTION ----------------------------COMPLETE--------------------------------------------------------
    @GET
    @Path("delTask/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delTask(String taskName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tasks WHERE TaskName = ?");
            ps.setString(1, taskName);
            ps.executeUpdate();

        } catch (Exception exception) {
            return("Error removing task: "+ exception.getMessage());
        }
        return ("Delete complete");
    }
}
