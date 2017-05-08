package app.users

import java.util

import app.util.{Path, ViewUtil}
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object UserController {
  def displayPage(): Route = (request, _) => {
    ViewUtil.render(request, new util.HashMap(), Path.Template.USER_PROFILE)
  }
}
