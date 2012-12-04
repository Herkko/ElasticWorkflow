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