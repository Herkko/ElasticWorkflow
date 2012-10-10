package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ProcessesTest extends Specification {
	
  //t�ss� tyyliss� ilmeisesti kusee jokin pahasti, koska
  //t�ll� tavalla tulostetussa nettisivussa ei ole "Process: 1"
  //teksti�, vaikka play run -> /models/new -> /models/1
  //kautta ruudulle tulostuu my�s prosessi...
  "Can create and list new models and processes" in {
    running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
    routeAndCall(FakeRequest(GET, "/models/new"))
    //routeAndCall(FakeRequest(GET, "/models/1/processes/new"))
    val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

    status(result) must equalTo(OK)
    contentType(result) must beSome("text/html")
    charset(result) must beSome("utf-8")
    contentAsString(result) must contain("Process: 1")
    }
  }
}