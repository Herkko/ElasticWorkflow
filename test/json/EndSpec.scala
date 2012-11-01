package json

import service.{ ModelService, ProcessService, ProcessElementService }
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.h2.jdbc.JdbcSQLException

class EndSpec extends Specification {

  val modelService = new ModelService
  val processService = new ProcessService
  val processElementService = new ProcessElementService

  "End elements when converted to json" should {
    "be listed at path /json/end using utf8 encoding" >> {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/json/end"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }

    "produce following output in next cases:" >> {

      "return an empty list when there is one model, no processes and no elements" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          modelService.create()
          val Some(result) = routeAndCall(FakeRequest(GET, "/json/end"))

          contentAsString(result) must be equalTo ("[]")
        }
      }
      
      "return a list of one element when there is one end element" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val model = modelService.create()
          val process = processService.create(model)
          processElementService.createEnd(process, 0, 0);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/end"))
          contentAsString(result) must be equalTo ("""[{"modelProcessId":1,"elementTypeId":3,"relationId":1,"value":"End","size":0,"x":0,"y":0}]""")
        }
      }
      
      "return a list of three elements when there is three end elements in two different processes" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val model = modelService.create()
          val process1 = processService.create(model)
          val process2 = processService.create(model)
          processElementService.createEnd(process1, 1, 1);
          processElementService.createEnd(process2, 2, 2);
          processElementService.createEnd(process2, 3, 3);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/end"))
          contentAsString(result) must be equalTo ("""[{"modelProcessId":1,"elementTypeId":3,"relationId":1,"value":"End","size":0,"x":1,"y":1},{"modelProcessId":2,"elementTypeId":3,"relationId":2,"value":"End","size":0,"x":2,"y":2},{"modelProcessId":2,"elementTypeId":3,"relationId":3,"value":"End","size":0,"x":3,"y":3}]""")
        }
      }
    }
  }
}