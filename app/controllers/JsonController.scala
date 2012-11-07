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

  def getSwimlane = Action { implicit request =>
    Ok(toJson(Element.findType("Swimlane")))
  }

  def getSwimlaneByModel(id: Int) = Action { implicit request =>
    Ok(toJson(Element.findTypeByModel(id, "Swimlane")))
  }

  def getStart = Action { implicit request =>
    Ok(toJson(Element.findType("Start")))
  }

  def getStartByModel(id: Int) = Action { implicit request =>
    Ok(toJson(Element.findTypeByModel(id, "Start")))
  }

  def getEnd = Action { implicit request =>
    Ok(toJson(Element.findType("End")))
  }

  def getEndByModel(id: Int) = Action { implicit request =>
    Ok(toJson(Element.findTypeByModel(id, "End")))
  }

  def getActivity = Action { implicit request =>
    Ok(toJson(Element.findType("Activity")))
  }
  
  def getActivityByReleationId(id: Int) = Action { implicit request => 
    Ok(toJson(Element.findTypeById(id)))
  }

  def getActivityByModel(id: Int) = Action { implicit request =>
    Ok(toJson(Element.findTypeByModel(id, "Activity")))
  }

  def getGateway = Action { implicit request =>
    Ok(toJson(Element.findType("Gateway")))
  }

  def getGatewayByModel(id: Int) = Action { implicit request =>
    Ok(toJson(Element.findTypeByModel(id, "Gateway")))
  }

  def getRelation = Action { implicit request =>
    Ok(toJson(Relation.findAll))
  }

  def getRelationByModel(id: Int) = Action { implicit request =>
    Ok(toJson(Relation.findByModel(id)))
  }

  //refactor :/
  def toElement = Action { request =>
   println( request.body.asFormUrlEncoded)

    request.body.asJson match {
      case Some(data) => {
        println(data)
        Ok(data)

      }
      case None => NotFound("Not found")
    }
    //  val Some(s) = request.body.asJson
    //  println(s)
    // 	Ok(s)
  }

  /* def getElements(elementType: String) = Action { implicit request =>
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
  }*/
}