package app.users

import app.util.{Path, ViewUtil}
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object UserFriendController {
  def displayPage(): Route = (request, _) => {
    ViewUtil.render(request, new java.util.HashMap(), Path.Template.USER_FRIENDS)
  }

}
