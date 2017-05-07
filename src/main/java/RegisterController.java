import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by JON on 5/6/2017.
 */
public class RegisterController {

    public static Route POST = (req, res) -> {
        JsonObject json = new JsonObject();
        String username = req.params("username");
        String password = req.params("password");
        String rePassword = req.params("rePassword");

        if (!password.equals(rePassword)) {
            json.add("message", new JsonPrimitive("Passwords do not match"));
        }
        else if (createUser(username, password)) {
            req.session().attribute("username", username);
            json.add("message", new JsonPrimitive("true"));
        }
        else {
            json.add("message", new JsonPrimitive("User Already Exists"));
        }

        return json;
    };

    private static boolean createUser(String username, String password) {
        Connection con = null;
        Statement state = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
            state = con.createStatement();
            state.execute("INSERT INTO Users VALUES ('" + username + "', '" + password + "', '', '', '', '', 0)");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (state != null) { state.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
