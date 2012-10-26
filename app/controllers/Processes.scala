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
   * to fix: processId isnt used anywhere
   */
  def create(id: Int) = Action { implicit request =>
    if (modelService.exists(id)) {
      createNewProcess(id)
      Redirect(routes.Models.read(id))
    } else {
      Redirect(routes.Models.list)
    }
  }

  /*
   * these two methods can be replaced with a method that takes function as parameter
   */
  def delete(modelId: Int, id: Int) = Action { implicit request =>
    if (modelService.exists(modelId)) {
      relationService.deleteByProcess(id)
      processElementService.deleteByProcess(id)
      processService.delete(id)
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
    val y = (processService.countByModel(modelId)) * 220 + 20

    val modelProcess = processService.create(modelId);

    val elem1 = processElementService.createSwimlane(modelProcess, 20, y);
    val elem2 = processElementService.createStart(modelProcess, 70, y + 70);
    val elem3 = processElementService.createEnd(modelProcess, 270, y + 70);
    val elem4 = processElementService.createActivity(modelProcess, 170, y + 90);
    val elem5 = processElementService.createActivity(modelProcess, 250, y + 90);
    val elem6 = processElementService.createActivity(modelProcess, 320, y + 90);
    //val elem6 = processElementService.createGateway(modelProcess, 300, y + 30);
    
    val rel = relationService.create(70, y + 70, 170, y + 90, "Relation between Start and End?", elem2, elem3);
  }
}