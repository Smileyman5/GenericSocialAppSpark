/**
 * Created by JON on 5/5/2017.
 */

import spark.*;
import spark.servlet.SparkApplication;

import static spark.Spark.*;

public class Main2 {
    public static void main (String[] args) {
        get("/login", new Route() {
            public Object handle(Request request, Response response) {
                    LoginController login = new LoginController();
                return login.login(request, response);
            }
        });
    }
//    public Main2() {
////        get("/log", SparkApplication::hello);
//        get("/login", new Route() {
//            public Object handle(Request request, Response response) {
//                return "hi";
//            }
//        });
//    }

    public static String hello(Request req, Response res) {
        return "hello world";
    }
}
