package controllers.elements

//import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

import service.{ ProcessElementService }
import models.{ ProcessElement }
import app.actions.CORSAction

import anorm.NotAssigned
import format.ProcessElementFormat

object Gateway extends Controller {

  import ProcessElementFormat._

  def create() = CORSAction {  implicit request =>
    request.body.asJson.map { json =>
      {
        val modelProcessId = (json \ "modelProcessId").asOpt[Int].getOrElse(1)
        val elementTypeId = (json \ "elementTypeId").asOpt[Int].getOrElse(5)
        val value = (json \ "value").asOpt[String].getOrElse("Gateway")
        val size = (json \ "size").asOpt[Int].getOrElse(0)
        val x = (json \ "cx").asOpt[String].getOrElse("100")
        val y = (json \ "cy").asOpt[String].getOrElse("100")

        ProcessElement.create(new ProcessElement(NotAssigned, modelProcessId, elementTypeId, value, size, x.toInt, y.toInt))
        Ok(views.html.edit())
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
    
  }

  def read(id: Long) = CORSAction { implicit request =>
     Ok(toJson(ProcessElement.read(id)))
 }

  def update(id: Int) = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val Some(id) = (json \ "id").asOpt[Int]
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

  def delete(id: Long) =  CORSAction { implicit request =>
  	Ok(toJson(ProcessElement.delete(id)))
  }

  def list = CORSAction { implicit request =>
  	Ok(toJson(ProcessElement.findType("Gateway")))
  }
}