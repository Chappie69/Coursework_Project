package Controllers;

import Server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("UserController/")
public class UserController {

    //CREATE NEW USER FUNCTION ------------------------------(in)COMPLETE-----------------------------------------------
    @GET
    @Path("newUser/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newUser(String username, String password) {
        try {

            int userID;
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password FROM Users");

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
            return("Error adding new user: " + exception.getMessage());
        }
        return ("User added");
    }


    //READ FROM USERS --------------------------------------------------------------------------------------------------
    @POST
    @Path("readUsers/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String readUsers() {

        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username FROM Users");
            ResultSet results = ps.executeQuery();

            //"results.next" makes the program go through each record in the table
            while (results.next()) {
                int userID = results.getInt(1);
                String username = results.getString(2);
                System.out.println(userID + " " + username);
            }

        } catch (Exception exception) {
            return("Database error: " + exception.getMessage());
        }
        return ("Users read from");
    }

    //EDIT EXISTING USER -------------------------- Not tested -----------------------------------------------------
    @GET
    @Path("editUser/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String editUser(String username, String password) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Password = ?, ");
            ps.setString(1,password);

            ps.executeUpdate();

        } catch (Exception exception) {
            return("Error changing password " + exception.getMessage());
        }
        return("User edit complete");
    }

    //DELETE EXISTING USER FUNCTION ------------------------------------------------------------------------------------
    @GET
    @Path("delUser/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delUser(String username) {
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
