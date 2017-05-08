package app.users

import app.util.Path
import spark.template.velocity.VelocityTemplateEngine
import spark.{ModelAndView, Route}

/**
  * Created by alex on 5/7/2017.
  */
object UserFriendController {
  def displayPage(): Route = (request, response) => {
    new VelocityTemplateEngine().render(new ModelAndView(new java.util.HashMap(), Path.Template.USER_FRIENDS))
  }

}
