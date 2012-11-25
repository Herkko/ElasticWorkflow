package controllers

import app.actions.CORSAction
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

import format.ProcessElementFormat
import anorm.NotAssigned

import models.{ Model, Process, ProcessElement }


object ProcessElements extends Controller {

  def create(modelId: Long, processId: Long, elemType: Int, value: String, x: Int, y: Int) = Action { implicit request =>
    (Model.read(modelId), Process.read(processId)) match {
      case (Some(model), Some(process)) => {
        val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
        ProcessElement.create(new ProcessElement(NotAssigned, modelProcessId, elemType, value, 0, x, y))
 
        Redirect(routes.Models.read(modelId.toInt))
      }
      case _ => NotFound("Not found model id '" + modelId + "' or  process id '" + processId + "' when creating new element")
    }
  }

  def create(id: Int) = //Action { implicit request =>
  /*  ProcessElement.create(new ProcessElement(id: Pk[Long],
      modelProcessId: Long,
      elementTypeId: Long,
      value: String,
      size: Int,
      x: Int,
      y: Int))
    request.body.asJson.map { json => {
      val id = (json \ "id")
      val modelProcessId = (json \ "id")
    }}*/
    Action { implicit request =>
      request.body.asJson.map { requestEntity => {
        println("requestEntiry");
        Ok("Good data")
      }
      }.getOrElse(Ok("Wrong data"))
    
  }
  
  def update(id: Int, value: String, size: Int, x: Int, y: Int) = Action {
    ProcessElement.read(id) match {
      case Some(element) => {
        ProcessElement.update(id, value, size, x, y)
        Redirect(routes.Models.read(ProcessElement.getModelId(id).toInt))
      }
      case None => NotFound("Not found element id '" + id + "' when updating element")
    }
  }

  def delete() {
    //TODO
  }
  
    
  def list() = CORSAction { implicit request =>
    import ProcessElementFormat._
    Ok(toJson(ProcessElement.list))
  }
}

