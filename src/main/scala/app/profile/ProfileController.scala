package app.profile

import java.util

import app.util.{Path, ViewUtil}
import spark.template.velocity.VelocityTemplateEngine
import spark.{ModelAndView, Route}

import scala.collection.mutable

/**
  * Created by alex on 5/7/2017.
  */
object ProfileController {
  def displayPage(): Route = (_, _) => {
    new VelocityTemplateEngine().render(new ModelAndView(new util.HashMap(), Path.Template.HOME_PROFILE))
  }
}
