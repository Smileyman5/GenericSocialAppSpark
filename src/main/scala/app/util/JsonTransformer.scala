package app.util

import com.google.gson.Gson

/**
  * Created by alex on 5/8/2017.
  */
object JsonTransformer {
  def toJson(`object`: Any): String = new Gson().toJson(`object`)

  def fromJson[T <: Object](json: String, classe: Class[T]): T = new Gson().fromJson(json, classe)
}

