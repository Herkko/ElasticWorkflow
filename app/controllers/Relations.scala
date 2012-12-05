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
import anorm.NotAssigned
import models.{ Model, Relation, ProcessElement }
/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Relations extends Controller {

  /**
   * Add new relation to the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. 
   */
  def create(modelId: Long, start: Long, end: Long, value: String) = Action { implicit request =>
    (Model.read(modelId), ProcessElement.read(start), ProcessElement.read(end)) match {
      case (Some(model), Some(element1), Some(element2)) => {
        Relation.create(new Relation(NotAssigned, start, end, 1, value))
        Redirect(routes.Models.read(modelId.toInt))
      }
      case _ => NotFound("This Model or Element doesn't exist. Thrown by: " + getClass.getName + " when creating new relation.")
    }
  }

  def update(id: Int, start: Int, end: Int, value: String) = Action { implicit request =>
    (Relation.read(id), ProcessElement.read(start), ProcessElement.read(end)) match {
      case (Some(model), Some(element1), Some(element2)) => {
        Relation.update(id, start, end, value)
        Redirect(routes.Models.read(Relation.getModelId(id).toInt))
      }
      case _ => NotFound("This Relation doesn't exist. Thrown by: " + getClass.getName + " when updating relation.")
    }
  }

  /**
   * Delete new relation of the element specified by elementId. Parameter modelId is needed to show the page of the right model
   * afterwards so that user can see changes. 
   */
  def delete2(id: Long) = Action { implicit request =>
    Relation.read(id) match {
      case Some(relation) => {
        val modelId = Relation.getModelId(id)
        Relation.delete(id)
        Redirect(routes.Models.read(modelId.toInt))
      }
      case None => NotFound("This Relation doesn't exist. Thrown by: " + getClass.getName + " when deleting relation.")
    }
  }
}