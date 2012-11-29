package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format }
import anorm.{ Pk, Id, NotAssigned }

object PkFormat {

  implicit object PkFormat extends Format[Pk[Long]] {

    def writes(pk: Pk[Long]): JsValue = toJson( pk.map(id => id).getOrElse(0L) )
    
    def reads(pk: JsValue): Pk[Long] = {
      pk.as[Int] match {
        case 0 => NotAssigned
        case key => Id[Long](key)
      }
    }
  }

}