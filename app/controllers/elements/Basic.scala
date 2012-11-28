package controllers.elements

import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._
import anorm.NotAssigned

import format.ProcessElementFormat

import app.actions.CORSAction
import models.ProcessElement

trait Basic extends Controller {

  import ProcessElementFormat._

  val typeName: String = ""
  val elementType: Int = 0

  def create() = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val modelProcessId = (json \ "modelProcessId").asOpt[Int].getOrElse(1)
        val elementTypeId = (json \ "elementTypeId").asOpt[Int].getOrElse(elementType)
        val value = (json \ "value").asOpt[String].getOrElse(typeName)
        val size = (json \ "size").asOpt[Int].getOrElse(0)
        val x = (json \ "cx").asOpt[Int].get
        val y = (json \ "cy").asOpt[Int].get

        val newId = ProcessElement(NotAssigned, modelProcessId, elementTypeId, value, size, x, y).create

        Ok(toJson(ProcessElement.read(newId)))
      }
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
        val Some(id) = (json \ "id").asOpt[Int]
        //  val Some(modelProcessId) = (json \ "modelProcessId").asOpt[Int]
        //  val Some(elementTypeId) = (json \ "elementTypeId").asOpt[Int]
        val Some(value) = (json \ "value").asOpt[String]
        val Some(size) = (json \ "size").asOpt[Int]
        val Some(x) = (json \ "cx").asOpt[String]
        val Some(y) = (json \ "cy").asOpt[String]

        ProcessElement.update(id, value, size, x.toInt, y.toInt)
        Ok(toJson(ProcessElement.read(id)))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  //add a check here that process element has a correct type!also delete its relations
  def delete(id: Long) = CORSAction { implicit request =>
    ProcessElement.delete(id)
    // Ok(views.html.edit())
    Ok(toJson(""))
  }

  def list = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.list())) //findType(typeName)))
  }
}

object Basic extends Controller {
  import ProcessElementFormat._

  def list = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.list())) //findType(typeName)))
  }
}