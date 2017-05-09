package app.users

import java.util

import app.util.RequestUtil.getQueryUsername
import app.util.{DBManager, Path, ViewUtil}
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object UserFriendController {
  def displayPage(): Route = (request, response) => {
    val displayedUser = getQueryUsername(request)
    if (DBManager.userExists(displayedUser)) {
      val model = new util.HashMap[String, AnyRef]()
      model.put("displayedUser", getQueryUsername(request))
      ViewUtil.render(request, model, Path.Template.USER_FRIENDS)
    } else {
      response.redirect(Path.Web.LOGIN); ""
    }
  }

}
