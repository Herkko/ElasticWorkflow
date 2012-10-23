package json

import service.{ ModelService, ProcessService, ProcessElementService }
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.h2.jdbc.JdbcSQLException

class StartSpec extends Specification {

  val modelService = new ModelService
  val processService = new ProcessService
  val processElementService = new ProcessElementService

  "Start elements when converted to json" should {
    "be listed at path /json/start using utf8 encoding" >> {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/json/start"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }

    "produce following output in next cases:" >> {

      "return an empty list when there is one model, no processes and no elements" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          modelService.create()
          val Some(result) = routeAndCall(FakeRequest(GET, "/json/start"))

          contentAsString(result) must be equalTo ("[]")
        }
      }
      
      "return a list of one element when there is one start element" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val model = modelService.create()
          val process = processService.create(model)
          processElementService.createStart(process, 0, 0);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/start"))
          contentAsString(result) must be equalTo ("""[{"cx":0,"cy":0}]""")
        }
      }
      
      "return a list of three element when there is three start elements in two different processes" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val model = modelService.create()
          val process1 = processService.create(model)
          val process2 = processService.create(model)
          processElementService.createStart(process1, 1, 1);
          processElementService.createStart(process2, 2, 2);
          processElementService.createStart(process2, 3, 3);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/start"))
          contentAsString(result) must be equalTo ("""[{"cx":1,"cy":1},{"cx":2,"cy":2},{"cx":3,"cy":3}]""")
        }
      }
    }
  }
}