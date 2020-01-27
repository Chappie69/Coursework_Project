package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

@Path("TimetableController/")
public class TimetableController {

    //ADD TIMETABLE FUNCTION ----------------------------------NOT TESTED------------------------------------------------
    @POST
    @Path("newTable/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTable(@FormDataParam("tableName")String tableName) {
        try {

            int tableID;
            PreparedStatement ps = Main.db.prepareStatement("SELECT MAX(TableID), FROM Tables");

            ResultSet results = ps.executeQuery();
            tableID = results.getInt(1);

            //This is the statement that will be sent into the table
            ps = Main.db.prepareStatement("INSERT INTO Tables (TableID, TableName) VALUES (?, ?)");

            int newID = tableID + 1;

            ps.setInt(1, newID);
            ps.setString(2, tableName);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error adding new Timetable: " + exception.getMessage());
        }
        return ("Table created");
    }


    //READ FROM TIMETABLES ----------------------------------NOT TESTED-------------------------------------------------
    @GET
    @Path("readTables/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readTables() {
        JSONArray list = new JSONArray();
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT TableID, TableName FROM Timetables");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("tableID", results.getInt(1));
                item.put("tableName", results.getString(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }


    //EDIT TIMETABLE FUNCTION ----------------------------------NOT TESTED----------------------------------------------
    @POST
    @Path("editTable/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editTable(@FormDataParam("tableName")String tableName, @FormDataParam("newName")String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Timetables SET TableName = ?, WHERE TableName = ?");
            ps.setString(1,newName);
            ps.setString(2,tableName);

            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error changing timetable name " + exception.getMessage());
        }
        return ("Changes saved");
    }


    //REMOVE TIMETABLE FUNCTION ----------------------------------NOT TESTED--------------------------------------------
    @POST
    @Path("delTable/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delTable(@FormDataParam("tableName")String tableName) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Timetables WHERE TableName = ?");
            ps.setString(1, tableName);
            ps.executeUpdate();


        } catch (Exception exception) {
            return ("Error removing timetable "+ exception.getMessage());
        }
        return ("Table deleted");
    }
}