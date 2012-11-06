package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.Play.current
import play.api.db.DB
import anorm.SQL

class ModelsSpec extends Specification {

  "The Models controller" >> {
    "User should be able to list models" >> {

      "request /models and receive response in html format and utf8 encoding" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          status(result) must equalTo(OK)
          contentType(result) must beSome("text/html")
          charset(result) must beSome("utf-8")
        }
      }

      "request /models when no models exist and see page with only a header" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(GET, "/models"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          contentAsString(result) must contain("All models:")
          contentAsString(result) must not contain ("Model:")
        }
      }

      "request /models when 3 models exist and see page with a header and a list of 3 models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          DB.withConnection { implicit connection =>
            SQL("""insert into models values (1, 'ModelName1', '2000-10-11')""").execute()
            SQL("""insert into models values (2, 'ModelName2', '2011-10-11')""").execute()
            SQL("""insert into models values (3, 'ModelName3', '2010-10-11')""").execute()
          }

          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          contentAsString(result) must contain("All models:")
          contentAsString(result) must contain("Model: 1")
          contentAsString(result) must contain("Model: 2")
          contentAsString(result) must contain("Model: 3")
        }
      }

      "create a new model with browser and then see it listed at /models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(before) = routeAndCall(FakeRequest(POST, "/models"))
          contentAsString(before) must not contain ("Model: 1")

          routeAndCall(FakeRequest(GET, "/models/new"))
          val Some(after) = routeAndCall(FakeRequest(GET, "/models"))
          contentAsString(after) must contain("Model: 1")
        }
      }
    }

    "User should be able to create models" >> {

      "when no models exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(POST, "/models"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          contentAsString(result) must contain("Model: 1")
        }
      }

      "when one other model already exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(POST, "/models"))
          routeAndCall(FakeRequest(POST, "/models"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          contentAsString(result) must contain("Model: 1")
          contentAsString(result) must contain("Model: 2")
        }
      }

      /*   "have automatically created process in each new model" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(POST, "/models"))
          status(result) should be equalTo (SEE_OTHER)
          redirectLocation(result) must beSome.which(_ == "/models/1/processes/new")
        }
      }*/

      "be able to see model page after creating model" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result0) = routeAndCall(FakeRequest(POST, "/models"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(result) must contain("Name: Model")
        }
      }
    }

    "User should be able to update model" >> {
      "when model with given id exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(POST, "/models"))
          routeAndCall(FakeRequest(PUT, "/models/id=1&name=I+has+new+name!"))
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

          contentAsString(result) must contain("Name: I has new name!")
        }
      }

      "fail when model with given id doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(PUT, "/models/id=1&name=I+has+new+name!"))
          status(result) must equalTo(404)
        }
      }
    }
  }
}