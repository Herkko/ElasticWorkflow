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

class ProcessSpec extends Specification {

  import models.{ Process, Model, ModelProcess }

  "Evolutions" should {
    "be applied without errors" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Process.list().size must be equalTo 0
      }
      success
    }
  }

  "Processes" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Process(NotAssigned, "Process1", new Date()).create
        Process(NotAssigned, "Process2", new Date()).create

        Process.list().size must be equalTo 2
      }
    }
  }
  //cant belong to two models
  "Process model" should {

    "be created and retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val id = Process(NotAssigned, "ProcessName", new Date()).create
        val Some(result) = Process.read(id)

        result.id.toString().toInt must equalTo(id)
        result.name must equalTo("ProcessName")
      }
    }

    "count how many processes belong to a model" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(Id(1), "ModelName1", new Date()).create
        Model(Id(2), "ModelName2", new Date()).create

        Process(Id(1), "ProcessName1", new Date()).create
        Process(Id(2), "ProcessName2", new Date()).create
        Process(Id(3), "ProcessName3", new Date()).create

        ModelProcess(Id(1), 1, 1).create
        ModelProcess(Id(2), 2, 2).create
        ModelProcess(Id(3), 1, 3).create

        Process.countByModel(2) must equalTo(1)
        Process.countByModel(1) must equalTo(2)
      }
    }

  }
}