package app.index

import app.util.Path
import spark.Route

/**
  * Created by alex on 5/7/2017.
  */
object IndexController {
  def displayPage(): Route = (_, response) => {
    response.redirect(Path.Web.LOGIN)
    ""
  }

}
