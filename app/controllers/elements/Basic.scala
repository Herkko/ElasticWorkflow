package controllers.elements

import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._
import anorm.{ NotAssigned, Id, Pk}

import format.ProcessElementFormat

import app.actions.CORSAction
import models.ProcessElement

trait Basic extends Controller {

  import ProcessElementFormat._

  val typeName: String = ""
  val elementType: Int = 0

  def create() = CORSAction { implicit request =>
    request.body.asJson.map { json =>  
        val id = Json.fromJson(json) match {
          case e: ProcessElement => e.create
          case _ => throw new Exception("Reading ProcessElement from Json failed.")
        }
        Ok(toJson(ProcessElement.read(id)))
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  def read(id: Long) = CORSAction { implicit request =>
    ProcessElement.read(id).filter(e => e.elementTypeId == elementType) match {
      case Some(e) => Ok(toJson(e))
      case _ => Ok(JsNull)
    }
  }

  def update(id: Int) = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val id = Json.fromJson(json) match {
          case e: ProcessElement => e.update
          case _ => throw new Exception("Updating ProcessElement from Json failed.")
        }
        Ok(toJson(ProcessElement.read(id)))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  //add a check here that process element has a correct type!also delete its relations
  def delete(id: Long) = CORSAction { implicit request =>
    ProcessElement.delete(id)
    Ok(toJson(""))
  }

  def list = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.list())) 
  }
}

object Basic extends Controller {
  import ProcessElementFormat._

  def list = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.list())) 
  }
}