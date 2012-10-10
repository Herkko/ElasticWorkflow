package controllers

import play.api._
import play.api.mvc._
import models._
import java.util.Date
import anorm._

object Relations extends Controller {

  //Add new relation to the element
  def add(modelId: Int, elementId: Int) = Action { implicit request =>
    if (Model.contains(modelId)) {
      // if (Process.contains(processId))
      Relation.insert(Relation(NotAssigned, 1, 100, 200, "Test relation", elementId))
      
      Redirect(routes.Models.show(modelId))
    } else {
      Redirect(routes.Models.list)
    }

  }
}