package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

/**
 * Control all actions related to showing, creating and deleting models.
 *
 */
object Models extends Controller {
  
  /**
   * List all the models from database 
   */
  def list = Action { implicit request =>
    Ok(views.html.modelTest.list(Model.findAll))
  }

  /**
   * Show data about model specified by parameter id. Data includes model itself, model processes 
   * and their elements, model relations. Processes and elements are grouped in tuples, so that each tuple
   * consists of a process and a set of elements which belong to it.
   * If model with a given id doesn't exist, redirects to a page that lists all models.
   */
  def show(id: Int) = Action { implicit request =>
    if (Model.contains(id)) {
      val model = Model.findById(id)
      val relations = Relation.findByModel(id)
      Ok(views.html.modelTest.details(model, Process.findByModelWithElements(model.id.toString().toInt), relations))
    } else {
      Redirect(routes.Models.list())
    }
  }

  /**
   * Add new model to database. Model id is auto-generated by Anorm. Model is automatically assigned one process,
   * which consists of SwimLane and StartElement elements.
   */
  def add = Action { implicit request =>
    val modelId: Int = Model.insert(Model(NotAssigned, "Model", new Date()))
    Redirect(routes.Processes.add(modelId))
  }
}