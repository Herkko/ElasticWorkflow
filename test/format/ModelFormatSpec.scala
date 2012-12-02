package test.format

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import anorm.{ NotAssigned, Id }
import models.Model

import play.api.libs.json.Json.toJson
import play.api.libs.json._
import java.util.Date

class ModelFormatSpec extends Specification {
  
   import format.ModelFormat._
   
   "Model can be converted to Json" in {
     try {
       Json.toJson(Model())   
     } catch {
       case exception => failure
     }
     success
   }
   
   "Model can be correctly converted from Json" in {
     val model = new Model(NotAssigned, "Name", new Date())
     try {
       val newModel: Model = Json.fromJson(Json.toJson(model))
       model must be equalTo newModel
     } catch {
       case exception => failure
     }
     success
   }
}
