package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("EventController/")
public class EventController {

    //CREATE EVENT FUNCTION ----------------------------NOT TESTED-----------------------------------------------------
    @GET
    @Path("newEvent/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newEvent(String eventName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT EventID, EventName FROM Events");

            int eventID;
            String EventName;

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                eventID = results.getInt(1);
                EventName = results.getString(2);
                System.out.println(eventID + " " + EventName);
            }

            ps = Main.db.prepareStatement("SELECT EventID FROM Events");
            results = ps.executeQuery();
            eventID = results.getInt(1);

            int newID = eventID + 1;
            String eventTime = "00:00";
            Boolean Reminder = false;


            //This is the statement that will be sent into the table ---------------------------------------------------
            ps = Main.db.prepareStatement("INSERT INTO Events (EventID, EventName, EventTime, Reminder) VALUES (?, ?, ?, ?, ?)");

            ps.setInt(1, newID);
            ps.setString(2, eventName);
            ps.setString(3, eventTime);
            ps.setBoolean(4, Reminder);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error adding new task " + exception.getMessage());
        }
        return ("Event added");
    }


    //READ FROM EVENTS -----------------------------------NOT TESTED------------------------------------------------------
    @POST
    @Path("readEvents/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String readEvents() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT EventID, EventName FROM Events");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int EventID = results.getInt(1);
                String EventName = results.getString(2);
                System.out.println(EventID + " " + EventName);
            }

        } catch (Exception exception) {
            return ("Error when reading from events " + exception.getMessage());
        }
        return("Events read from");
    }


    //UPDATE EXISTING EVENT -------------------------- Not tested or correct? -----------------------------------------------
    @GET
    @Path("editEvent/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editEvent(String eventName, String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Events SET EventName = ?, WHERE EventName = ?");
            ps.setString(1,newName);
            ps.setString(2,eventName);

            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error changing event name " + exception.getMessage());
        }
        return ("Event edited");
    }


    //DESTROY EVENT FUNCTION ----------------------------Not tested--------------------------------------------------------
    @GET
    @Path("delEvent/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delEvent(String eventName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Events WHERE EventName = ?");
            ps.setString(1, eventName);
            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error removing event: "+ exception.getMessage());
        }
        return ("Event deleted");
    }
}
