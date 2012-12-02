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

package test.controllers

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import models.Model
import anorm.Id
import java.util.Date

class ApplicationSpec extends Specification {

  import controllers.Application 
  
  "The Application controller" should {
    "respond to the index Action" in {
      val result = Application.index(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("text/html")
      charset(result) must beSome("utf-8")   
    }
  }
}