package test.controllers.elements

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import anorm.{ NotAssigned, Id }
import java.util.Date

class ActivitySpec extends Specification {

  import models.{ Model, Process, ModelProcess, ProcessElement }

  "The Activity Controller" should {
    "respond in format application/json at path json/activity" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }

    "produce following output in next cases:" >> {

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

          ProcessElement(NotAssigned, 1L, 4, "Activity", 0, 0, 0).create()

          val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))
          contentAsString(result) must be equalTo ("""[{"id":1,"modelProcessId":1,"elementTypeId":4,"value":"Activity","size":0,"cx":0,"cy":0}]""")
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
          contentAsString(result) must be equalTo ("""[{"id":1,"modelProcessId":1,"elementTypeId":4,"value":"Activity1","size":0,"cx":0,"cy":0},{"id":2,"modelProcessId":1,"elementTypeId":4,"value":"Activity2","size":0,"cx":0,"cy":0},{"id":3,"modelProcessId":1,"elementTypeId":4,"value":"Activity3","size":0,"cx":0,"cy":0}]""")
        }
      }
    }
  }
}