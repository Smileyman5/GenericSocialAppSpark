import spark.Spark.{get, port}

/**
  * Created by alex on 5/2/2017.
  */
object Main {
  def main(args: Array[String]): Unit = {
    port(8080)
    get("/hello/:msg", (req, _) => "<p>Hi World! " + req.params("msg") + " :D</p>")
    get("/hello/*/bye", (_, _) => "<p>Peace out World! :D</p>")
  }
}
