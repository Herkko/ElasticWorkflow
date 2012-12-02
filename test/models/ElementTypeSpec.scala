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

package test.models

import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable._

import anorm.{ NotAssigned, Id }
import java.util.Date

class ElementTypeSpec extends Specification {

  import models.{ ElementType }

  "Evolutions" should {
    "be applied without errors" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        ElementType.list().size must be equalTo 5
        println(ElementType.list())
      }
      success
    }
  }

  "Element Types " should {
    "exist" in {
      "Swimlane element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Swimlane") must beTrue
        }
      }

      "Start element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Start") must beTrue
        }
      }

      "End element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("End") must beTrue
        }
      }

      "Activity element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Activity") must beTrue
        }
      }

      "Gateway element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Gateway") must beTrue
        }
      }
    }
  }
}