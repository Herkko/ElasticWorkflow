package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Relations extends Controller {

  /**
   * Add new relation to the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. //TODO add check that elementId actually exists.
   */
  def create(modelId: Int, elementId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      // if (Process.contains(processId)) {
      Relation.create(Relation(NotAssigned, 1, 100, 200, "Test relation", elementId))
      // } 
      Redirect(routes.Models.read(modelId))
    } else {
      Redirect(routes.Models.list)
    }

  }

  /**
   * Delete new relation of the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. //TODO add check that id actually exists.
   */
  def delete(modelId: Int, id: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      Relation.delete(id)
      Redirect(routes.Models.read(modelId))
    } else {
      Redirect(routes.Models.list)
    }
  }
}