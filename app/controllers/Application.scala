package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm.Pk

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  

  def attempt = Action {
    Ok(views.html.show())
  }
  
  //Just a method to be able to test if anorm works
  def listModels = Action { implicit request =>
    //Creating a few models and adding to database
    var model1 = new Model(anorm.NotAssigned, "Send an order", new Date())
    var model2 = new Model(anorm.NotAssigned, "Write a program", new Date())
    var model3 = new Model(anorm.NotAssigned, "Transfer items", new Date())
    
    Model.insert(model1)
    Model.insert(model2)
    Model.insert(model3)
    
    //Fetching all models from database and showing html page
    val models = Model.findAll
    Ok(views.html.model_list(models))
  }

}
