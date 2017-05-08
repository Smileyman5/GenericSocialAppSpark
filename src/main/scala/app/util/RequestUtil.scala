package app.util

import spark.Request

/**
  * Created by alex on 5/7/2017.
  */
object RequestUtil {
  def getSessionCurrentUser(request: Request): String = {
    request.session().attribute("username")
  }
}
