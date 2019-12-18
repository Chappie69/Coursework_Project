package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventsController {

    //CREATE EVENT FUNCTION ----------------------------NOT TESTED----------------------------------------------------
    public static void newEvent(String eventName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT EventID, EventName FROM Events");

            int EventID;
            String EventName;

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                EventID = results.getInt(1);
                EventName = results.getString(2);
                System.out.println(EventID + " " + EventName);
            }

            ps = Main.db.prepareStatement("SELECT EventID FROM Events");
            results = ps.executeQuery();
            EventID = results.getInt(1);

            int newID = EventID + 1;
            String EventTime = "00:00";
            Boolean Reminder = false;


            //This is the statement that will be sent into the table ---------------------------------------------------
            ps = Main.db.prepareStatement("INSERT INTO Events (EventID, EventName, EventTime, Reminder) VALUES (?, ?, ?, ?, ?)");

            ps.setInt(1, newID);
            ps.setString(2, eventName);
            ps.setString(3, EventTime);
            ps.setBoolean(4, Reminder);

            //Executes prepared statement
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error adding new task " + exception.getMessage());
        }
    }


    //READ FROM EVENTS -----------------------------------NOT TESTED------------------------------------------------------
    public static void readEvents() {

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
            System.out.println("Error when reading from events " + exception.getMessage());
        }
    }


    //UPDATE EXISTING EVENT -------------------------- Not tested or correct? -----------------------------------------------
    public static void editEvent(String EventName, String newName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Events SET EventName = ?, WHERE EventName = ?");
            ps.setString(1,newName);
            ps.setString(2,EventName);

            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error changing event name " + exception.getMessage());
        }

    }


    //DESTROY EVENT FUNCTION ----------------------------Not tested--------------------------------------------------------
    public static void delEvent(String EventName) {
        try {

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Events WHERE EventName = ?");
            ps.setString(1, EventName);
            ps.executeUpdate();

        } catch (Exception exception) {
            System.out.println("Error removing event: "+ exception.getMessage());
        }
    }
}
