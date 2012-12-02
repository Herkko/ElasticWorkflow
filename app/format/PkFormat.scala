/*Copyright 2012 University of Helsinki, Panu Klemola, Herkko Virolainen, Daria Antonova
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
import play.api.libs.json.{ JsValue, Format }
import anorm.{ Pk, Id, NotAssigned }

object PkFormat {

  implicit object PkFormat extends Format[Pk[Long]] {

    def writes(pk: Pk[Long]): JsValue = toJson( pk.map(id => id).getOrElse(0L) )
    
    def reads(pk: JsValue): Pk[Long] = {
      pk.as[Long] match {
        case 0 => NotAssigned
        case key => Id[Long](key)
      }
    }
  }

}