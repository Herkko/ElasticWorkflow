package controllers

import service._
import play.api._
import play.api.mvc._

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Processes extends Controller {

  val processService = new ProcessService
  val processElementService = new ProcessElementService
  val relationService = new RelationService
  val modelService = new ModelService

  /**
   * Add new process to a model. Called when a new model is created, or user wants to add a process to existing model.
   * Redirects to a page that lists all models, if model with id equal to parameter modelId doesn't exist.
   */
  def create(modelId: Int) = Action { implicit request =>
    modelService.read(modelId) match {
      case Some(model) => {
        createNewProcess(modelId)
        Redirect(routes.Models.read(modelId))
      }
      case None => NotFound("This Model doesn't exist. Thrown by: " + getClass.getName + " when creating new process.")
    }
  }

  def update(id: Int, name: String) = Action {
    processService.read(id) match {
      case Some(process) => {
        processService.update(id, name)
        Redirect(routes.Models.read(processService.getModelId(id)))
      }
      case None => NotFound("This Process doesn't exist. Thrown by: " + getClass.getName + " when updating process.")
    }
  }

  def delete(id: Int) = Action { implicit request =>
    processService.read(id) match {
      case Some(process) => {
        val modelId = processService.getModelId(id)
        deleteProcess(id)
        Redirect(routes.Models.read(modelId))
      }
      case None => NotFound("This Process doesn't exist. Thrown by: " + getClass.getName + " when deleting process.")
    }
  }

  /**
   * Delete process along with all the data it contains.
   */
  def deleteProcess(id: Int) = {
    relationService.deleteByProcess(id)
    processElementService.deleteByProcess(id)
    processService.delete(id)
  }
  
  /**
   * Create new process and set it to belong to a model specified by parameter modelId. Each process by default contains
   * SwimLane and StartElement and no relations.
   */
  def createNewProcess(modelId: Int) = {
    val y = (processService.countByModel(modelId)) * 220 + 20

    val processId = processService.create(modelId);
    
    val elem1 = processElementService.createSwimlane(modelId, processId, 20, y)
    val elem2 = processElementService.createStart(modelId, processId, 70, y + 110)
    val elem3 = processElementService.createEnd(modelId, processId, 480, y + 110)
    val elem4 = processElementService.createActivity(modelId, processId, 170, y + 90)
    val elem5 = processElementService.createActivity(modelId, processId, 250, y + 90)
    val elem6 = processElementService.createActivity(modelId, processId, 320, y + 90)
    val elem7 = processElementService.createGateway(modelId, processId, 300, y + 30)

    processElementService.update(elem5, "HI, I have been modified, YAY!", 10, 0, 0)
    val rel = relationService.create(elem2, elem3, "Relation between Start and End")
    
  }
}