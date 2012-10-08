package controllers

import play.libs.Json.toJson
import models._
import play.api._
import play.api.mvc._
import com.codahale.jerkson.Json._

object ControllerJSON extends Controller {

  def showElement = Action { implicit request =>
    val json = generate(
      Map(
        "url" -> "http://nytimes.com",
        "attributes" -> Map(
          "name" -> "nytimes",
          "country" -> "US",
          "id" -> 25),
        "links" -> List(
          "http://link1",
          "http://link2")))
   println("LALA " + json)
   Ok(views.html.showJson(json))
  }
}