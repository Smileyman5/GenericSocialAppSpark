package app.util

import java.sql.{DriverManager, Timestamp}
import java.util

/**
  * Created by alex on 5/8/2017.
  */
object DBManager {

  val username = "smileyman5"
  val password = "password"

  def queryAllUsers(): util.ArrayList[String] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    val result = stmt.executeQuery("Select username from users;")
    val list = new util.ArrayList[String]()
    while (result.next())
      list.add(result.getString("username"))
    result.close()
    con.close()
    stmt.close()
    list
  }

  def queryRecommendations(user: String): util.HashSet[String] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", this.username, password)
    val stmt = con.createStatement

    val friendsRes = stmt.executeQuery("Select * from friends where username='" + user + "';")
    val friends = new util.HashSet[String]()
    val recommendations = new util.HashSet[String]()

    while (friendsRes.next())
      friends.add(friendsRes.getString("friend"))
    friendsRes.close()

    friends.forEach(friend => {
      val recommendationRes = stmt.executeQuery("Select friend from friends where username ='" + friend + "';")
      while (recommendationRes.next())
        if (!friends.contains(recommendationRes.getString("friend")) && recommendationRes.getString("friend") != user)
          recommendations.add(recommendationRes.getString("friend"))
    })

    if (recommendations.size() < 2)
    {
      val everyone = stmt.executeQuery("Select * from users;")
      while (everyone.next())
          if (everyone.getString("username") != user && !friends.contains(everyone.getString("username")))
            recommendations.add(everyone.getString("username"))
      everyone.close()
    }
    con.close()
    stmt.close()
    recommendations
  }

  def queryUserData(username: String): util.HashMap[String, Any] = {
    val map = new util.HashMap[String, Any]()

    map.put("friend", getConfirmedJson(username))
    map.put("request", getPendingJson(username))
    map.put("pending", getRequestedJson(username))

    map
  }

  def getConfirmedJson(user: String): util.ArrayList[String] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    val result = stmt.executeQuery("Select * from friends where username='" + user + "' and status='Confirmed';")
    val list = new util.ArrayList[String]()
    while (result.next())
      list.add(result.getString("friend"))

    result.close()
    con.close()
    stmt.close()
    list
  }

  def getPendingJson(user: String): util.ArrayList[String] =  {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    val result = stmt.executeQuery("Select * from friends where username='" + user + "' and status='Pending';")
    val list = new util.ArrayList[String]()
    while (result.next())
      list.add(result.getString("friend"))

    result.close()
    con.close()
    stmt.close()
    list
  }

  def getRequestedJson(user: String): util.ArrayList[String] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    val result = stmt.executeQuery("Select * from friends where friend='" + user + "' and status='Pending';")
    val list = new util.ArrayList[String]()
    while (result.next())
      list.add(result.getString("username"))

    result.close()
    con.close()
    stmt.close()
    list
  }

  def userExists(displayedUser: String): Boolean = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    val result = stmt.executeQuery("Select username from users where username='" + displayedUser + "';")
    val exists = result.next()
    result.close()
    con.close()
    stmt.close()
    exists
  }

  def queryAllUsersPosts(user: String): util.ArrayList[util.HashMap[String, Any]] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    /*
        [
          {
            "username" : "Alex",
            "message_body" : "Hi!",
            "time_stamp" : "41687687168",
            "post_id" : "1",
            "comments" : [
              "username" : "Smileyman5",
              "comment" : "What's up?!",
              "liked" : 1,
              "timestamp" : "54654684652"
            ]
          },
          {
            "username" : "Alex",
            "message_body" : "Hello!",
            "time_stamp" : "8746549846",
            "post_id" : "2",
            "comments" : [
              "username" : "JesGirl",
              "comment" : "SUP!? :P",
              "liked" : 0,
              "timestamp" : "13816484"
          }
        ]
     */
    val result = stmt.executeQuery("Select * from posts where username='" + user + "';")
    val list = new util.ArrayList[util.HashMap[String, Any]]()
    while (result.next())
    {
      val map = new util.HashMap[String, Any]()
      map.put("username", user)
      map.put("message_body", result.getString("message_body"))
      map.put("time_stamp", result.getTimestamp("time_stamp"))
      map.put("post_id", result.getInt("id"))
      map.put("comments", new util.ArrayList[util.HashMap[String, Any]]())
      list.add(map)
    }
    result.close()
    list.forEach(ele => {
      val arr = ele.get("comments").asInstanceOf[util.ArrayList[util.HashMap[String, Any]]]
      val res = stmt.executeQuery("Select * from likes_and_comments where post_id='" + ele.get("post_id") + "';")
      while (res.next())
      {
        val map = new util.HashMap[String, Any]()
        map.put("username", res.getString("username"))
        map.put("comment", res.getString("comment"))
        map.put("liked", res.getInt("liked"))
        map.put("time_stamp", res.getTimestamp("timestamp"))
        arr.add(map)
      }
      res.close()
    })

    // Friends posts
    val friendResult = stmt.executeQuery("Select * from friends where friend='" + user + "';")
    val usersFriends = new util.ArrayList[String]()
    while (friendResult.next())
      usersFriends.add(friendResult.getString("username"))
    friendResult.close()

    usersFriends.forEach(friend => {

      val friendPostResult = stmt.executeQuery("Select * from posts where username='" + friend + "';")

      val friendsPosts = new util.ArrayList[util.HashMap[String, Any]]()
      while (friendPostResult.next())
      {
        val map = new util.HashMap[String, Any]()
        map.put("username", friend)
        map.put("message_body", friendPostResult.getString("message_body"))
        map.put("time_stamp", friendPostResult.getTimestamp("time_stamp"))
        map.put("post_id", friendPostResult.getInt("id"))
        map.put("comments", new util.ArrayList[util.HashMap[String, Any]]())
        friendsPosts.add(map)
      }
      friendPostResult.close()

      friendsPosts.forEach(ele => {
        val arr = ele.get("comments").asInstanceOf[util.ArrayList[util.HashMap[String, Any]]]
        val res = stmt.executeQuery("Select * from likes_and_comments where post_id='" + ele.get("post_id") + "';")
        while (res.next())
        {
          val map = new util.HashMap[String, Any]()
          map.put("username", res.getString("username"))
          map.put("comment", res.getString("comment"))
          map.put("liked", res.getInt("liked"))
          map.put("time_stamp", res.getTimestamp("timestamp"))
          arr.add(map)
        }
        res.close()
      })
      // Adding on friends posts and the comments connected
      list.addAll(friendsPosts)
    })
    con.close()
    stmt.close()
    list.sort((o1: util.HashMap[String, Any], o2: util.HashMap[String, Any]) => {
      if (o1.get("time_stamp").asInstanceOf[Timestamp].before(o2.get("time_stamp").asInstanceOf[Timestamp])) 1 else -1
    })
    list
  }

  def executePostFromUser(username: String, message: String): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", this.username, password)
    val stmt = con.createStatement
    stmt.execute("INSERT INTO posts VALUES (NULL, '"+message+"', NULL, '" + username + "')")

    con.close()
    stmt.close()
  }

  def executeCommentOnPost(username: String, message: String, liked: Int, postId: Int): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", this.username, password)
    val stmt = con.createStatement
    stmt.execute(s"INSERT INTO likes_and_comments VALUES (NULL, '$username', '$message', $liked, NULL, $postId)")

    con.close()
    stmt.close()
  }

  def executeRemovePostById(postId: Int): Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", this.username, password)
    val stmt = con.createStatement

    val result = stmt.executeQuery(s"Select * from likes_and_comments where post_id=$postId")
    val comments = new util.ArrayList[Int]()
    while (result.next())
      comments.add(result.getInt("id"))

    comments.forEach(id => stmt.execute(s"DELETE FROM likes_and_comments where id=$id"))
    stmt.execute(s"DELETE FROM posts WHERE id=$postId;")

    con.close()
    stmt.close()
  }
}
