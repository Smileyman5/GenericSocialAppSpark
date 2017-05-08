package app.util

import spark.{Filter, Request, Response}

/**
  * Created by alex on 5/7/2017.
  */
object Filters {
  // If a user manually manipulates paths and forgets to add
  // a trailing slash, redirect the user to the correct path
  var addTrailingSlashes: Filter = (request: Request, response: Response) => {
    def foo(request: Request, response: Response) = {
      if (!request.pathInfo.endsWith("/")) response.redirect(request.pathInfo + "/")
    }

    foo(request, response)
  }

  var removeDuplicateSlashes: Filter = (request, response) => {
    def foo(request: Request, response: Response) = {
      if (request.pathInfo.contains("//")) response.redirect(request.pathInfo.replaceAll("//", "/"))
    }

    foo(request, response)
  }

}
