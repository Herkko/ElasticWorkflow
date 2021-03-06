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

package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }
import anorm.{ Pk, Id, NotAssigned }

import models.Relation

object RelationFormat {

  implicit object RelationFormat extends Format[Relation] {

    import PkFormat._

    def reads(json: JsValue): Relation = Relation(
      (json \ "id").as[Option[Pk[Long]]].getOrElse(NotAssigned),
      (json \ "startId").as[Long],
      (json \ "endId").as[Long],
      (json \ "relationTypeId").as[Long],
      (json \ "value").as[String])

    def writes(relation: Relation): JsObject = JsObject(Seq(
      "id" -> toJson(relation.id),
      "startId" -> toJson(relation.startId),
      "endId" -> toJson(relation.endId),
      "relationTypeId" -> toJson(relation.relationTypeId),
      "value" -> toJson(relation.value)))
  }
}