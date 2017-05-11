package app;

import app.util.ViewUtil;
import com.google.gson.*;
import spark.Route;

import java.util.HashMap;

/**
 * Created by JON on 5/6/2017.
 */
public class RegisterController {

    public static Route GET = (req, res) -> {
        return ViewUtil.render(req, new HashMap<>(), "templates/register.vtl");
    };

    public static Route POST = (req, res) -> {
        JsonObject json = new JsonObject();
        JsonObject element = ((JsonObject) new JsonParser().parse(req.body()));
        String username = element.get("username").getAsString();
        String password = element.get("password").getAsString();
        String rePassword = element.get("repassword").getAsString();

        if (!password.equals(rePassword)) {
            json.add("message", new JsonPrimitive("Passwords do not match"));
        }
        else if (createUser(username, password)) {
            req.session().attribute("username", username);
            json.add("message", new JsonPrimitive("User Created"));
        }
        else {
            json.add("message", new JsonPrimitive("User Already Exists"));
        }

        return json.toString();
    };

    private static boolean createUser(String username, String password) {
        return DatabaseQuery.execute("INSERT INTO Users VALUES ('" + username + "', '" + password + "', '', '', '', '', 0)");
    }
}
