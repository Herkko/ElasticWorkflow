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
    val ending = path.charAt(path.length() - 1)
    if (ending == '/')
      Redirect('/' + path.substring(0, path.length() - 1));
    else
      NotFound
  }

  /**
   * Show page that uses Raphael.
   */
  def showScala = Action { implicit request =>
    Ok(views.html.jsTest())
  }
  
   def showEdit = Action { implicit request =>
    Ok(views.html.edit())
  }
  /**
   * just trying to do stuff with backbone
   */
  def showBackboneAttempt = Action { implicit request =>
    Ok(views.html.jvsTest())
  }
  
  def showEditPage = Action { implicit request =>
    Ok(views.html.edit())
  }
}
