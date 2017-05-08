import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by JON on 5/6/2017.
 */
public class RegisterController {

    public static Route GET = (req, res) -> {
        return new VelocityTemplateEngine().render(new ModelAndView(new HashMap(), "templates/register.vtl"));
    };

    public static Route POST = (req, res) -> {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String username = req.queryParams("username");
        String password = req.queryParams("password");
        String rePassword = req.queryParams("repassword");

        System.out.println("Pass: " + password);
        System.out.println("RePass: " + rePassword);

        if (!password.equals(rePassword)) {
            map.put("message", "Passwords do not match");
        }
        else if (createUser(username, password)) {
            req.session().attribute("username", username);
            map.put("message", "User created");
//            res.redirect("/home");
//            return null;
        }
        else {
            map.put("message", "User Already Exists");
        }

        return new VelocityTemplateEngine().render(new ModelAndView(map, "templates/register.vtl"));
    };

    private static boolean createUser(String username, String password) {
//        Connection con = null;
//        Statement state = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
//            state = con.createStatement();
//            state.execute("INSERT INTO Users VALUES ('" + username + "', '" + password + "', '', '', '', '', 0)");
//        } catch (SQLException | ClassNotFoundException e) {
//            return false;
//        } finally {
//            try {
//                if (state != null) { state.close(); }
//                if (con != null) { con.close(); }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return true;
        return DatabaseQuery.execute("INSERT INTO Users VALUES ('" + username + "', '" + password + "', '', '', '', '', 0)");
    }
}
