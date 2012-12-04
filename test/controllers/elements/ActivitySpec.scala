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


package test.controllers.elements

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import anorm.{ NotAssigned, Id }
import java.util.Date

import play.api.libs.json.Json.toJson
import play.api.libs.json._

class ActivitySpec extends Specification {

  import models.{ Model, Process, ModelProcess, ProcessElement }
  import format.ProcessElementFormat._
  
  def createModel(): Unit = {
    Model(NotAssigned, "ModelName1", new Date()).create
    Process(NotAssigned, "ProcessName1", new Date()).create
    ModelProcess(NotAssigned, 1L, 1L).create
  }
   
  "The Activity Controller" should {
    "respond in format application/json at path json/activity" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
  }

  "User should be able to create activities" >> {
    
    "create activites when no activities exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          createModel()
          routeAndCall(FakeRequest(POST, "/activity").withJsonBody(Json.toJson(ProcessElement(NotAssigned, 1L, 4, "New Activity", 0, 99, 99))))
         
          val Some(result) = routeAndCall(FakeRequest(GET, "/activity/1"))
          contentAsString(result) must be equalTo (Json.stringify(Json.toJson(
              ProcessElement(Id(1), 1L, 4, "New Activity", 0, 99, 99
          ))))
       }
    }
    
    "create activites when one other activity exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          createModel()
          ProcessElement(NotAssigned, 1L, 4, "Old Activity", 0, 199, 199).create
          routeAndCall(FakeRequest(POST, "/activity").withJsonBody(Json.toJson(ProcessElement(NotAssigned, 1L, 4, "New Activity", 0, 99, 99))))
         
          val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))
          contentAsString(result) must be equalTo (Json.stringify(Json.toJson(List(
          ProcessElement(Id(1), 1L, 4, "Old Activity", 0, 199, 199),
          ProcessElement(Id(2), 1L, 4, "New Activity", 0, 99, 99)
        ))))
        }
    }
  }
    
  "User should be able to list activities" >> {

    "return an empty list when there is no elements of this type" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "Model", new Date()).create()
        val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))

        contentAsString(result) must be equalTo ("[]")
      }
    }

    "return a list of one element when there is one activity element" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "ModelName1", new Date()).create
        Process(NotAssigned, "ProcessName1", new Date()).create
        ModelProcess(Id(1), 1L, 1L).create

        ProcessElement(NotAssigned, 1L, 4, "Activity", 0, 0, 0, 0).create()

        val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))
        contentAsString(result) must be equalTo (Json.stringify(Json.toJson(List(
            ProcessElement(Id(1), 1L, 4, "Activity", 0, 0, 0, 0)))))
      }
    }

    "return a list of three elements when there is three activity elements in two different processes" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "ModelName1", new Date()).create
        Process(NotAssigned, "ProcessName1", new Date()).create
        ModelProcess(Id(1), 1L, 1L).create

        ProcessElement(NotAssigned, 1L, 4, "Activity1", 0, 0, 0).create()
        ProcessElement(NotAssigned, 1L, 4, "Activity2", 0, 0, 0).create()
        ProcessElement(NotAssigned, 1L, 4, "Activity3", 0, 0, 0).create()

        val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))
        contentAsString(result) must be equalTo (Json.stringify(Json.toJson(List(
          ProcessElement(Id(1), 1L, 4, "Activity1", 0, 0, 0),
          ProcessElement(Id(2), 1L, 4, "Activity2", 0, 0, 0),
          ProcessElement(Id(3), 1L, 4, "Activity3", 0, 0, 0)
        ))))
      }
    }
  }
  
  "User should be able to delete activities" >> {
    "delete the only activity of a model" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        createModel();
        ProcessElement(NotAssigned, 1L, 4, "Activity", 0, 99, 99).create
        val Some(before) = routeAndCall(FakeRequest(GET, "/activity/1"))
        contentAsString(before) must be equalTo (Json.stringify(Json.toJson(ProcessElement(Id(1), 1L, 4, "Activity", 0, 99, 99))))
        
        routeAndCall(FakeRequest(DELETE, "/activity/1"))        
        val Some(result) = routeAndCall(FakeRequest(GET, "/activity/1"))
        contentAsString(result) must be equalTo "null"
      }
    }
  }
}