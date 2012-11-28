package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }
import anorm.{ Pk, Id, NotAssigned }

import models.Process
import java.util.Date

object ProcessFormat {

  import format.PkFormat._
  import format.DateFormat._

  implicit object ProcessFormat extends Format[Process] {
    def reads(json: JsValue) = Process(
      (json \ "id").as[Pk[Long]],
      (json \ "name").as[String],
      (json \ "dateCreated").as[Date])

    def writes(Process: Process) = JsObject(Seq(
      "id" -> toJson(Process.id),
      "name" -> toJson(Process.name),
      "dateCreated" -> toJson(Process.dateCreated)))
  }

}