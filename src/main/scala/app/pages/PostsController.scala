package app.pages

import java.util
import java.util.Scanner

import app.util.DBManager._
import app.util.RequestUtil._
import app.util.{DBManager, JsonTransformer, Path, ViewUtil}
import com.google.gson.Gson
import org.eclipse.jetty.http.HttpStatus
import spark.Route

/**
  * Created by alex on 5/6/2017.
  */
object PostsController {

  private case class ID(postId: Int)

  def getAllPostsByName: Route = (request, response) => {
    val userPosts = queryAllUsersPosts(getSessionCurrentUser(request))
    response.`type`("application/json")
    new Gson().toJson(userPosts)
  }

  def post(): Route = (request, response) => {
    DBManager.executePostFromUser(getSessionCurrentUser(request), request.body().substring(request.body().indexOf("=") + 1))
    response.status(HttpStatus.ACCEPTED_202)
    ViewUtil.render(request, new util.HashMap[String, AnyRef](), Path.Template.HOME_PROFILE)
  }

  def addCommentOrLike(): Route = (request, response) => {
    val payload = new Scanner(request.body())
    val postLoad = payload.nextLine()
    val commentLoad = payload.nextLine()
    val postId = Integer.parseInt(postLoad.substring(postLoad.indexOf("=") + 1))
    val comment = commentLoad.substring(commentLoad.indexOf("=") + 1)
    DBManager.executeCommentOnPost(getSessionCurrentUser(request), comment, 1, postId)
    response.status(HttpStatus.OK_200)
    ViewUtil.render(request, new util.HashMap[String, AnyRef](), Path.Template.HOME_PROFILE)
  }

  def removePost(): Route = (request, response) => {
    val postLoad = JsonTransformer.fromJson(request.body(), classOf[ID])
    DBManager.executeRemovePostById(postLoad.postId)
    response.status(HttpStatus.OK_200)
    ""
  }

  def getAllPostsByNameForUser: Route = (request, response) => {
    val username = getQueryUsername(request)
    val userPosts = queryAllUsersPosts(username)
    response.`type`("application/json")
    new Gson().toJson(userPosts)
  }

}
