package controllers

import play.libs.Json.toJson
import models._
import play.api._
import play.api.mvc._
import com.codahale.jerkson.Json._

object JsonController extends Controller {

  def showAll = Action { implicit request =>

   val jsonElements = JsonObject.findAll map {
     jsonObject => generate(jsonObject)
   }
 
   Ok(views.html.showJson(jsonElements))
  }
  
  def showElementByModel(id: Int) = Action { implicit request =>
   val jsonElements = JsonObject.findByModel(id) map {
     jsonObject => generate(jsonObject)
   }
   Ok(views.html.showJson(jsonElements))
  }
}