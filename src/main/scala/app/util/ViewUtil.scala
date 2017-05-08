package app.util

import app.util.RequestUtil.getSessionCurrentUser
import org.apache.velocity.app._
import org.eclipse.jetty.http._
import spark._
import spark.template.velocity._

object ViewUtil { // Renders a template given a model and a request
  // The request is needed to check the user session for language settings
  // and to see if the user is logged in
  def render(request: Request, model: java.util.Map[String, AnyRef], templatePath: String): String = {
    if (request.session().attribute("currentUser") == null)
      request.session().attribute("currentUser", "Alex")

    model.put("currentUser", getSessionCurrentUser(request))
    model.put("WebPath", Path.Web)
    strictVelocityEngine.render(new ModelAndView(model, templatePath))
  }

  var notFound: Route = (request: Request, response: Response) => {
    def foo(request: Request, response: Response) = {
      response.status(HttpStatus.NOT_FOUND_404)
      render(request, new java.util.HashMap[String, AnyRef], Path.Template.NOT_FOUND)
    }

    foo(request, response)
  }

  private def strictVelocityEngine = {
    val configuredEngine = new VelocityEngine
    configuredEngine.setProperty("runtime.references.strict", true)
    configuredEngine.setProperty("resource.loader", "class")
    configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")
    new VelocityTemplateEngine(configuredEngine)
  }
}
