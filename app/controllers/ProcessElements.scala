package controllers

import service._
import play.api._
import play.api.mvc._
import service.ProcessElementService

object ProcessElements extends Controller {

  val processElementService = new ProcessElementService()

  //add size param here
  def create(modelId: Int, processId: Int, elemType: Int, value: String, x: Int, y: Int) = Action { implicit request =>
    processElementService.create(modelId, processId, elemType, value, x, y)
    Redirect(routes.Models.read(modelId))
  }

  def update(modelId: Int, processId: Int, id: Int, value: String, size: Int, x: Int, y: Int) = Action {
	processElementService.update(id, value, size, x, y)
    Redirect(routes.Models.read(modelId))
  }

  def delete() {
	  //TODO
  }

}

