package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ProcessElementsSpec extends Specification {
  
    "User should be able to update process elements" >> {
      "when process element with given id exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(POST, "/models"))
          routeAndCall(FakeRequest(PUT, "/elements/id=2&value=I+was+changed&size=10&x=100&y=100"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

          contentAsString(result) must contain("I was changed: 2")
        }
      }

      "fail when process element with given id doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(PUT, "/elements/id=2&value=I+was+changed&size=10&x=100&y=100"))
          status(result) must equalTo(404)
        }
      }
    }
}