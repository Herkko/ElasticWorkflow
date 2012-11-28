package test.controllers.elements

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class GatewaySpec extends Specification {

  "The Gateway Controller" should {
    "respond in format application/json at path json/gateway" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/gateway"))
        
        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }
  }
}