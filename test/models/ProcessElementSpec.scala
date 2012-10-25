package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm._
import java.util.Date

class ProcessElementSpec extends Specification {

  "Evolutions" should {
    "be applied without errors" in {
      evolutionFor("default")
      running(FakeApplication()) {
        DB.withConnection {
          implicit connection =>
            SQL("select count(1) from processElements").execute()
        }
      }
      success
    }
  }

  "The ProcessElement class" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        var rows: anorm.SqlRow = null
        DB.withConnection { implicit connection =>
          SQL("""insert into models values (1, 'ModelName1', '2000-10-11')""").execute()
          SQL("""insert into processes values (2, 'ProcessName1', '2000-10-11')""").execute()
          SQL("""insert into modelProcesses values (1, 1, 2)""").execute()

          SQL("""insert into processElements values (1, 1, 3, 'ModelName1', 10, 200, 200)""").execute()
          SQL("""insert into processElements values (1, 2, 4, 'ModelName2', 10, 100, 100)""").execute()
        }
        DB.withConnection { implicit connection =>
          rows = SQL("""select count(*) as c from processElements""").apply().head
        }
        rows[Long]("c") must be equalTo 2
      }
    }
  }

  "ProcessElement model" should {

    "be created and retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.create(Id(1), "Name1", new Date())
        Process.create(Id(2), "ProcessName", new Date())
        ModelProcess.create(Id(3), 1, 2)
        ProcessElement.create(3, 1, Id(4), "I am Process Element", 10, 99, 99)

        val result = ProcessElement.read(4)
        result.relationId.toString().toInt must equalTo(4)
        result.value must equalTo("I am Process Element")
      }
    }
  }

  "Start Element" >> {
    "exists" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withConnection {
          implicit connection =>
            val result = SQL("select * from elementTypes where name = 'Start'").execute()
            result must beTrue
        }
      }
    }

    "can be created and retrieve by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.create(Id(1), "Name1", new Date())
        Process.create(Id(2), "ProcessName", new Date())
        ModelProcess.create(Id(3), 1, 2)
        ProcessElement.create(3, 2, Id(4), "I am Start Element", 10, 99, 99)

        val result = ProcessElement.read(4)
        result.relationId.toString().toInt must equalTo(4)
        result.value must equalTo("I am Start Element")
      }
    }
  }
}