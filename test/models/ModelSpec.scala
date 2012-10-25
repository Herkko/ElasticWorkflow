package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm._
import java.util.Date

class ModelSpec extends Specification {

  "Evolutions" should {
    "be applied without errors" in {
      evolutionFor("default")
      running(FakeApplication()) {
        DB.withConnection {
          implicit connection =>
            SQL("select count(1) from models").execute()
        }
      }
      success
    }
  }

  "The Model class" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        var rows: anorm.SqlRow = null
        DB.withConnection { implicit connection =>
          SQL("""insert into models values (1, 'ModelName1', '2000-10-11')""").execute()
          SQL("""insert into models values (2, 'ModelName2', '2011-10-11')""").execute()
        }
        DB.withConnection { implicit connection =>
          rows = SQL("""select count(*) as c from models""").apply().head
        }
        rows[Long]("c") must be equalTo 2
      }
    }
  }

  "Model model" should {

    "be created and retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val modelId = Model.create(NotAssigned, "Name1", new Date())
        val result = Model.read(modelId)

        result.id.toString().toInt must equalTo(modelId)
        result.name must equalTo("Name1")
      }
    }

    "find all models when some models exist" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.create(NotAssigned, "Name1", new Date())
        Model.create(NotAssigned, "Name2", new Date())
        Model.create(NotAssigned, "Name3", new Date())

        Model.findAll.size must equalTo(3)
      }
    }
    
    "find all models when no models exist" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.findAll.size must equalTo(0)
      }
    }

    "be able to have same name with other model" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.create(NotAssigned, "Name1", new Date())
        Model.create(NotAssigned, "Name2", new Date())
        Model.create(NotAssigned, "Name2", new Date())

        Model.findAll.size must equalTo(3)
      }
    }

    "be able to check if model with such id exists" in {

      "if model exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val id = Model.create(NotAssigned, "Name1", new Date())

          Model.contains(id) must beTrue
        }
      }

      "if model doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val id = Model.create(NotAssigned, "Name1", new Date())

          Model.contains(id + 1) must beFalse
        }
      }
    }

  }

}