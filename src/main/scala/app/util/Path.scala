package app.util

import lombok._

/**
  * Created by alex on 5/7/2017.
  */
object Path {

  object Web {
    @Getter val INDEX = "/"
    @Getter val PROFILE = "/profile/"
    @Getter val LOGOUT = "/logout/"
    @Getter val FRIENDS = "/friends/"
    @Getter val SETTINGS = "/settings/"
    @Getter val HOME = "/home/"
    @Getter val USER = "/user/"
    @Getter val LOGIN = "/login/"
    @Getter val POST = "/post/"
    @Getter val RESTFUL = "/restful/"
    @Getter val RPROFILE = "/profile/:username/"
    @Getter val RRECOMMEND = "/recommend/:username/"
    @Getter val RSEARCH = "/search/:username/"
    @Getter val REGISTER = "/register/"

  }

  object Template {
    val INDEX = "/velocity/index/index.vm"
    val LOGIN = "/velocity/login/login.vm"
    val NOT_FOUND = "/velocity/notFound.vm"

    val HOME_PROFILE = "/velocity/home/profile.vm"
    val HOME_SETTINGS = "/velocity/home/settings.vm"
    val HOME_FRIENDS = "/velocity/home/friends.vm"

    val USER_PROFILE = "/velocity/user/profile.vm"
    val USER_FRIENDS = "/velocity/user/friends.vm"
  }

}
