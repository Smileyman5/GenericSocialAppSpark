package app.util

import java.sql.DriverManager
import java.util

import app.util.DBManager.username

/**
  * Created by alex on 5/8/2017.
  */
object DBManager {

  val username = "root"
  val password = ""

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
    val con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", username, password)
    val stmt = con.createStatement

    val friendsRes = stmt.executeQuery("Select * from friends where friend='" + user + "';")
    val friends = new util.HashSet[String]()
    val recommendations = new util.HashSet[String]()

    while (friendsRes.next())
      friends.add(friendsRes.getString("username"))
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
          if (everyone.getString("username") != user)
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
    map.put("request", getRequestedJson(username))
    map.put("pending", getPendingJson(username))

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
}
