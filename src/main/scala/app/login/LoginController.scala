package app.login

import app.util.{Path, ViewUtil}
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object LoginController {

  def displayPage(): Route = (request, _) => {
    request.session().attributes().clear()
    ViewUtil.render(request, new java.util.HashMap(), Path.Template.LOGIN)
  }

  def login(): Route = (_, _) => {
    ""
  }

}
