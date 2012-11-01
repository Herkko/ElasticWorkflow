package controllers

import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._
import collection.mutable.HashMap
/**
 * Control all actions related to showing, creating and deleting json objects.
 */
object JsonController extends Controller {
  
  //val map = new HashMap[String, Element]() //withDefaultValue("")
  
  def list() = Action { implicit request =>
    val elements = Element.findAll
    val relations = Relation.findAll
    Ok(toJson(Seq(toJson(elements), toJson(relations))))
  }
 
  //TODO: how to refactor??
  def getElements(elementType: String) = Action { implicit request =>
    elementType match {
      case "swimlane" => Ok(toJson(Swimlane.findAll))
      case "start" => Ok(toJson(Start.findAll))  
      case "end" => Ok(toJson(End.findAll))
      case "relation" => Ok(toJson(Relation.findAll))
      case "activity" => Ok(toJson(Activity.findAll))
      case "gateway" => Ok(toJson(Gateway.findAll))
      case _ => Ok("NotFound")
    }
  }
    
  def getElementsByModel(elementType: String, id: Int) = Action { implicit request =>
    elementType match {
      case "swimlane" => Ok(toJson(Swimlane.findByModel(id)))
      case "start" => Ok(toJson(Start.findByModel(id)))  
      case "end" => Ok(toJson(End.findByModel(id)))
      case "relation" => Ok(toJson(Relation.findByModel(id)))
      case "activity" => Ok(toJson(Activity.findByModel(id))) 
      case "gateway" => Ok(toJson(Gateway.findByModel(id)))
      case _ => Ok("NotFound")
    }
  }
}