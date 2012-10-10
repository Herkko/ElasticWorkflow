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
   */
  def add(modelId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      createNewProcess(modelId)
      Redirect(routes.Models.show(modelId))
    } else {
      Redirect(routes.Models.list)
    }
  }

  /**
   * Create new process and set it to belong to a model specified by parameter modelId. Each process by default contains
   * SwimLane and StartElement and no relations.
   */
  def createNewProcess(modelId: Int) = {
    var xCoord = (Process.countByModel(modelId)) * 200 + 20
    val processId: Int = Process.insert(Process(NotAssigned, "Process", new Date()))
    val modelProcessId: Int = ModelProcess.insert(ModelProcess(NotAssigned, modelId, processId, new Date()))

    val processRelId1 = ProcessElement.insert(ProcessElement(modelProcessId, 1, NotAssigned, "Swimlane", 0, xCoord, 20))
    val processRelId2 = ProcessElement.insert(ProcessElement(modelProcessId, 2, NotAssigned, "Start Element", 0, xCoord + 50, 70))
  }
}