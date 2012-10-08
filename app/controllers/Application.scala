package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  //List all models from database
  def listModels = Action { implicit request =>
    Ok(views.html.modelTest.list(Model.findAll))
  }

  //Show data about one model (process, elements..). Doesn't show relations yet.
  def showModel(id: Int) = Action { implicit request =>
    if (Model.contains(id)) {
      val model = Model.findById(id);
      Ok(views.html.modelTest.details(model, Process.findByModel(model.id.toString().toInt)))
    } else {
      Redirect(routes.Application.listModels)
    }
  }

  //Add new model to database
  def addModel = Action { implicit request =>
    val modelId: Int = Model.insert(Model(NotAssigned, "Model", new Date()))
    Redirect(routes.Application.addProcessToModel(modelId))
  }

  //Add new process to model
  def addProcessToModel(modelId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      createNewProcess(modelId)
      Redirect(routes.Application.showModel(modelId))
    } else {
      Redirect(routes.Application.listModels)
    }
  }
  
  //Create new process with two elements
  def createNewProcess(modelId: Int) = {
      val processId: Int = Process.insert(Process(NotAssigned, "Process", new Date()))
      val modelProcessId: Int = ModelProcess.insert(ModelProcess(NotAssigned, modelId, processId, new Date()))

      //Adding two elements to new process
      ProcessElement.insert(ProcessElement(modelProcessId, 1, 0, "Swimlane", 0, 0, 0))
      ProcessElement.insert(ProcessElement(modelProcessId, 2, 0, "Start Element", 0, 0, 0))
  }

  //Try to remove slash from the end of URL to see if path will work
  def removeSlash(path: String) = Action {
    val ending = path.charAt(path.length() - 1)
    if (ending == '/')
      Redirect('/' + path.substring(0, path.length() - 1));
    else
      NotFound
  }
}