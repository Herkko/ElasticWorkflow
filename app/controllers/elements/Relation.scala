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
    request.body.asJson.map { json =>  
        val id = Json.fromJson(json) match {
          case e: Relation => e.create
          case _ => throw new Exception("Reading Relation from Json failed.")
        }
        Ok(toJson(Relation.read(id)))
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }
  
  def delete(id: Long) = CORSAction { implicit request =>
    Relation.delete(id)  
    Ok(toJson(""))
  }
  
}