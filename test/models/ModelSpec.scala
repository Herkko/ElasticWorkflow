package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm.Id
import java.util.Date
import org.h2.jdbc.JdbcSQLException

class ModelSpec extends Specification {

  "The Model class" should {

    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.insert(new Model(Id(1), "name", new Date()))
        Model.findAll must have size 1
      }
    }

    "list models correctly" >> {

      "list 0 models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Model.findAll must have size 0
        }
      }

      "list 3 models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Model.insert(new Model(Id(1), "name1", new Date()))
          Model.insert(new Model(Id(2), "name2", new Date()))
          Model.insert(new Model(Id(3), "name3", new Date()))
          Model.findAll must have size 3
        }
      }

      "list models when requested by browser" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(GET, "/models/new"))
          routeAndCall(FakeRequest(GET, "/models/new"))

          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          status(result) must equalTo(OK)
          contentType(result) must beSome("text/html")
          charset(result) must beSome("utf-8")
          contentAsString(result) must contain("Model: 1")
          contentAsString(result) must contain("Model: 2")
          contentAsString(result) must not contain("Model: 3")
        }
      }
    }

    "insert models correctly" >> {

      "fail to insert model if id exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Model.insert(new Model(Id(1), "name1", new Date()))
          Model.insert(new Model(Id(1), "name2", new Date())) must throwA[JdbcSQLException]
        }

        "insert model when requested by browser" in {
          running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
            routeAndCall(FakeRequest(GET, "/models/new"))
            val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))

            status(result) must equalTo(OK)
            contentType(result) must beSome("text/html")
            charset(result) must beSome("utf-8")
            contentAsString(result) must contain("Name: Model")
          }
        }
      }
    }

  }

}