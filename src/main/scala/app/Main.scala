package app

import app.index.IndexController
import app.login.LoginController
import app.profile.{FriendsController, ProfileController}
import app.users.{UserController, UserFriendController}
import app.util.{Filters, Path}
import spark.Spark._

/**
  * Created by alex on 5/2/2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    port(8080)
    staticFiles.location("/public")
    staticFiles.expireTime(500L)

    // Set up before-filters (called before each get/post)
    before("*", Filters.addTrailingSlashes)

    get("/", IndexController.displayPage())
    get("/login/", LoginController.displayPage())

    path(Path.Web.HOME, () => {
      get("/", ProfileController.displayPage())
      get(Path.Web.FRIENDS, FriendsController.displayPage())
    })

    path(Path.Web.USER, () => {
      get("/", UserController.displayPage())
      get(Path.Web.FRIENDS, UserFriendController.displayPage())
    })
  }
}
