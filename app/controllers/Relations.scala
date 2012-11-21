package controllers

import play.api._
import play.api.mvc._
import anorm.NotAssigned
import service.{ ProcessElementService }
import models.{ Model, Relation }
/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Relations extends Controller {

  val processElementService = new ProcessElementService

  /**
   * Add new relation to the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. 
   */
  def create(modelId: Long, start: Long, end: Long, value: String) = Action { implicit request =>
    (Model.read(modelId), processElementService.read(start), processElementService.read(end)) match {
      case (Some(model), Some(element1), Some(element2)) => {
        Relation.create(new Relation(NotAssigned, start, end, 1, value))
        Redirect(routes.Models.read(modelId.toInt))
      }
      case _ => NotFound("This Model or Element doesn't exist. Thrown by: " + getClass.getName + " when creating new relation.")
    }
  }

  def update(id: Int, start: Int, end: Int, value: String) = Action { implicit request =>
    (Relation.read(id), processElementService.read(start), processElementService.read(end)) match {
      case (Some(model), Some(element1), Some(element2)) => {
        Relation.update(id, start, end, value)
        Redirect(routes.Models.read(Relation.getModelId(id).toInt))
      }
      case _ => NotFound("This Relation doesn't exist. Thrown by: " + getClass.getName + " when updating relation.")
    }
  }

  /**
   * Delete new relation of the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. 
   */
  def delete(id: Long) = Action { implicit request =>
    Relation.read(id) match {
      case Some(relation) => {
        val modelId = Relation.getModelId(id)
        Relation.delete(id)
        Redirect(routes.Models.read(modelId.toInt))
      }
      case None => NotFound("This Relation doesn't exist. Thrown by: " + getClass.getName + " when deleting relation.")
    }
  }
}