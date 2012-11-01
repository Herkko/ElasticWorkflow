package controllers

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import play.api.Play.current
import play.api.db.DB
import anorm.SQL

class ProcessesSpec extends Specification {
  
  "The Processes controller" >> {
    "User should be able to add processes to models " >> {
      "when model exists and has no processes" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          DB.withConnection { implicit connection =>
            SQL("""insert into models values (1, 'Model1', '2000-10-11')""").execute()
          }
          val Some(before) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(before) must contain("Model: 1")
          contentAsString(before) must not contain ("Process: 1")

          routeAndCall(FakeRequest(GET, "/models/1/processes/new"))
          val Some(after) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(after) must contain("Model: 1")
          contentAsString(after) must contain("Process: 1")
        }
      }

      "have one process added to each new model automatically" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/new"))
          status(result) should be equalTo (SEE_OTHER)
          redirectLocation(result) must beSome.which(_ == "/models/1/processes/new")
        }
      }

      "when model has one process automatically generated" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(GET, "/models/new"))
          routeAndCall(FakeRequest(GET, "/models/1/processes/new"))
          val Some(before) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(before) must contain("Model: 1")
          contentAsString(before) must contain("Process: 1")

          routeAndCall(FakeRequest(GET, "/models/1/processes/new"))
          val Some(after) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(after) must contain("Model: 1")
          contentAsString(after) must contain("Process: 1")
          contentAsString(after) must contain("Process: 2")
        }
      }
    }
  }
}
//  status(result) should equal (SEE_OTHER)
  //    redirectLocation(result) should equal (Some(routes.Application.index.url))
