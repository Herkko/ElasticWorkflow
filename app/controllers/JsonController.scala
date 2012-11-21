package controllers

import json._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

import collection.mutable.HashMap
import service.{ ProcessElementService }
import models.{ ProcessElement, Relation }
import app.actions.CORSAction

import format.RelationFormat
import format.ProcessElementFormat

object JsonController extends Controller {

  import RelationFormat._
  import ProcessElementFormat._

  val processElementService = new ProcessElementService

  def list() = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.list))
  }

  def getProcessElement(elem: String) = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.findType(elem)))
  }

  def getProcessElementByModel(id: Long, elem: String) = CORSAction { implicit request =>
    Ok(toJson(ProcessElement.findTypeByModel(id, elem)))
  }

  def createProcessElement(id: Long) = CORSAction { request =>
    processElementService.create(1, 1, id, "New Element", 100, 100);
    Ok(views.html.edit())
  }

  def getRelation = CORSAction { implicit request =>
    Ok(toJson(Relation.list))
  }

  def getRelationByModel(id: Int) = CORSAction { implicit request =>
    Ok(toJson(Relation.findByModel(id)))
  }

  def createRelation() = CORSAction { request =>
    println("lol. not going to happen");
    Ok(views.html.edit())
  }
  
  def toElement(id: Int) = CORSAction { implicit request =>
    request.body.asJson.map { json =>
      {
        val Some(relationId) = (json \ "id").asOpt[Int]
        val Some(value) = (json \ "value").asOpt[String]
        val Some(size) = (json \ "size").asOpt[Int]
        val Some(x) = (json \ "cx").asOpt[String]
        val Some(y) = (json \ "cy").asOpt[String]
        println(relationId + " " + value + " " + size + " " + x + " " + y)
        processElementService.update(relationId, value, size, x.toInt, y.toInt)
        Ok(views.html.edit())
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  def createElement(id: Int) = CORSAction { request =>
    /*  request.body.asJson.map { json => {
    	val Some(modelProcessId) = (json \ "modelProcessId").asOpt[Int]
        val Some(elementTypeId) = (json \ "elementTypeId").asOpt[Int]
        val Some(relationId) = (json \ "id").asOpt[Int]
        val Some(value) = (json \ "value").asOpt[String]
        val Some(size) = (json \ "size").asOpt[Int]
        val Some(x) = (json \ "cx").asOpt[Int]
        val Some(y) = (json \ "cy").asOpt[Int]
       // println(relationId + " " + value + " " + size + " " + x + " " + y)
        processElementService.createActivity(1, 1, 100, 100);//create(modelProcessId, elementTypeId, relationId, value, x, y)
        Redirect(routes.Models.list)
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    } */
    processElementService.create(1, 1, 4, "New Activity", 100, 100);
    Ok(views.html.edit())
  }
}