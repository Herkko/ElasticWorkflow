package controllers

import service._
import play.api._
import play.api.mvc._

import service.{ ProcessElementService }
import models.{ Model, Process }

object ProcessElements extends Controller {

  val processElementService = new ProcessElementService()

  def create(modelId: Long, processId: Long, elemType: Int, value: String, x: Int, y: Int) = Action { implicit request =>
    (Model.read(modelId), Process.read(processId)) match {
      case (Some(model), Some(process)) => {
        processElementService.create(modelId, processId, elemType, value, x, y)
        Redirect(routes.Models.read(modelId.toInt))
      }
      case _ => NotFound("Not found model id '" + modelId + "' or  process id '" + processId + "' when creating new element")
    }
  }

  def update(id: Int, value: String, size: Int, x: Int, y: Int) = Action {
    processElementService.read(id) match {
      case Some(element) => {
        processElementService.update(id, value, size, x, y)
        Redirect(routes.Models.read(processElementService.getModelId(id).toInt))
      }
      case None => NotFound("Not found element id '" + id + "' when updating element")
    }
  }

  def delete() {
    //TODO
  }
}

