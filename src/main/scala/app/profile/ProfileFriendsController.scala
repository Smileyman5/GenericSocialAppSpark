package app.profile

import java.util

import app.util.{Path, ViewUtil}
import app.util.RequestUtil._
import spark.template.velocity.VelocityTemplateEngine
import spark.{ModelAndView, Route}

/**
  * Created by alex on 5/7/2017.
  */
object ProfileFriendsController {

  def displayPage(): Route = (request, _) => {
    ViewUtil.render(request, new util.HashMap(), Path.Template.HOME_FRIENDS)
  }

  def addFriend(): Route = (_, _) => {
    ""
  }

  def confirmFriend(): Route = (_, _) => {
    ""
  }

  def declineFriend(): Route = (_, _) => {
    ""
  }

  def getJsonRecommendation: Route = (_, _) => {
    ""
  }

  def getJsonSearch: Route = (_, _) => {
    ""
  }

}
