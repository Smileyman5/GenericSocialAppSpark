import spark.Spark.{get, port}
import spark.Spark.{post, port}
import spark.Spark.{get, port, path}
import spark.Spark.{post, port, path}
import spark.Spark.{put, port, path}
import spark.Spark.{delete, port, path}


/**
  * Created by alex on 5/2/2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    port(8080)

    path("/", () => {
//      get("", (req, res) => "Hello")
      post(":username/:password", LoginController.POST)
    })

    post("/register/:username/:password/:rePassword", RegisterController.POST)
    path("/profile", () => {
      path("/settings", () => {
        get("/:username", SettingsController.GET)
        post("/:password/:firstname/:lastname/:birthday/:gender", SettingsController.POST)
      })

      path("/friends", () => {
        post("/:friend", FriendsController.POST)
        put("/:friend", FriendsController.PUT)
        delete("/:friend", FriendsController.DELETE)
      })
    })

  }
}