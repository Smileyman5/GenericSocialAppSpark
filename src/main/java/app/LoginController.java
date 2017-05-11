package app;

import app.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import spark.Route;

import java.util.ArrayList;
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
        JsonObject json = new JsonObject();
        JsonObject element = ((JsonObject) new JsonParser().parse(req.body()));
        String username = element.get("username").getAsString();
        String password = element.get("password").getAsString();

        if (checkLogin(username, password)) {
            req.session().attribute("username", username);
            updateStats(username, password);
            json.add("failed", new JsonPrimitive(false));
        }
        else {
            json.add("failed", new JsonPrimitive(true));
        }
        return json;
    };

    public static Route FORGOT = (req, res) -> {
        JsonObject json = new JsonObject();

        JsonObject element = ((JsonObject) new JsonParser().parse(req.body()));
        String username = element.get("username").getAsString();

        ArrayList<String> users = DatabaseQuery.executeQuery("SELECT * FROM Users WHERE username LIKE '%" + username + "%';", "username");
        if (users.size() > 0) {
            json.add("message", new JsonPrimitive(""));
            json.add("username", new JsonPrimitive(users.get(0)));
            json.add("password", new JsonPrimitive(DatabaseQuery.executeQuery("SELECT * FROM Users WHERE username='" + users.get(0) + "';", "password").get(0)));
        }
        else {
            json.add("message", new JsonPrimitive("Please enter another username"));
            json.add("username", new JsonPrimitive(username));
            json.add("password", new JsonPrimitive(""));
        }

        return json;
    };

    private static boolean checkLogin(String username, String password) {
        return DatabaseQuery.executeQuery("SELECT * FROM Users WHERE BINARY username='" + username + "' AND BINARY password='" + password + "'", "username").size() != 0;
    }

    private static void updateStats(String username, String password) {
        DatabaseQuery.execute("UPDATE Users SET login=login+1 WHERE username='" + username + "';");
    }

}