import spark.Spark.{get, port}
import spark.Spark.{post, port}
import spark.Spark.{post, port, path}
import spark.Spark.{get, port, path}


/**
  * Created by alex on 5/2/2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    port(8080)
    path("/", () => {
      get("", (req, res) => "Hello")
      post(":username/:password", LoginController::login())
    })

    post("/register/:username/:password/:rePassword", (req, res) => new RegisterController().register(req, res))
    post("/profile/:password/:firstname/:lastname/:birthday/:gender", (req, res) => new SettingsController().updateUser(req, res))
  }
}