package controllers.elements

//import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

import service.{ ProcessElementService }
import models.{ ProcessElement }
import app.actions.CORSAction

import anorm.NotAssigned
import format.ProcessElementFormat

object Activity extends Controller with Basic {

  val typeName: String = "Activity"
  val elementType: Int = 4

}