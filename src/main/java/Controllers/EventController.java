package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("EventController/")
public class EventController {

    //CREATE EVENT FUNCTION ----------------------------NOT TESTED-----------------------------------------------------
    @POST
    @Path("newEvent/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newEvent(@FormDataParam("eventName")String eventName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT MAX(EventID) FROM Events");

            int eventID;

            ResultSet results = ps.executeQuery();
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
    @GET
    @Path("readEvents/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readEvents() {
        JSONArray list = new JSONArray();
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT EventID, EventName FROM Events");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("EventID", results.getInt(1));
                item.put("EventName", results.getString(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }


    //UPDATE EXISTING EVENT -------------------------- Not tested or correct? -----------------------------------------------
    @POST
    @Path("editEvent/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editEvent(@FormDataParam("eventName")String eventName,@FormDataParam("newName") String newName) {
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
    @POST
    @Path("delEvent/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delEvent(@FormDataParam("eventName")String eventName) {
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
