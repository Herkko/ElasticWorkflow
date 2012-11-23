package controllers

//import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

import models.{ ProcessElement }
import app.actions.CORSAction

import format.ProcessElementFormat

object JsonController extends Controller {

  import ProcessElementFormat._
  
  def list() = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.list))
  }

}