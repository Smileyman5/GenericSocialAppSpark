package app

import app.index.IndexController
import app.login.LoginController
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

    get (Path.Web.LOGIN,           LoginController.displayPage())
    post(Path.Web.LOGIN,           LoginController.login())

    path(Path.Web.HOME, () => {
      get    (Path.Web.INDEX,      ProfileMainController.displayPage())
      post   (Path.Web.INDEX,      ProfileMainController.updateUser())

      get    (Path.Web.POST,       PostsController.getAllPostsByName())
      post   (Path.Web.POST,       PostsController.post())
      delete (Path.Web.POST,       PostsController.removePost())

      get    (Path.Web.FRIENDS,    ProfileFriendsController.displayPage())
      post   (Path.Web.FRIENDS,    ProfileFriendsController.addFriend())
      put    (Path.Web.FRIENDS,    ProfileFriendsController.confirmFriend())
      delete (Path.Web.FRIENDS,    ProfileFriendsController.declineFriend())

      get    (Path.Web.SETTINGS,   ProfileSettingsController.displayPage())

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
