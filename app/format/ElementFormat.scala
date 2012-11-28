package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }
import anorm.{ Pk, Id, NotAssigned }

import models.ProcessElement

object ProcessElementFormat {

  import format.PkFormat._

  implicit object ProcessElementFormat extends Format[ProcessElement] {
    def reads(json: JsValue): ProcessElement = ProcessElement(
      (json \ "id").as[Pk[Long]],
      (json \ "modelProcessId").as[Long],
      (json \ "elementTypeId").as[Int],
      (json \ "value").as[String],
      (json \ "size").as[Int],
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(element: ProcessElement): JsObject = JsObject(Seq(
      "id" -> toJson(element.id),
      "modelProcessId" -> toJson(element.modelProcessId),
      "elementTypeId" -> toJson(element.elementTypeId),
      "value" -> toJson(element.value),
      "size" -> toJson(element.size),
      "cx" -> toJson(element.x),
      "cy" -> toJson(element.y)))
  }

}