import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import spark.*;

import java.sql.*;

/**
 * Created by JON on 5/6/2017.
 */
public class SettingsController {

    public JsonObject updateUser (Request req, Response res) {
        JsonObject json = new JsonObject();
        String username = req.session().attribute("username");
        String password = req.params("password");
        String firstname = req.params("firstname");
        String lastname = req.params("lastname");
        String birthday = req.params("birthday");
        String gender = req.params("gender");

//        if (gender.equals("Male") || gender.equals("Female") || gender.equals("")) {
        if (update(username, password, firstname, lastname, birthday, gender)) {
            json.add("message", new JsonPrimitive("Updated"));
        }
        else {
            json.add("message", new JsonPrimitive("Failed to Update"));
        }

        return json;
    }

    private boolean update(String username, String password, String firstname, String lastname, String birthday, String gender) {
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data?useSSL=false", "root", "");
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
