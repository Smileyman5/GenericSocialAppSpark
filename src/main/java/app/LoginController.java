import spark.*;
import spark.template.velocity.*;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by JON on 5/6/2017.
 */

public class LoginController {

    public static Route GET = (req, res) -> {
        return new VelocityTemplateEngine().render(new ModelAndView(new HashMap(), "templates/index.vtl"));
    };

    public static Route POST = (req, res) -> {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String username = req.queryParams("username");
        String password = req.queryParams("password");

        if (checkLogin(username, password)) {
            req.session().attribute("username", username);
            updateStats(username, password);
            res.redirect("/register");
            return null;
        }
        else {
            map.put("message", "Username/Password incorrect");
        }
        return new VelocityTemplateEngine().render(new ModelAndView(map, "templates/index.vtl"));
    };

    private static boolean checkLogin(String username, String password) {
//        Connection con = null;
//        Statement state = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
//            state = con.createStatement();
//            ResultSet set = state.executeQuery("SELECT * FROM Users WHERE BINARY username='" + username + "' AND BINARY password='" + password + "'");
//            return set.next();
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (state != null) { state.close(); }
//                if (con != null) { con.close(); }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return false;

//        try {
//            ResultSet set = DatabaseQuery.executeQuery("SELECT * FROM Users WHERE BINARY username='" + username + "' AND BINARY password='" + password + "'");
//            return set.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;

        return DatabaseQuery.executeQuery("SELECT * FROM Users WHERE BINARY username='" + username + "' AND BINARY password='" + password + "'", "username").size() != 0;
    }

    private static void updateStats(String username, String password) {
//        Connection con = null;
//        Statement state = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
//            state = con.createStatement();
//            state.execute("UPDATE Users SET login=login+1 WHERE username='" + username + "';");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (state != null) { state.close(); }
//                if (con != null) { con.close(); }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
        DatabaseQuery.execute("UPDATE Users SET login=login+1 WHERE username='" + username + "';");
    }

}