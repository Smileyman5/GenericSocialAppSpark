import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import spark.*;

import java.sql.*;

/**
 * Created by JON on 5/6/2017.
 */
public class LoginController {

    public static Route POST = (req, res) -> {
        JsonObject json = new JsonObject();
        String username = req.params("username");
        String password = req.params("password");

        if (checkLogin(username, password)) {
            json.add("message", new JsonPrimitive("true"));
            req.session().attribute("username", username);
            updateStats(username, password);
        }
        else {
            json.add("message", new JsonPrimitive("Username/Password incorrect"));
        }

        return json;
    };

    public static JsonObject login (Request req, Response res) {
        JsonObject json = new JsonObject();
        String username = req.params("username");
        String password = req.params("password");

        if (checkLogin(username, password)) {
            json.add("message", new JsonPrimitive("true"));
            req.session().attribute("username", username);
            updateStats(username, password);
        }
        else {
            json.add("message", new JsonPrimitive("Username/Password incorrect"));
        }

        return json;
    }

    private static boolean checkLogin(String username, String password) {
        Connection con = null;
        Statement state = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data?useSSL=false", "root", "");
            state = con.createStatement();
            ResultSet set = state.executeQuery("SELECT * FROM Users WHERE BINARY username='" + username + "' AND BINARY password='" + password + "'");
            return set.next();
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
        return false;
    }

    private static void updateStats(String username, String password) {
        Connection con = null;
        Statement state = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data?useSSL=false", "root", "");
            state = con.createStatement();
            state.execute("UPDATE Users SET login=login+1 WHERE username='" + username + "';");
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