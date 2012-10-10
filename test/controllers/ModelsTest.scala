package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ModelsTest extends Specification {

  "Can create and list new models" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
    routeAndCall(FakeRequest(GET, "/models/new"))
    //routeAndCall(FakeRequest(GET, "/models/1/processes/new"))
    val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

    status(result) must equalTo(OK)
    contentType(result) must beSome("text/html")
    charset(result) must beSome("utf-8")
    contentAsString(result) must contain("Model: 1")
    //contentAsString(result) must not contain ("")
    }
  }
  
  "Does not show any models when started" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
    //routeAndCall(FakeRequest(GET, "/models/new"))
    //routeAndCall(FakeRequest(GET, "/models/1/processes/new"))
    val Some(result) = routeAndCall(FakeRequest(GET, "/models"))

    status(result) must equalTo(OK)
    contentType(result) must beSome("text/html")
    charset(result) must beSome("utf-8")
    contentAsString(result) must not contain("Model: 1")
    //contentAsString(result) must not contain ("")
    }
  }
}