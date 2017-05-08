package app.profile

import java.util

import app.util.Path
import spark.{ModelAndView, Route}
import spark.template.velocity.VelocityTemplateEngine

/**
  * Created by alex on 5/8/2017.
  */
object ProfileSettingsController {

  def displayPage(): Route = (_, _) => {
    new VelocityTemplateEngine().render(new ModelAndView(new util.HashMap(), Path.Template.HOME_SETTINGS))
  }

  def getJsonProfile: Route = (_, _) => {
    ""
  }
}
