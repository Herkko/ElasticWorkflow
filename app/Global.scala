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

import play.api._
import play.api.mvc._
import play.api.mvc.Results._

import anorm.NotAssigned
import java.util.Date
import models.{ Model }
import controllers.Processes

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started")
    Logger.info("Creating new model and process...")
   // val modelId = Model.create(new Model(NotAssigned, "Model", new Date()))
   // Processes.createNewProcess(modelId)
  }  
  
  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  } 
  
  override def onBadRequest(request: RequestHeader, error: String) = {
    BadRequest("Bad Request: " + error)
  }
    
}