package app.profile

import java.util

import app.util.RequestUtil._
import app.util.{DBManager, Path, ViewUtil}
import com.google.gson.Gson
import spark.Route

/**
  * Created by alex on 5/8/2017.
  */
object ProfileSettingsController {

  def displayPage(): Route = (request, response) => {
    if (authenicateUser(request))
      ViewUtil.render(request, new util.HashMap(), Path.Template.HOME_SETTINGS)
    else
    {
      response.redirect(Path.Web.LOGIN)
      ""
    }
  }

  def getJsonProfile: Route = (request, response) => {
    val map = DBManager.queryUserData(getQueryUsername(request))
    response.`type`("application/json")
    new Gson().toJson(map)
  }
}
