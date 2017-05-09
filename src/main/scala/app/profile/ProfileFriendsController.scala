package app.profile

import java.util

import app.util.RequestUtil._
import app.util.{DBManager, Path, ViewUtil}
import com.google.gson.Gson
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object ProfileFriendsController {

  def displayPage(): Route = (request, response) => {
    if (authenicateUser(request))
      ViewUtil.render(request, new util.HashMap(), Path.Template.HOME_FRIENDS)
    else
    {
      response.redirect(Path.Web.LOGIN)
      ""
    }
  }

  def getJsonRecommendation: Route = (request, response) => {
    val list = DBManager.queryRecommendations(getQueryUsername(request))
    response.`type`("application/json")
    new Gson().toJson(list)
  }

  def getJsonSearch: Route = (_, response) => {
    val list = DBManager.queryAllUsers()
    response.`type`("application/json")
    new Gson().toJson(list)
  }

}
