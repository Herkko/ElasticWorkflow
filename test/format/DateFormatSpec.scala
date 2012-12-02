package test.format

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import anorm.{ NotAssigned, Id }
import java.util.Date
import java.text.SimpleDateFormat

import play.api.libs.json.Json.toJson
import play.api.libs.json._

class DateFormatSpec extends Specification {
  
   import format.DateFormat._
   
   "Date can be converted to Json" in {
     val date = new Date()
     try {
       Json.toJson(date)   
     } catch {
       case exception => failure
     }
     success
   }
   
   "Date can be correctly converted from Json" in {
     val date: Date = new Date()
     try {
       val newDate: Date = Json.fromJson(Json.toJson(date))
       date must be equalTo newDate
     } catch {
       case exception => failure
     }
     success
   }
}