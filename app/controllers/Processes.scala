/*Copyright 2012 University of Helsinki, Panu Klemola, Herkko Virolainen, Daria Antonova
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

import models.{ Model, ModelProcess, Process, Relation, ProcessElement }

import play.api._
import play.api.mvc._
import anorm.{ NotAssigned, Id }
import java.util.Date

import app.actions.CORSAction

/**
 * Control all actions related to showing, creating and deleting processes.
 */
object Processes extends Controller {

  import format.DateFormat._
  /**
   * Add new process to a model. Called when a new model is created, or user wants to add a process to existing model.
   * Redirects to a page that lists all models, if model with id equal to parameter modelId doesn't exist.
   */
  def create(modelId: Long) = Action { implicit request =>
    Model.read(modelId) match {
      case Some(model) => {
        createNewProcess(modelId)
        Redirect(routes.Application.showEditPage)
      }
      case None => NotFound("This Model doesn't exist. Thrown by: " + getClass.getName + " when creating new process.")
    }
  }

  def update() = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val Some(id) = (json \ "id").asOpt[Long]
        val Some(name) = (json \ "name").asOpt[String]
        val Some(date) = (json \ "dateCreated").asOpt[Date]
        
        Process(Id(id), name, date).update
        Redirect(routes.Models.read(Process.getModelId(id).toInt))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }
  

  def delete(id: Long) = Action { implicit request =>
    Process.read(id) match {
      case Some(process) => {
        val modelId = Process.getModelId(id)
        deleteProcess(id)
        Redirect(routes.Models.read(modelId.toInt))
      }
      case None => NotFound("This Process doesn't exist. Thrown by: " + getClass.getName + " when deleting process.")
    }
  }

  /**
   * Delete process along with all the data it contains.
   */
  def deleteProcess(id: Long) = {
    Relation.deleteByProcess(id)
    ProcessElement.deleteByProcess(id.toInt)
    Process.delete(id)
  }

  /**
   * Create new process and set it to belong to a model specified by parameter modelId. Each process by default contains
   * SwimLane and StartElement and no relations.
   */
  def createNewProcess(modelId: Long) = {
    val count = Process.countByModel(modelId)
    val y = (count * 300 + 20 * (count + 1)).toInt

    val processId: Long = Process(NotAssigned, "Process", new Date()).create
    val modelProcessId: Long = ModelProcess(NotAssigned, modelId, processId).create

    val elem1 = ProcessElement(NotAssigned, modelProcessId, 1, "Swimlane", 800, 300, 20, y).create
    val elem2 = ProcessElement(NotAssigned, modelProcessId, 2, "Start", 0, 0, 90, y + 150).create
    val elem3 = ProcessElement(NotAssigned, modelProcessId, 3, "End", 0, 0, 740, y + 150).create
    val elem4 = ProcessElement(NotAssigned, modelProcessId, 4, "Activity 1", 0, 0, 170, y + 125).create
    val elem5 = ProcessElement(NotAssigned, modelProcessId, 4, "Activity 2", 0, 0, 520, y + 50).create
    val elem6 = ProcessElement(NotAssigned, modelProcessId, 4, "Activity 3", 0, 0, 520, y + 200).create
    val elem7 = ProcessElement(NotAssigned, modelProcessId, 5, "Gateway", 0, 0, 400, y + 110).create

    val rel = Relation(NotAssigned, elem2, elem4, 1, "First Relation").create
    val rel1 = Relation(NotAssigned, elem4, elem7, 1, "Second Relation").create
    val rel2 = Relation(NotAssigned, elem7, elem5, 1, "Third Relation").create
    val rel3 = Relation(NotAssigned, elem7, elem6, 1, "Fourth Relation").create
    val rel4 = Relation(NotAssigned, elem5, elem3, 1, "Fifth Relation").create
  }

}