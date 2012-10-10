package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

object Processes extends Controller {

  var xCoord = 20
  
  //Add new process to model
  def add(modelId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      createNewProcess(modelId)
      Redirect(routes.Models.show(modelId))
    } else {
      Redirect(routes.Models.list)
    }
  }
  
  //Create new process with two elements
  def createNewProcess(modelId: Int) = {
      val processId: Int = Process.insert(Process(NotAssigned, "Process", new Date()))
      val modelProcessId: Int = ModelProcess.insert(ModelProcess(NotAssigned, modelId, processId, new Date()))
      
      //Adding two elements to new process
      val processRelId1 = ProcessElement.insert(ProcessElement(modelProcessId, 1, NotAssigned, "Swimlane", 0, xCoord, 20))
      val processRelId2 = ProcessElement.insert(ProcessElement(modelProcessId, 2, NotAssigned, "Start Element", 0, xCoord+50, 70))
      xCoord += 200
  }
}