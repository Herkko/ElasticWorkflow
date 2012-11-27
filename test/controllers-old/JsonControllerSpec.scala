package controllers

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._

class JsonControllerSpec extends Specification {

  "The Json Controller" should {
    "respond in format application/json at path json/swimlane" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/swimlane"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
    
    "respond in format application/json at path json/start" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/start"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
    
     "respond in format application/json at path json/activity" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/activity"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
     
     "respond in format application/json at path json/end" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/end"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
     
    "respond in format application/json at path json/relation" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/relation"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }    
    
    "not respond in format application/json at path /json" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/json"))

        contentType(result) must not beSome("application/json")
      }
    }    
    
  }
}