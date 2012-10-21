package controllers

import service.{ProcessService, ProcessElementService, RelationService}
import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Processes extends Controller {

  val processService = new ProcessService
  val processElementService = new ProcessElementService
  val relationService = new RelationService

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
    val y = (Process.countByModel(modelId)) * 220 + 20

    val modelProcess = processService.create(modelId);
    
    val elem1 = processElementService.createSwimlane(modelProcess, 20, y);
    val elem2 = processElementService.createStart(modelProcess, 70, y + 70);
    val elem3 = processElementService.createEnd(modelProcess, 270, y + 70);
    val elem4 = processElementService.createActivity(modelProcess, 170, y + 90);

    val rel = relationService.create(70, y + 70, 170, y + 90, "Relation between Start and Activity", elem2, elem3);
  }
  
    /*
  def createNewProcessOld(modelId: Int) = {
    var yCoord = (Process.countByModel(modelId)) * 220 + 20
    //val processId = Process.create(Process(NotAssigned, "Process", new Date()))
    //val modelProcess = ModelProcess.create(ModelProcess(NotAssigned, modelId, processId, new Date()))

    val processId = Process.create(Process(NotAssigned, "Process", new Date()))
    val modelProcess = ModelProcess.create(ModelProcess(NotAssigned, modelId, processId, new Date()))

    ProcessElement.create(ProcessElement(modelProcess, 1, NotAssigned, "Swimlane", 0, 20, yCoord))
    val relId1 = ProcessElement.create(ProcessElement(modelProcess, 2, NotAssigned, "Start Element", 0, 70, yCoord + 70))

    val relId2 = ProcessElement.create(ProcessElement(modelProcess, 3, NotAssigned, "End Element", 0, 270, yCoord + 70))
    val relId3 = ProcessElement.create(ProcessElement(modelProcess, 4, NotAssigned, "Activity Element", 0, 170, yCoord + 90))

    //?? all info is repeated twice in relations?
    Relation.create(Relation(NotAssigned, 1, 70, yCoord + 70, 170, yCoord + 90, "first relation", relId1))
    Relation.create(Relation(NotAssigned, 1, 70, yCoord + 70, 170, yCoord + 90, "first relation", relId3))
  }*/
}