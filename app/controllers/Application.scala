/**Copyright 2012 University of Helsinki, Daria Antonova, Herkko Virolainen, Panu Klemola
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.*/

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
  
  def showIndex = Action { implicit request =>
    Ok(views.html.index())
  }
  
  def showAboutPage = Action { implicit request =>
    Ok(views.html.about())
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
