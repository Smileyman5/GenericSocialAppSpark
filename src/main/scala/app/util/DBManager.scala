package app.util

import java.sql.DriverManager
import java.util

/**
  * Created by alex on 5/8/2017.
  */
object DBManager {

  val username = "smileyman5"
  val password = "password"

  def queryAllUsers(): util.ArrayList[String] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "smileyman5", "password")
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

  def queryRecommendations(username: String): util.HashSet[String] = {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "smileyman5", "password")
    val stmt = con.createStatement

    val friendsRes = stmt.executeQuery("Select * from friends where friend='" + username + "';")
    val friends = new util.HashSet[String]()
    val recommendations = new util.HashSet[String]()

    while (friendsRes.next())
      friends.add(friendsRes.getString("username"))
    friendsRes.close()

    friends.forEach(friend => {
      val recommendationRes = stmt.executeQuery("Select friend from friends where username ='" + friend + "';")
      while (recommendationRes.next())
        if (!friends.contains(recommendationRes.getString("friend")) && recommendationRes.getString("friend") != username)
          recommendations.add(recommendationRes.getString("friend"))
    })

    if (recommendations.size() < 2)
    {
      val everyone = stmt.executeQuery("Select * from users;")
      while (everyone.next())
          if (everyone.getString("username") != username)
            recommendations.add(everyone.getString("username"))
      everyone.close()
    }
    con.close()
    stmt.close()
    recommendations
  }

  def queryUserData(username: String): util.HashMap[String, Any] = {
    val map = new util.HashMap[String, Any]()
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "smileyman5", "password")
    val stmt = con.createStatement

    val result = stmt.executeQuery("Select * from users where username='" + username + "';")
    if (result.next())
    {
      map.put("username", username)
      map.put("birthday", result.getString("birthday"))
      map.put("firstName", result.getString("first_name"))
      map.put("lastName", result.getString("last_name"))
      map.put("gender", result.getString("gender"))
      map.put("login", result.getInt("login"))
    }
    result.close()
    con.close()
    stmt.close()
    map
  }
}
