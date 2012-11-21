package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }
import anorm.{ Pk, Id, NotAssigned }

import models.Relation

object RelationFormat {

  implicit object RelationFormat extends Format[Relation] {

    import PkFormat._

    def reads(json: JsValue) = Relation(
      (json \ "id").as[Pk[Long]],
      (json \ "startId").as[Int],
      (json \ "endId").as[Int],
      (json \ "relationTypeId").as[Int],
      (json \ "value").as[String])

    def writes(relation: Relation) = JsObject(Seq(
      "id" -> toJson(relation.id),
      "startId" -> toJson(relation.startId),
      "endId" -> toJson(relation.endId),
      "relationTypeId" -> toJson(relation.relationTypeId),
      "value" -> toJson(relation.value)))
  }
}