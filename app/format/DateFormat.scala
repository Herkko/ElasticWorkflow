package format

import play.api.libs.json.Json.toJson
import play.api.libs.json.{ JsValue, Format, JsObject }

import java.util.Date
import java.text.SimpleDateFormat

object DateFormat {
  
  implicit object JsonDateFormatter extends Format[Date] {

    val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")

    def writes(date: Date): JsValue = {
      toJson(dateFormat.format(date))
    }

    def reads(date: JsValue): Date = {
        dateFormat.parse(date.as[String])
    }
  }
}