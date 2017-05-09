package app;

import app.util.DBManager;
import app.util.ViewUtil;
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
        HashMap<String, Object> map = new HashMap<String, Object>();
        String username = req.session().attribute("username");
        String password = req.queryParams("password").trim();
        String firstname = req.queryParams("firstName").trim();
        String lastname = req.queryParams("lastName").trim();
        String birthday = req.queryParams("birthday").trim();
        String gender = req.queryParams("gender").trim();

        map.put("fname", firstname);
        map.put("lname", lastname);
        map.put("bday", birthday);
        map.put("gender", gender);

        if (update(username, password, firstname, lastname, birthday, gender)) {
            map.put("message", "Updated");
        }
        else {
            map.put("message", "Failed to Update");
        }

        return ViewUtil.render(req, map, "templates/settings.vtl");
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
