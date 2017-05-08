package app.profile

import java.util

import app.util.{Path, ViewUtil}
import spark.Route

/**
  * Created by alex on 5/8/2017.
  */
object ProfileSettingsController {

  def displayPage(): Route = (request, _) => {
    ViewUtil.render(request, new util.HashMap(), Path.Template.HOME_SETTINGS)
  }

  def getJsonProfile: Route = (_, _) => {
    ""
  }
}
