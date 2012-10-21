package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class RelationsTest extends Specification {
	
  "Can create relations with modelsidrelationsidnew" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
    routeAndCall(FakeRequest(GET, "/models/new"))
    //routeAndCall(FakeRequest(GET, "/models/1/relations/1/new"))
    val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

    status(result) must equalTo(OK)
    contentType(result) must beSome("text/html")
    charset(result) must beSome("utf-8")
    contentAsString(result) must not contain("Start Point:")
    }
  }
  /*
  "Can create relations with modelsidrelationsidnew" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
    routeAndCall(FakeRequest(GET, "/models/new"))
    routeAndCall(FakeRequest(GET, "/models/1/relations/1/new"))
    val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

    status(result) must equalTo(OK)
    contentType(result) must beSome("text/html")
    charset(result) must beSome("utf-8")
    contentAsString(result) must contain("Start Point:")
    }
  }
  */
}