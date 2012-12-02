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

package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }
import anorm.{ Pk, Id, NotAssigned }

import models.ProcessElement

object ProcessElementFormat {

  import format.PkFormat._

  implicit object ProcessElementFormat extends Format[ProcessElement] {
    def reads(json: JsValue): ProcessElement = ProcessElement(
      (json \ "id").as[Option[Pk[Long]]].getOrElse(NotAssigned),
      (json \ "modelProcessId").as[Option[Long]].getOrElse(1L),
      (json \ "elementTypeId").as[Int],
      (json \ "value").as[String],
      (json \ "width").as[Int],
      (json \ "height").as[Int],
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(element: ProcessElement): JsObject = JsObject(Seq(
      "id" -> toJson(element.id),
      "modelProcessId" -> toJson(element.modelProcessId),
      "elementTypeId" -> toJson(element.elementTypeId),
      "value" -> toJson(element.value),
      "width" -> toJson(element.width),
      "height" -> toJson(element.height),
      "cx" -> toJson(element.x),
      "cy" -> toJson(element.y)))
  }

}