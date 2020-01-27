package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("TaskController/")
public class TaskController {


    //CREATE TASK FUNCTION ----------------------------COMPLETE---------------------------------------------------------
    @POST
    @Path("newTask/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTask(@FormDataParam("taskName")String taskName) {
        try {

            int TaskID;

            PreparedStatement ps = Main.db.prepareStatement("SELECT MAX(TaskID) FROM Tasks");
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
    @GET
    @Path("readTasks/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readTasks() {
        JSONArray list = new JSONArray();
        try {

            //These are the fields which will be retrieved and outputted:
            PreparedStatement ps = Main.db.prepareStatement("SELECT TaskID, TaskName FROM Tasks");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("TaskID", results.getInt(1));
                item.put("TaskName", results.getString(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }


    //UPDATE TASK FUNCTION -----------------------------INCOMPLETE/UNTESTED--------------------------------------------------------
    @POST
    @Path("editTask/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editTask(@FormDataParam("taskName")String taskName, @FormDataParam("newName")String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Tasks SET TaskName = ? WHERE TaskName = ?");
            ps.setString(1,newName);
            ps.setString(2,taskName);

            ps.executeUpdate();

        } catch (Exception exception) {
            return("Error when editing task name " + exception.getMessage());
        }
        return ("Edit complete");
    }


    //DESTROY TASK FUNCTION ----------------------------COMPLETE--------------------------------------------------------
    @POST
    @Path("delTask/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delTask(@FormDataParam("taskName") String taskName) {
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
