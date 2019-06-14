import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static Connection db = null;

    public static void main(String[] args) {
        openDatabase("proj_database.db");

        Scanner in = new Scanner(System.in);

        int userID = 0;
        String user = "Coolio";
        Boolean go = true;

        try {

            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, Password FROM UserData");

            while (go = true){
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    userID = results.getInt(1);
                    String username = results.getString(2);
                    String password = results.getString(3);
                    System.out.println(userID + " " + username + " " + password);
                }

                ps = db.prepareStatement("INSERT INTO UserData (UserId, Username, Password) VALUES (?, ?, ?)");

                int newID = userID + 1;
                String inpUser = in.nextLine();
                String inpPass = in.nextLine();

                ps.setInt(1, newID);
                ps.setString(2, inpUser);
                ps.setString(3, inpPass);

                ps.executeUpdate();

                System.out.println("Would you like add a new user? (Y/N)");
                user = in.nextLine();

                if (user == "N"){
                    go = false;
                }
            }
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

        closeDatabase();
    }

    private static void openDatabase(String dbFile) {
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

    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }
}
