/*package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class RelationsSpec extends Specification {
  
/*
  "User when interacting with relations in browser" should {
    "is able to create new relation " in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result0) = routeAndCall(FakeRequest(GET, "/models/new"))
        status(result0) should be equalTo (SEE_OTHER)
        //redirectLocation(result) should be equalTo (Some(controllers.routes.Models.read(1).url))

        //routeAndCall(FakeRequest(GET, "/models/1/relations/1/new"))

        val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("text/html")
        charset(result) must beSome("utf-8")
        contentAsString(result) must not contain ("Start Point:")
        contentAsString(result) must not contain ("Start Point:")
      }
    }
  }*/
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
  
    "User should be able to update relations" >> {
      "when relation with given id exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(POST, "/models"))
          routeAndCall(FakeRequest(PUT, "/relations/id=1&start=2&end=3&value=Changed+name"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

          contentAsString(result) must contain("Description: Changed name")
        }
      }

      "fail when relation with given id doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(PUT, "/relations/id=1&start=2&end=3&value=Changed+name"))
          status(result) must equalTo(404)
        }
      }
    }
}*/