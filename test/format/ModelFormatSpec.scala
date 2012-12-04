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
