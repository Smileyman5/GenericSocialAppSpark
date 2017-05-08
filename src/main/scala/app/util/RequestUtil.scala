package app.util

import spark.Request

/**
  * Created by alex on 5/7/2017.
  */
object RequestUtil {

  def getQueryUsername(request: Request): String = {
    request.params("username")
  }

  def getSessionCurrentUser(request: Request): String = {
    request.session().attribute("username")
  }

  def clientAcceptsHtml(request: Request): Boolean = {
    val accept = request.headers("Accept")
    accept != null && accept.contains("text/html")
  }

  def clientAcceptsJson(request: Request): Boolean = {
    val accept = request.headers("Accept")
    accept != null && accept.contains("application/json")
  }
}
