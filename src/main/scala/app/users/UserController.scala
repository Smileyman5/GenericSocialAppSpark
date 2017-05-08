package app.users

import app.util.RequestUtil._
import java.util

import app.util.{Path, ViewUtil}
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object UserController {
  def displayPage(): Route = (request, _) => {
    val model = new util.HashMap[String, AnyRef]()
    model.put("displayedUser", getQueryUsername(request))
    ViewUtil.render(request, model, Path.Template.USER_PROFILE)
  }
}
