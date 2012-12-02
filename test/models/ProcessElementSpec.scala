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

package test.models

import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable._
import org.specs2.mutable.After

import anorm.{ NotAssigned, Id }
import java.util.Date

class ProcessElementSpec extends Specification {

  import models.{ ProcessElement, Process, Model, ModelProcess }
  
  "Evolutions" should {
    "be applied without errors" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Process.list().size must be equalTo 0
      }
      success
    }
  }

  "The ProcessElement class" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(Id(1), "ModelName1", new Date()).create
        Process(Id(2), "ProcessName1", new Date()).create
        ModelProcess(Id(1), 1L, 1L).create
        
        ProcessElement(Id(1), 1L, 3, "ProcessElement1", 10, 200, 200).create()
        ProcessElement(Id(1), 1L, 4, "ProcessElement2", 10, 100, 100).create()
       
        ProcessElement.list().size must be equalTo 2
        ProcessElement.read(1) must not be none
        ProcessElement.read(2) must not be none
        ProcessElement.read(1).get.value must be equalTo "ProcessElement1"
        
      }
    }
  }

  "ProcessElement" should {

    "be created and retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "ModelName1", new Date()).create
        Process(NotAssigned, "ProcessName1", new Date()).create
        ModelProcess(NotAssigned, 1, 1).create
        
        ProcessElement(NotAssigned, 1, 4, "I am a Process Element", 10, 99, 99).create

        val result = ProcessElement.read(1)
        result must not be none
        result.get.value  must equalTo("I am a Process Element")
      }
    }
  }
/*
  "Start Element" >> {
    "exists" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        /*DB.withConnection {
          implicit connection =>
            val result = SQL("select * from elementTypes where name = 'Start'").execute()
            result must beTrue
        }*/
      }
    }

    "can be created and retrieve by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.create(Id(1), "Name1", new Date())
        Process.create(Id(2), "ProcessName", new Date())
        ModelProcess.create(Id(3), 1, 2)
        ProcessElement.create(3, 2, Id(4), "I am Start Element", 10, 99, 99)

        val Some(result) = ProcessElement.read(4)
        result.relationId.toString().toInt must equalTo(4)
        result.value must equalTo("I am Start Element")
      }
    }
  }*/
}