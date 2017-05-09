package app;

import app.util.ViewUtil;
import spark.Route;

import java.util.HashMap;

/**
 * Created by JON on 5/6/2017.
 */

public class LoginController {

    public static Route GET = (req, res) -> {
        req.session(true).removeAttribute("username");
        return ViewUtil.render(req, new HashMap<>(), "templates/index.vtl");
    };

    public static Route POST = (req, res) -> {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String username = req.queryParams("username");
        String password = req.queryParams("password");

        if (checkLogin(username, password)) {
            req.session().attribute("username", username);
            updateStats(username, password);
            res.redirect("/home");
            return null;
        }
        else {
            map.put("message", "Username/Password incorrect");
        }
        return ViewUtil.render(req, map, "templates/index.vtl");
    };

    private static boolean checkLogin(String username, String password) {
        return DatabaseQuery.executeQuery("SELECT * FROM Users WHERE BINARY username='" + username + "' AND BINARY password='" + password + "'", "username").size() != 0;
    }

    private static void updateStats(String username, String password) {
        DatabaseQuery.execute("UPDATE Users SET login=login+1 WHERE username='" + username + "';");
    }

}