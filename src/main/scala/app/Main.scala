package app

import app.pages.PostsController
import app.profile.{ProfileFriendsController, ProfileMainController, ProfileSettingsController}
import app.users.{UserController, UserFriendController}
import app.util.{Filters, Path, ViewUtil}
import spark.Spark._

/**
  * Created by alex on 5/2/2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    port(8080)
    staticFiles.location("/public")
    staticFiles.expireTime(600L)

    // Set up before-filters (called before each get/post)
    before("*", Filters.addTrailingSlashes)
    before("*", Filters.removeDuplicateSlashes)

    redirect.get(Path.Web.INDEX,  Path.Web.LOGIN)
    redirect.get(Path.Web.LOGOUT,  Path.Web.LOGIN)

    get (Path.Web.LOGIN,           LoginController.GET)
    post(Path.Web.LOGIN,           LoginController.POST)

    get (Path.Web.REGISTER,        RegisterController.GET)
    post(Path.Web.REGISTER,        RegisterController.POST)


    path(Path.Web.HOME, () => {
      get    (Path.Web.INDEX,      ProfileMainController.displayPage())
      post   (Path.Web.INDEX,      ProfileMainController.updateUser())

      get    (Path.Web.POST,       PostsController.getAllPostsByName())
      post   (Path.Web.POST,       PostsController.post())
      delete (Path.Web.POST,       PostsController.removePost())

      get    (Path.Web.FRIENDS,    ProfileFriendsController.displayPage())
      post   (Path.Web.RFRIENDS,   FriendsController.POST)
      put    (Path.Web.RFRIENDS,   FriendsController.PUT)
      delete (Path.Web.RFRIENDS,   FriendsController.DELETE)

      get    (Path.Web.SETTINGS,   SettingsController.GET)
      post   (Path.Web.SETTINGS,   SettingsController.POST)

    })

    path(Path.Web.USER, () => {
      get    (Path.Web.INDEX,      UserController.displayPage())
      get    (Path.Web.FRIENDS,    UserFriendController.displayPage())
    })

    path(Path.Web.RESTFUL, () => {
      get    (Path.Web.RPROFILE,   ProfileSettingsController.getJsonProfile)
      get    (Path.Web.RRECOMMEND, ProfileFriendsController.getJsonRecommendation)
      get    (Path.Web.RSEARCH,    ProfileFriendsController.getJsonSearch)
    })

    get("*", ViewUtil.notFound)
  }
}
