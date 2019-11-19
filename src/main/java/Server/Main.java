package Server;

import Controllers.UserDataController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {

    public static Connection db = null;

//  public static void main(String[] args) {

//      openDatabase("proj_database.db");

//      ResourceConfig config = new ResourceConfig();
//      config.packages("Controllers");
//      config.register(MultiPartFeature.class);
//      ServletHolder servlet = new ServletHolder(new ServletContainer(config));

//      Server server = new Server(8081);
//      ServletContextHandler context = new ServletContextHandler(server, "/");
//      context.addServlet(servlet, "/*");

//      try {
//          server.start();
//          System.out.println("Server successfully started.");
//          server.join();
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//  }

    public static void main(String[] args) {
        openDatabase("proj_database.db");

        Scanner in = new Scanner(System.in);

        int userID = 0;
        String user;

        //STUFF TO CALL METHOD TO ADD USER
        System.out.println("Would you like add, delete, edit or view a user? (ADD/DEL/EDIT/VIEW)");
        user = in.nextLine();

        if (user == "ADD") {
            UserDataController.newUser();
        } else if (user == "DEL"){
            UserDataController.delUser();
        } else if  (user == "EDIT") {

            UserDataController.editUser();
        }
    }

    // OPEN DATABASE FUNCTION
    public static void openDatabase(String dbFile) {
        try  {

            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");

        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }
    }

    // CLOSE DATABASE FUNCTION
    public static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }
}
