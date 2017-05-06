import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import spark.*;

/**
 * Created by JON on 5/6/2017.
 */
public class RegisterController {

    public RegisterController(){}

    public JsonObject register(Request req, Response res) {
        JsonObject json = new JsonObject();
        String username = "";       //req.params("username");
        String password = "";       //req.params("password");
        String rePassword = "";     //req.params("rePassword");

        if (!password.equals(rePassword)) {
            json.add("message", new JsonPrimitive("Passwords do not match"));
        }
        else if (createUser(username, password)) {
            json.add("message", new JsonPrimitive("Passwords do not match"));
        }
        else {
            json.add("message", new JsonPrimitive("true"));
        }

        return json;
    }

    private boolean createUser(String username, String password) {
        return false;
    }
}
