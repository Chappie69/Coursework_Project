package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("UserController/")
public class UserController {

    //CREATE NEW USER FUNCTION ------------------------------COMPLETE--------------------------------------------------
    @POST
    @Path("newUser/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newUser(@FormDataParam("username")String username, @FormDataParam("password")String password) {
        try {

            int userID;
            PreparedStatement ps = Main.db.prepareStatement("SELECT MAX(UserID) FROM Users");

            ResultSet results = ps.executeQuery();
            userID = results.getInt(1);

            //This is the statement that will be sent into the table
            ps = Main.db.prepareStatement("INSERT INTO Users (UserID, Username, Password) VALUES (?, ?, ?)");

            int newID = userID + 1;

            ps.setInt(1, newID);
            ps.setString(2, username);
            ps.setString(3, password);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            return ("Error adding new user: " + exception.getMessage());
        }
        return ("User added");
    }


    //READ FROM USERS --------------------------------------------------------------------------------------------------
    @GET
    @Path("readUsers/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readUsers() {
        JSONArray list = new JSONArray();
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username FROM Users");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("userID", results.getInt(1));
                item.put("username", results.getString(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }
    }

    //EDIT EXISTING USER -------------------------- Not tested -----------------------------------------------------
    @POST
    @Path("editUser/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editUser(@FormDataParam("username")String username, @FormDataParam("newName")String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ? WHERE Username = ?");
            ps.setString(1, username);
            ps.setString(2, newName);

            ps.executeUpdate();

        } catch (Exception exception) {
            return("Error changing password " + exception.getMessage());
        }
        return("User edit complete");
    }

    //DELETE EXISTING USER FUNCTION ------------------------------------------------------------------------------------
    @POST
    @Path("delUser/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delUser(@FormDataParam("username")String username) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
            ps.setString(1, username);
            ps.executeUpdate();


        } catch (Exception exception) {
            return("Error removing user: "+ exception.getMessage());
        }
        return("User deleted");
    }
}
