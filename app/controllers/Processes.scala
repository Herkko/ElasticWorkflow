package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Processes extends Controller {

  /**
   * Add new process to a model. Called when a new model is created, or user wants to add a process to existing model.
   * Redirects to a page that lists all models, if model with id equal to parameter modelId doesn't exist.
   * to fix: processId isnt used anywhere, database still determines id itself.
   */
  def create(modelId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      createNewProcess(modelId)
      Redirect(routes.Models.read(modelId))
    } else {
      Redirect(routes.Models.list)
    }
  }
  
  /*
   * these two methods can be replaced with a method that takes function as parameter
   */
  def delete(modelId: Int, processId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      Relation.deleteByProcess(processId)
      ProcessElement.deleteByProcess(processId)
      ModelProcess.deleteByProcess(processId)
      Process.delete(processId)
      Redirect(routes.Models.read(modelId))
    } else {
      Redirect(routes.Models.list)
    }
  }

  /**
   * Create new process and set it to belong to a model specified by parameter modelId. Each process by default contains
   * SwimLane and StartElement and no relations.
   */
  def createNewProcess(modelId: Int) = {
    var yCoord = (Process.countByModel(modelId)) * 220 + 20
    val processId: Int = Process.create(Process(NotAssigned, "Process", new Date()))
    val modelProcessId: Int = ModelProcess.create(ModelProcess(NotAssigned, modelId, processId, new Date()))

    val processRelId1 = ProcessElement.create(ProcessElement(modelProcessId, 1, NotAssigned, "Swimlane", 0, 20, yCoord))
    val processRelId2 = ProcessElement.create(ProcessElement(modelProcessId, 2, NotAssigned, "Start Element", 0, 70, yCoord + 70))
  }
}