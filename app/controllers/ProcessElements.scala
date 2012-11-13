package controllers

import service._
import play.api._
import play.api.mvc._
import service.{ ModelService, ProcessService, ProcessElementService }

object ProcessElements extends Controller {

  val processElementService = new ProcessElementService()
  val modelService = new ModelService()
  val processService = new ProcessService()

  def create(modelId: Int, processId: Int, elemType: Int, value: String, x: Int, y: Int) = Action { implicit request =>
    (modelService.read(modelId), processService.read(processId)) match {
      case (Some(model), Some(process)) => {
        processElementService.create(modelId, processId, elemType, value, x, y)
        Redirect(routes.Models.read(modelId))
      }
      case _ => NotFound("Not found model id '" + modelId + "' or  process id '" + processId + "' when creating new element")
    }
  }

  def update(id: Int, value: String, size: Int, x: Int, y: Int) = Action {
    processElementService.read(id) match {
      case Some(element) => {
        processElementService.update(id, value, size, x, y)
        Redirect(routes.Models.read(processElementService.getModelId(id)))
      }
      case None => NotFound("Not found element id '" + id + "' when updating element")
    }
  }

  def delete() {
    //TODO
  }
}
