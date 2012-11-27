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
    val modelId = Model.create(new Model(NotAssigned, "Model", new Date()))
    Processes.createNewProcess(modelId)
  }  
  
  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  } 
  
  override def onBadRequest(request: RequestHeader, error: String) = {
    BadRequest("Bad Request: " + error)
  }
    
}