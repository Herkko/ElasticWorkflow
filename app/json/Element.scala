package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

trait Element {
   //def findAll(): List[Element]
  // def findByModel(id: Int): List[Element]

 /* implicit object ElementFormat extends Format[Element] {
    def reads(json: JsValue) = fromjson(json)
    def writes(element: Element) = tojson(element)
  }

  def tojson[T](element: T)(implicit format: Writes[T]): JsValue = {
    format.writes(element)
  }

  def fromjson[T](json: JsValue)(implicit format: Reads[T]): T = {
    format.reads(json)
  }*/
}
/*
object Element {
}*/

