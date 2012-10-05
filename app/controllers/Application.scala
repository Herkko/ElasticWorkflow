package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def attempt = Action {
    Ok(views.html.show())
  }
  
  def listModels = Action { implicit request =>
    //Creating a few models and adding to database
    var model1 = new Model(1, "Send an order", new Date())
    var model2 = new Model(2, "Write a program", new Date())
    var model3 = new Model(3, "Transfer items", new Date())
    
    Model.insert(model1)
    Model.insert(model2)
    Model.insert(model3)
    
    //Fetching all models from database and showing html page
    val models = Model.findAll
    Ok(views.html.model_list(models))
  }
}