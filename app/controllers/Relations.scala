package controllers

import play.api._
import play.api.mvc._
import service.{ RelationService, ModelService, ProcessElementService }

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Relations extends Controller {

  val relationService = new RelationService
  val modelService = new ModelService
  val processElementService = new ProcessElementService

  /**
   * Add new relation to the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. 
   */
  def create(modelId: Int, start: Int, end: Int, value: String) = Action { implicit request =>
    (modelService.read(modelId), processElementService.read(start), processElementService.read(end)) match {
      case (Some(model), Some(element1), Some(element2)) => {
        relationService.create(start, end, value)
        Redirect(routes.Models.read(modelId))
      }
      case _ => NotFound("This Model or Element doesn't exist. Thrown by: " + getClass.getName + " when creating new relation.")
    }
  }

  def update(id: Int, start: Int, end: Int, value: String) = Action { implicit request =>
    (relationService.read(id), processElementService.read(start), processElementService.read(end)) match {
      case (Some(model), Some(element1), Some(element2)) => {
        relationService.update(id, start, end, value)
        Redirect(routes.Models.read(relationService.getModelId(id)))
      }
      case _ => NotFound("This Relation doesn't exist. Thrown by: " + getClass.getName + " when updating relation.")
    }
  }

  /**
   * Delete new relation of the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. 
   */
  def delete(id: Int) = Action { implicit request =>
    relationService.read(id) match {
      case Some(relation) => {
        val modelId = relationService.getModelId(id)
        relationService.delete(id)
        Redirect(routes.Models.read(modelId))
      }
      case None => NotFound("This Relation doesn't exist. Thrown by: " + getClass.getName + " when deleting relation.")
    }
  }
}