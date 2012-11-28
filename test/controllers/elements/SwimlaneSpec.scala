package test.controllers.elements

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class SwimlaneSpec extends Specification {

  "The Swimlane Controller" should {
    "respond in format application/json at path json/swimlane" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/swimlane"))
        
        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
  }
}