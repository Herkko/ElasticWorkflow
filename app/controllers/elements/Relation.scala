package controllers.elements

//import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

import models.{ Relation }
import app.actions.CORSAction

import format.RelationFormat

object RelationElement extends Controller {

  import RelationFormat._
  
  def list = CORSAction { implicit request =>
    Ok(toJson(Relation.list))
  }

  def getRelationByModel(id: Int) = CORSAction { implicit request =>
    Ok(toJson(Relation.findByModel(id)))
  }

  def create() = CORSAction { request =>
    println("lol. not going to happen");
    Ok(views.html.edit())
  }
  
}