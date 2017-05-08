package app.users

import java.util

import app.util.Path
import spark.{ModelAndView, Route}
import spark.template.velocity.VelocityTemplateEngine

/**
  * Created by alex on 5/7/2017.
  */
object UserController {
  def displayPage(): Route = (request, response) => {
    new VelocityTemplateEngine().render(new ModelAndView(new util.HashMap(), Path.Template.USER_PROFILE))
  }
}
