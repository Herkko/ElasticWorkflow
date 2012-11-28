package controllers

import play.api._
import play.api.mvc._
import anorm.{ NotAssigned, Id }
import java.util.Date

import models.{ Model, Process, Relation }

import app.actions.CORSAction
/**
 * Control all actions related to showing, creating and deleting models.
 */
object Models extends Controller {

  import format.ModelFormat._
  import format.DateFormat._

  /**
   * Add new model to database. Model id is auto-generated by Anorm.
   */
  //this should get json instead of always creating same model
  def create = CORSAction { implicit request =>
    val modelId = Model.create(new Model(NotAssigned, "Model", new Date()))
    Processes.createNewProcess(modelId)
    Redirect(routes.Models.read(modelId.toInt))
  }

  def read(id: Long) = Action { implicit request =>
    Model.read(id) match {
      case Some(model) => Ok(views.html.modelTest.details(model, Process.findByModelWithElements(id), Relation.findByModel(id)))
      case None => NotFound("This Model doesn't exist. Thrown by: " + getClass.getName + " when reading model from database.")
    }
  }

  def update() = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val Some(id) = (json \ "id").asOpt[Long]
        val Some(name) = (json \ "name").asOpt[String]
        val Some(date) = (json \ "dateCreated").asOpt[Date]

        Model(Id(id), name, date).update
        Redirect(routes.Models.read(id.toInt))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  //Not finished
  def delete(id: Long) = Action { implicit request =>
    Model.read(id) match {
      case Some(model) => {
        //actually delete model < to be ADDED modelService.delete(id)
        Redirect(routes.Models.list)
      }
      case None => NotFound("This Model doesn't exist. Thrown by: " + getClass.getName + " when deleting model.")
    }
  }

  def list = Action { implicit request =>
    Ok(views.html.modelTest.list(Model.list))
  }
}