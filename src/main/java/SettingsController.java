import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.collections.map.HashedMap;
import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
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

        return new VelocityTemplateEngine().render(new ModelAndView(map, "templates/settings.vtl"));
    };

    public static Route POST = (req, res) -> {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String username = req.session().attribute("username");
        String password = req.queryParams("password");
        String firstname = req.queryParams("firstname");
        String lastname = req.queryParams("lastname");
        String birthday = req.queryParams("birthday");
        String gender = req.queryParams("gender");

        if (update(username, password, firstname, lastname, birthday, gender)) {
            map.put("message", "Updated");
        }
        else {
            map.put("message", "Failed to Update");
        }

        return new VelocityTemplateEngine().render(new ModelAndView(map, "templates/settings.vtl"));
    };

    private static boolean update(String username, String password, String firstname, String lastname, String birthday, String gender) {
        if ((!password.equals("") && password != null)
                && (!firstname.equals("") && firstname != null)
                && (!lastname.equals("") && lastname != null)
                && (!birthday.equals("") && birthday != null)
                && ((gender.equals("") || gender.equals("Male") || gender.equals("Female")) && gender != null))
            return false;

        Connection con = null;
        Statement state = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
            state = con.createStatement();
            state.execute("UPDATE Users SET password='" + password + "' WHERE username='" + username + "'");
            state.execute("UPDATE Users SET firstname='" + firstname + "' WHERE username='" + username + "'");
            state.execute("UPDATE Users SET lastname='" + lastname + "' WHERE username='" + username + "'");
            state.execute("UPDATE Users SET birthday='" + birthday + "' WHERE username='" + username + "'");
            state.execute("UPDATE Users SET gender='" + gender + "' WHERE username='" + username + "'");
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
        return true;
    }

}
