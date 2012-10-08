package controllers

import play.libs.Json.toJson
import models._
import play.api._
import play.api.mvc._
import com.codahale.jerkson.Json._

object ControllerJSON extends Controller {

  def showElement = Action { implicit request =>
   val processElements = ProcessElement.findAll map {
     processElement => generate(processElement)
   }
   Ok(views.html.showJson(processElements))
  }
}