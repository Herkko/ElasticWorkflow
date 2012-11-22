package controllers

import service._
import play.api._
import play.api.mvc._
import java.util.Date

import anorm.NotAssigned
import models.{ Model, ModelProcess, Process, Relation }

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Processes extends Controller {

  val processElementService = new ProcessElementService

  /**
   * Add new process to a model. Called when a new model is created, or user wants to add a process to existing model.
   * Redirects to a page that lists all models, if model with id equal to parameter modelId doesn't exist.
   */
  def create(modelId: Long) = Action { implicit request =>
    Model.read(modelId) match {
      case Some(model) => {
        createNewProcess(modelId)
        Redirect(routes.Models.read(modelId.toInt))
      }
      case None => NotFound("This Model doesn't exist. Thrown by: " + getClass.getName + " when creating new process.")
    }
  }

  def update(id: Int, name: String) = Action {
    Process.read(id) match {
      case Some(process) => {
        Process.update(id, name)
        Redirect(routes.Models.read(Process.getModelId(id).toInt))
      }
      case None => NotFound("This Process doesn't exist. Thrown by: " + getClass.getName + " when updating process.")
    }
  }

  def delete(id: Long) = Action { implicit request =>
    Process.read(id) match {
      case Some(process) => {
        val modelId = Process.getModelId(id)
        deleteProcess(id)
        Redirect(routes.Models.read(modelId.toInt))
      }
      case None => NotFound("This Process doesn't exist. Thrown by: " + getClass.getName + " when deleting process.")
    }
  }

  /**
   * Delete process along with all the data it contains.
   */
  def deleteProcess(id: Long) = {
    Relation.deleteByProcess(id)
    processElementService.deleteByProcess(id.toInt)
    Process.delete(id)
  }
  
  /**
   * Create new process and set it to belong to a model specified by parameter modelId. Each process by default contains
   * SwimLane and StartElement and no relations.
   */
  def createNewProcess(modelId: Long) = {
    val y = (Process.countByModel(modelId) * 300 + 20).toInt
    
   
    val processId: Long = Process.create(new Process(NotAssigned, "Process", new Date()))
    ModelProcess.create(new ModelProcess(NotAssigned, modelId, processId))
    
    
    val elem1 = processElementService.create(modelId, processId, 1, "Swimlane", 20, y)
    val elem2 = processElementService.create(modelId, processId, 2, "Start", 70, y + 110)
    val elem3 = processElementService.create(modelId, processId, 3, "End", 480, y + 110)
    val elem4 = processElementService.create(modelId, processId, 4, "Activity 1", 170, y + 90)
    val elem5 = processElementService.create(modelId, processId, 4, "Activity 2", 250, y + 90)
    val elem6 = processElementService.create(modelId, processId, 4, "Activity 3", 320, y + 90)
    val elem7 = processElementService.create(modelId, processId, 5, "Gateway", 300, y + 30)

    processElementService.update(elem5, "HI, I have been modified, YAY!", 10, 0, 0)
    val rel = Relation.create(new Relation(NotAssigned, elem2, elem4, 1, "First Relation"))
    val rel1 = Relation.create(new Relation(NotAssigned, elem4, elem5, 1, "Second Relation"))
    val rel2 = Relation.create(new Relation(NotAssigned, elem5, elem6, 1, "Third Relation"))
    val rel3 = Relation.create(new Relation(NotAssigned, elem6, elem3, 1, "Fourth Relation"))
    val rel4 = Relation.create(new Relation(NotAssigned, elem5, elem7, 1, "Fifth Relation"))//something weird with this relation 5 -> 5??
  }
}