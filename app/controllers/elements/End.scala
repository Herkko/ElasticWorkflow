package controllers.elements

import play.api._
import play.api.mvc._
import app.actions.CORSAction

import play.api.libs.json.Json.toJson
import play.api.libs.json._

import models.ProcessElement

object End extends Controller with Basic {

  import format.ProcessElementFormat._
  
  override val typeName: String = "End"
  override val elementType: Int = 3

  override def list = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.findType(typeName)))
  }
}
