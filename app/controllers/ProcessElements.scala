package controllers

import service._
import play.api._
import play.api.mvc._
import service.{ ModelService, ProcessService, ProcessElementService }

object ProcessElements extends Controller {

  val processElementService = new ProcessElementService()
  val modelService = new ModelService()
  val processService = new ProcessService()

  //ugly way to handle errors
  def create(modelId: Int, processId: Int, elemType: Int, value: String, x: Int, y: Int) = Action { implicit request =>
    (modelService.read(modelId), processService.read(processId)) match {
      case (Some(model), Some(process)) => {
        processElementService.create(modelId, processId, elemType, value, x, y)
        Redirect(routes.Models.read(modelId))
      }
      case (None, Some(process)) => handleError("model id '" + modelId + "' when creating new element")
      case (Some(model), None) => handleError("process id '" + processId + "' when creating new element")
      case (None, None) => handleError("model id '" + modelId + "' and  process id '" + processId + "' when creating new element")
    }
  }

  def update(id: Int, value: String, size: Int, x: Int, y: Int) = Action {
    processElementService.read(id) match {
      case Some(element) => {
        processElementService.update(id, value, size, x, y)
        Redirect(routes.Models.read(processElementService.getModelId(id)))
      }
      case None => handleError("element id '" + id + "' when updating element")
    }
  }

  def delete() {
    //TODO
  }

  def handleError(logInfo: String) = {
    val info = "Not found: " + logInfo + ". Thrown by: " + getClass.getName
    Logger.error(info)
    Redirect(routes.Models.list)
  }
}

