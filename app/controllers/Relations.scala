package controllers

import play.api._
import play.api.mvc._
import service.{ RelationService, ModelService }

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Relations extends Controller {

  val relationService = new RelationService
  val modelService = new ModelService

  /**
   * Add new relation to the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. //TODO add check that elementId actually exists.
   */
  def create(modelId: Int, element1: Int, element2: Int) = Action { implicit request =>
    if (modelService.exists(modelId)) {
      // if (Process.contains(processId)) {
      relationService.create(element1, element2, "Test relation")
      // } 
      Redirect(routes.Models.read(modelId))
    } else {
      Redirect(routes.Models.list)
    }

  }

  def update(modelId: Int, id: Int, value: String) = Action { implicit request =>
    relationService.update(id, value)
    Redirect(routes.Models.read(modelId))
  }
  
  /**
   * Delete new relation of the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. //TODO add check that id actually exists.
   */
  def delete(modelId: Int, id: Int) = Action { implicit request =>
    if (modelService.exists(modelId)) {
      relationService.delete(id)
      Redirect(routes.Models.read(modelId))
    } else {
      Redirect(routes.Models.list)
    }
  }
}