package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  /**
   * Show default start page.
   * @return
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /**
   * By default Play sees addresses such as, for example, /models and /models/
   * as different addresses. This method helps application to redirect to the right
   * page, even if path ends with a slash.
   */
  def removeSlash(path: String) = Action {
    path.charAt(path.length() - 1) match {
      case '/' => Redirect('/' + path.substring(0, path.length() - 1));
      case _   => NotFound("Path not found.")
    }
  }
  
  def showEditPage = Action { implicit request =>
    Ok(views.html.edit())
  }
  
  def options(url: String) = Action {
    Ok("").withHeaders(
      "Access-Control-Allow-Origin" -> "*",
      "Access-Control-Allow-Methods" -> "GET, POST, PUT, DELETE, OPTIONS",
      "Access-Control-Allow-Headers" -> "Content-Type, X-Requested-With, Accept",
      "Access-Control-Max-Age" -> (60 * 60 * 24).toString
    )
  }
}
