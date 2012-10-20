package controllers

import models.JsonObject
import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

/**
 * Control all actions related to showing, creating and deleting json objects.
 */
object JsonController extends Controller {
  /*
  implicit object JsonFormat extends Format[JsonObject] {
    def reads(json: JsValue) = JsonObject(
      (json \ "type").as[String],
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(jsonObject: JsonObject) = JsObject(Seq(
      "type" -> JsString(jsonObject.`type`),
      "cx" -> JsNumber(jsonObject.cx),
      "cy" -> JsNumber(jsonObject.cy)))
  }

  /**
   * Find all the elements from the database and create json object of each element, using Jerkson. This method can be
   * accessed by path /json.
   */
  def showAll = Action { implicit request =>
    val jsonElements = JsonObject.findAll
    Ok(toJson(jsonElements))
  }*/


  def getElements(elementType: String) = Action { implicit request =>
    elementType match {
      case "start" => Ok(toJson(Start.findAll))  
      case "end" => Ok(toJson(End.findAll))
      case "relation" => Ok(toJson(Relation.findAll))
      case "activity" => Ok(toJson(Activity.findAll))
      case _ => Ok("NotFound")
    }
  }
  
  def getElementsByModel(elementType: String, id: Int) = Action { implicit request =>
    elementType match {
      case "start" => Ok(toJson(Start.findByModel(id)))  
      case "end" => Ok(toJson(End.findByModel(id)))
      case "relation" => Ok(toJson(Relation.findByModel(id)))
      case "activity" => Ok(toJson(Activity.findByModel(id))) 
      case _ => Ok("NotFound")
    }
  }
}