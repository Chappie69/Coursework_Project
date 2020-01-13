package Controllers;

import Server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

@Path("TimetableController/")
public class TimetableController {

    public static Connection db = null;

    //ADD TIMETABLE FUNCTION ----------------------------------NOT TESTED------------------------------------------------
    @GET
    @Path("newTable/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTable(String tableName) {
        try {

            int tableID;
            PreparedStatement ps = Main.db.prepareStatement("SELECT TableID, FROM Tables");

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
    @POST
    @Path("readTables/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String readTables() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT TableID, TableName FROM Timetables");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int tableID = results.getInt(1);
                String tableName = results.getString(2);
                System.out.println(tableID + " " + tableName);
            }

        } catch (Exception exception) {
            return ("Error reading from timetables " + exception.getMessage());
        }
        return ("Tables read");
    }


    //EDIT TIMETABLE FUNCTION ----------------------------------NOT TESTED----------------------------------------------
    @GET
    @Path("editTable/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editTable(String tableName, String newName) {
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
    @GET
    @Path("delTable/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delTable(String tableName) {
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