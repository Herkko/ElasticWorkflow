package controllers

import play.api._
import play.api.mvc._
import models._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def attempt = Action {
    Ok(views.html.show())
  }
  
  def listModels = Action { implicit request =>
    val models = Model.findAll
    Ok(views.html.model_list(models))
  }
}