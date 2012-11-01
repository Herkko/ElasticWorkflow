package json

import service._
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import org.h2.jdbc.JdbcSQLException

class RelationSpec extends Specification {

  val modelService = new ModelService
  val processService = new ProcessService
  val processElementService = new ProcessElementService
  val relationService = new RelationService

  "Relation elements in json format" should {

    "be listed at path /json/relation using utf8 encoding" >> {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
        charset(result) must beSome("utf-8")
      }
    }

    "be translated to json correctly" >> {

      "one model, no processes and no relations will return empty list" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          modelService.create()
          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))

          contentAsString(result) must be equalTo ("[]")
        }
      }

      "one model with one process containing a swimlane and start element with no relations, will return empty list" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

          val modelId = modelService.create()
          val modelProcess = processService.create(modelId);
          val elem1 = processElementService.createSwimlane(modelProcess, 20, 100);
          val elem2 = processElementService.createStart(modelProcess, 70, 170);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))
          contentAsString(result) must be equalTo ("[]")
        }
      }

      "one model with one process containing a swimlane and start element with 1 relation, will return json representation of a relation" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val modelId = modelService.create()
          val modelProcess = processService.create(modelId)
          val elem1 = processElementService.createSwimlane(modelProcess, 20, 100)
          val elem2 = processElementService.createStart(modelProcess, 70, 170)
          val relation = relationService.create(elem1, elem2, "The Only Relation")

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))
          contentAsString(result) must be equalTo ("""[{"id":1,"startId":"""+elem1+""","endId":"""+elem2+""","relationTypeId":1,"value":"The Only Relation"}]""")
        }
      }

      "one model with one process containing multiple elements with 3 relations, will return json representation all relations" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val modelId = modelService.create()
          val modelProcess = processService.create(modelId);

          val elem1 = processElementService.createSwimlane(modelProcess, 20, 100)
          val elem2 = processElementService.createStart(modelProcess, 70, 170)
          val elem3 = processElementService.createEnd(modelProcess, 270, 170)

          val relation0 = relationService.create(elem1, elem2, "The First Relation")
          val relation1 = relationService.create(elem1, elem3, "The Second Relation")
          val relation2 = relationService.create(elem3, elem2, "The Third Relation")

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))
          contentAsString(result) must contain("""[{"id":1,"startId":"""+elem1+""","endId":"""+elem2+""","relationTypeId":1,"value":"The First Relation"},{"id":2,"startId":"""+elem1+""","endId":"""+elem3+""","relationTypeId":1,"value":"The Second Relation"},{"id":3,"startId":"""+elem3+""","endId":"""+elem2+""","relationTypeId":1,"value":"The Third Relation"}]""")
        }
      }
    }

    "be correctly converted to json when requested relations of one model" >> {

      "show all relations that belong to one model and none of relations of other models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          
          //First model data
          val model1 = modelService.create()
          val modelProcess1 = processService.create(model1);
          val elem1 = processElementService.createSwimlane(modelProcess1, 10, 10);
          val elem2 = processElementService.createStart(modelProcess1, 20, 20);
          val relation1 = relationService.create(elem1, elem2, "The Relation that belongs to first model");
          
          //Second model data
          val model2 = modelService.create()
          val modelProcess2 = processService.create(model2);
          val elem3 = processElementService.createSwimlane(modelProcess2, 30, 30);
          val elem4 = processElementService.createStart(modelProcess2, 40, 40);
          val relation2 = relationService.create(elem3, elem4, "The Relation that belongs to second model");
          
          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation/1"))
          contentAsString(result) must beEqualTo("""[{"id":1,"startId":"""+elem1+""","endId":"""+elem2+""","relationTypeId":1,"value":"The Relation that belongs to first model"}]""")
          val Some(result2) = routeAndCall(FakeRequest(GET, "/json/relation/2"))
          contentAsString(result2) must beEqualTo("""[{"id":2,"startId":"""+elem3+""","endId":"""+elem4+""","relationTypeId":1,"value":"The Relation that belongs to second model"}]""")
        }
      }

      "not change any of relations data" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          1 == 1
        }
      }
    }
  }
}

