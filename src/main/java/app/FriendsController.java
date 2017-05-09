package app;

import app.util.DBManager;
import spark.Route;

import javax.xml.crypto.Data;
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
            DatabaseQuery.execute("INSERT INTO Friends VALUES ('" + username + "', '" + friend + "', 'Pending');");

        return "";
    };

    public static Route PUT = (req, res) -> {
        String username = req.session().attribute("username");
        String friend = req.params("friend");

        if (username != null && friend != null) {
            DatabaseQuery.execute("UPDATE Friends SET status='Confirmed' WHERE username='" + friend + "' AND friend='" + username + "';");
            DatabaseQuery.execute("INSERT INTO Friends VALUES ('" + username + "', '" + friend + "', 'Confirmed');");
        }

        return "";
    };

    public static Route DELETE = (req, res) -> {
        String username = req.session().attribute("username");
        String friend = req.params("friend");

        if (username != null && friend != null)
            DatabaseQuery.execute("DELETE FROM Friends WHERE username='" + friend + "' AND friend='" + username + "';");

        return "";
    };
}
