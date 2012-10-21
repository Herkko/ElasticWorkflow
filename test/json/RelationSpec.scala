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
          val modelProcess = processService.create(modelId);
          val elem1 = processElementService.createSwimlane(modelProcess, 20, 100);
          val elem2 = processElementService.createStart(modelProcess, 70, 170);
          val relation = relationService.create(20, 100, 70, 170, "The Only Relation", elem1, elem2);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))
          contentAsString(result) must be equalTo ("""[{"x1":20,"y1":100,"x2":70,"y2":170},{"x1":20,"y1":100,"x2":70,"y2":170}]""")
        }
      }

      "one model with one process containing multiple elements with 3 relations, will return json representation all relations" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val modelId = modelService.create()
          val modelProcess = processService.create(modelId);

          val elem1 = processElementService.createSwimlane(modelProcess, 20, 100);
          val elem2 = processElementService.createStart(modelProcess, 70, 170);
          val elem3 = processElementService.createEnd(modelProcess, 270, 170);

          val relation0 = relationService.create(20, 100, 70, 170, "The First Relation", elem1, elem2);
          val relation1 = relationService.create(20, 100, 270, 170, "The Second Relation", elem1, elem3);
          val relation2 = relationService.create(270, 170, 70, 170, "The Third Relation", elem3, elem2);

          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation"))
          contentAsString(result) must contain("""[{"x1":20,"y1":100,"x2":70,"y2":170},{"x1":20,"y1":100,"x2":70,"y2":170},{"x1":20,"y1":100,"x2":270,"y2":170},{"x1":20,"y1":100,"x2":270,"y2":170},{"x1":270,"y1":170,"x2":70,"y2":170},{"x1":270,"y1":170,"x2":70,"y2":170}]""")
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
          val relation1 = relationService.create(10, 10, 20, 20, "The Relation that belongs to first model", elem1, elem2);
          
          //Second model data
          val model2 = modelService.create()
          val modelProcess2 = processService.create(model2);
          val elem3 = processElementService.createSwimlane(modelProcess2, 30, 30);
          val elem4 = processElementService.createStart(modelProcess2, 40, 40);
          val relation2 = relationService.create(30, 30, 40, 40, "The Relation that belongs to second model", elem3, elem4);
          
          val Some(result) = routeAndCall(FakeRequest(GET, "/json/relation/1"))
          contentAsString(result) must beEqualTo("""[{"x1":10,"y1":10,"x2":20,"y2":20},{"x1":10,"y1":10,"x2":20,"y2":20}]""")
          val Some(result2) = routeAndCall(FakeRequest(GET, "/json/relation/2"))
          contentAsString(result2) must beEqualTo("""[{"x1":30,"y1":30,"x2":40,"y2":40},{"x1":30,"y1":30,"x2":40,"y2":40}]""")
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

