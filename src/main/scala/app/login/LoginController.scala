package app.login

import app.util.Path
import spark.template.velocity.VelocityTemplateEngine
import spark.{ModelAndView, Route}

import scala.collection.mutable

/**
  * Created by alex on 5/7/2017.
  */
object LoginController {
  def displayPage(): Route = (_, _) => {
//    request.session().invalidate()
    new VelocityTemplateEngine().render(new ModelAndView(new java.util.HashMap(), Path.Template.LOGIN))
  }

}
