package Controllers;

import Server.Main;
import jdk.jfr.Category;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

@Path("ScheduleController/")
public class ScheduleController {

    public static Connection db = null;

    //ADD SCHEDULE FUNCTION -------------------------------NOT TESTED--------------------------------------------
    @POST
    @Path("newSchedule/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newSchedule(@FormDataParam("scheduleName") String scheduleName, @FormDataParam("categoryID") Integer categoryID) {
        try {

            int scheduleID = 0;

            PreparedStatement ps = db.prepareStatement("SELECT MAX(ScheduleID) FROM Schedules");

            ResultSet results = ps .executeQuery();

            //This is the statement that will be sent into the table
            ps = db.prepareStatement("INSERT INTO Schedules (ScheduleID, ScheduleName, CategoryID) VALUES (?, ?, ?)");

            int newID = scheduleID + 1;

            ps.setInt(1, newID);
            ps.setString(2, scheduleName);
            ps.setInt(3, categoryID);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            return("Error adding new schedule " + exception.getMessage());
        }
        return ("New schedule created");
    }


    //READ FROM SCHEDULES -------------------------------NOT TESTED-----------------------------------------------------
    @GET
    @Path("readSchedules/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readSchedules() {

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
            return("Error when reading schedules " + exception.getMessage());
        }
        return("Schedules read");
    }


    //UPDATE SCHEDULE FUNCTION ---------------------------NOT TESTED---------------------------------------------------
    @POST
    @Path("editSchedule/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editSchedule(@FormDataParam("newName") String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Schedules SET ScheduleName = ?, ");
            ps.setString(1,newName);

            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error changing schedule name " + exception.getMessage());
        }
        return ("Changes saved");
    }


    //DESTROY SCHEDULE FUNCTION ----------------------------NOT TESTED-------------------------------------------------
    @POST
    @Path("delSchedule/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delSchedule(@FormDataParam("scheduleName")String scheduleName) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Schedules WHERE ScheduleName = ?");
            ps.setString(1, scheduleName);
            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error removing schedule: "+ exception.getMessage());
        }
        return("Schedule deleted");
    }

}
