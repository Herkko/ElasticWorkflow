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

  //Try to remove slash from the end of URL to see if path will work
  def removeSlash(path: String) = Action {
    val ending = path.charAt(path.length() - 1)
    if (ending == '/')
      Redirect('/' + path.substring(0, path.length() - 1));
    else
      NotFound
  }

  def showScala = Action { implicit request =>
    Ok(views.html.show())
  }
}
