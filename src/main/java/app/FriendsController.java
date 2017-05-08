package app;

import spark.Route;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by JON on 5/6/2017.
 */
public class FriendsController {

    public static Route POST = (req, res) -> {
        String username = req.session().attribute("username");
        String friend = req.params("friend");

        if (username != null && friend != null)
            connect("INSERT INTO Friends VALUES ('" + username + "', '" + friend + "', 'Pending');");

        return "";
    };

    public static Route PUT = (req, res) -> {
        String username = req.session().attribute("username");
        String friend = req.params("friend");

        if (username != null && friend != null) {
            connect("UPDATE Friends SET status='Confirmed' WHERE username='" + friend + "' AND friend='" + username + "';");
            connect("INSERT INTO Friends VALUES ('" + username + "', '" + friend + "', 'Confirmed');");
        }

        return "";
    };

    public static Route DELETE = (req, res) -> {
        String username = req.session().attribute("username");
        String friend = req.params("friend");

        if (username != null && friend != null)
            connect("DELETE FROM Friends WHERE username='" + friend + "' AND friend='" + username + "';");

        return "";
    };

    private static void connect(String sql) {
        Connection con = null;
        Statement state = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
            state = con.createStatement();
            state.execute(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) { state.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
