package app.profile

import java.util

import app.util.Path
import app.util.RequestUtil._
import spark.template.velocity.VelocityTemplateEngine
import spark.{ModelAndView, Route}

/**
  * Created by alex on 5/7/2017.
  */
object ProfileFriendsController {
  def displayPage(): Route = (request, _) => {
    if (request.session().attribute("currentUser") == null)
      request.session().attribute("currentUser", "Alex")

    val model = new util.HashMap[String, Any]()
    model.put("currentUser", getSessionCurrentUser(request))
    model.put("WebPath", Path.Web)
    new VelocityTemplateEngine().render(new ModelAndView(model, Path.Template.HOME_FRIENDS))
  }
}
