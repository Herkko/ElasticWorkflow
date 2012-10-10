package controllers

import play.libs.Json.toJson
import models._
import play.api._
import play.api.mvc._
import com.codahale.jerkson.Json._

/**
 * Control all actions related to showing, creating and deleting json objects.
 */
object JsonController extends Controller {

  /**
   * Find all the elements from the database and create json object of each element, using Jerkson. This method can be
   * accessed by path /json.
   */
  def showAll = Action { implicit request =>
   val jsonElements = JsonObject.findAll map {
     jsonObject => generate(jsonObject)
   }
   Ok(views.html.showJson(jsonElements))
  }
  
  /**
   * Find all the elements of the model specified by parameter id. This method can be accessed by path /json/:id.
   */
  def showElementByModel(id: Int) = Action { implicit request =>
   val jsonElements = JsonObject.findByModel(id) map {
     jsonObject => generate(jsonObject)
   }
   Ok(views.html.showJson(jsonElements))
  }
}