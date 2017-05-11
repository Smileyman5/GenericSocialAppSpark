package app;

import app.util.DBManager;
import app.util.ViewUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import spark.Route;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.HashMap;

/**
 * Created by JON on 5/6/2017.
 */
public class SettingsController {

    public static Route GET = (req, res) -> {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String username = req.session().attribute("username");
        Connection con = null;
        Statement state = null;
        map.put("username", username);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", DBManager.username(), DBManager.password());
            state = con.createStatement();
            ResultSet set = state.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
            if (set.next()) {
                map.put("fname", set.getString("first_name"));
                map.put("lname", set.getString("last_name"));
                map.put("bday", set.getString("birthday"));
                map.put("gender", set.getString("gender"));
            }
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

        return ViewUtil.render(req, map, "templates/settings.vtl");
    };

    public static Route POST = (req, res) -> {
        JsonObject json = new JsonObject();
        JsonObject element = ((JsonObject) new JsonParser().parse(req.body()));
        String username = req.session().attribute("username");
        String password = element.get("password").getAsString();
        String firstname = element.get("firstName").getAsString();
        String lastname = element.get("lastName").getAsString();
        String birthday = element.get("birthday").getAsString();
        String gender = element.get("gender").getAsString();

        if (update(username, password, firstname, lastname, birthday, gender)) {
            json.add("message", new JsonPrimitive("Updated"));
        }
        else {
            json.add("message", new JsonPrimitive("Failed to Update"));
        }

        return json;
    };

    private static boolean update(String username, String password, String firstname, String lastname, String birthday, String gender) {
        if (!((firstname != null && !firstname.equals(""))
                && (lastname != null && !lastname.equals(""))
                && (birthday != null && !birthday.equals(""))
                && (gender != null && (gender.equals("") || gender.equals("Male") || gender.equals("Female"))))) {
            return false;
        }

        String sqlQuery = "UPDATE Users SET ";
        if (!password.equals("") && password != null)
            sqlQuery += " password='" + password + "', ";
        sqlQuery += "first_name='" + firstname + "', ";
        sqlQuery += "last_name='" + lastname + "', ";
        sqlQuery += "birthday='" + birthday + "', ";
        sqlQuery += "gender='" + gender + "' ";
        sqlQuery += "WHERE username='" + username + "';";
        DatabaseQuery.execute(sqlQuery);

        return true;
    }

}
