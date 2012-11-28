package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }
import anorm.{ Pk, Id, NotAssigned }

import models.Model
import java.util.Date

object ModelFormat {

  import format.PkFormat._
  import format.DateFormat._

  implicit object ModelFormat extends Format[Model] {
    def reads(json: JsValue) = Model(
      (json \ "id").as[Pk[Long]],
      (json \ "name").as[String],
      (json \ "dateCreated").as[Date])

    def writes(model: Model) = JsObject(Seq(
      "id" -> toJson(model.id),
      "name" -> toJson(model.name),
      "dateCreated" -> toJson(model.dateCreated)))
  }

}