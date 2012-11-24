package controllers.elements

import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._
import anorm.NotAssigned

import format.ProcessElementFormat

import app.actions.CORSAction
import models.ProcessElement
/*trait Basic {

  def create()
  def read(id: Int)
  def update(id: Int)
  def delete(id: Int)
  def list()
  
}*/

trait Basic extends Controller {

  import ProcessElementFormat._

  val typeName: String
  val elementType: Int

  def create() = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val modelProcessId = (json \ "modelProcessId").asOpt[Int].getOrElse(1)
        val elementTypeId = (json \ "elementTypeId").asOpt[Int].getOrElse(elementType)
        val value = (json \ "value").asOpt[String].getOrElse(typeName)
        val size = (json \ "size").asOpt[Int].getOrElse(0)
        val x = (json \ "cx").asOpt[String].getOrElse("100")
        val y = (json \ "cy").asOpt[String].getOrElse("100")

        val newId = ProcessElement(NotAssigned, modelProcessId, elementTypeId, value, size, x.toInt, y.toInt).create
       
   //     ProcessElement.create(new ProcessElement(NotAssigned, modelProcessId, elementTypeId, value, size, x.toInt, y.toInt))
       // Ok(views.html.edit())
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
        Ok(views.html.edit())
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }
  
  //add a check here that process element has a correct type!
  def delete(id: Long) = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.delete(id)))
  }

  def list = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.findType(typeName)))
  }

}