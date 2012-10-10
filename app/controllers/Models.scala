package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

object Models extends Controller {
  
  //List all models from database
  def list = Action { implicit request =>
    Ok(views.html.modelTest.list(Model.findAll))
  }

  //Show data about one model (process, elements..). Doesn't show relations yet.
  def show(id: Int) = Action { implicit request =>
    if (Model.contains(id)) {
      val model = Model.findById(id)
      val relations = Relation.findByModel(id)
      Ok(views.html.modelTest.details(model, Process.findByModel(model.id.toString().toInt), relations))
    } else {
      Redirect(routes.Models.list())
    }
  }

  //Add new model to database
  def add = Action { implicit request =>
    val modelId: Int = Model.insert(Model(NotAssigned, "Model", new Date()))
    Redirect(routes.Processes.add(modelId))
  }
}