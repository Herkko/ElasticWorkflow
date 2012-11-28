package test.models

import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable._

import anorm.{ NotAssigned }
import java.util.Date

class ModelSpec extends Specification {

  import models.Model
  
  object ModelTest {
    def create = println(":)")
  }
 
  "Evolutions" should {
    "be applied without errors" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.list().size must be equalTo 0
      }
      success
    }
  }

  "The Model class" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "ModelName1", new Date()).create
        Model(NotAssigned, "ModelName2", new Date()).create
        
        Model.list().size must be equalTo 2
      }
    }
  }

  "Model model" should {

    "be created and retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val id = Model(NotAssigned, "Name1", new Date()).create
        val Some(result) = Model.read(id)

        result.id.toString().toInt must equalTo(id)
        result.name must equalTo("Name1")
      }
    }

    "return list of all models when some models exist" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "Name1", new Date()).create
        Model(NotAssigned, "Name2", new Date()).create
        Model(NotAssigned, "Name3", new Date()).create

        Model.list().size must equalTo(3)
      }
    }
    
    "return empty list when no models exist" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.list().size must equalTo(0)
      }
    }

    "be able to have models with same name" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model(NotAssigned, "Name1", new Date()).create
        Model(NotAssigned, "Name2", new Date()).create
        Model(NotAssigned, "Name2", new Date()).create

        Model.list().size must equalTo(3)
      }
    }

    "correctly check if model with given id exists" in {

      "return true if model exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val id = Model(NotAssigned, "Name1", new Date()).create

          Model.contains(id) must beTrue
        }
      }

      "return false if model doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val id = Model(NotAssigned, "Name1", new Date()).create

          Model.contains(id + 1) must beFalse
        }
      }
    }
  }
}