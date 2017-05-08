/**
 * Created by JON on 5/7/2017.
 */

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class Main2 {

    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/public");

        path("/", () -> {
            get("", LoginController.GET);
            post("", LoginController.POST);
        });

        path("/register", () -> {
            get("", RegisterController.GET);
            post("", RegisterController.POST);
        });
        path("/profile", () -> {
            path("/settings", () -> {
                get("", SettingsController.GET);
                post("", SettingsController.POST);
            });

            path("/friends", () -> {
                post("/:friend", FriendsController.POST);
                put("/:friend", FriendsController.PUT) ;
                delete("/:friend", FriendsController.DELETE);
            });
         });
    }
}
