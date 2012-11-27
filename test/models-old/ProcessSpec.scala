package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm._
import java.util.Date

class ProcessSpec extends Specification {

  "Evolutions" should {
    "be applied without errors" in {
      evolutionFor("default")
      running(FakeApplication()) {
        DB.withConnection {
          implicit connection =>
            SQL("select count(1) from processes").execute()
        }
      }
      success
    }
  }

  "Processes" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        var rows: anorm.SqlRow = null
        DB.withConnection { implicit connection =>
          SQL("""insert into processes values (1, 'Process1', '2000-10-11')""").execute()
          SQL("""insert into processes values (2, 'Process2', '2011-10-11')""").execute()
        }
        DB.withConnection { implicit connection =>
          rows = SQL("""select count(*) as c from processes""").apply().head
        }
        rows[Long]("c") must be equalTo 2
      }
    }
  }
  //cant belong to two models
  "Process model" should {

    "be created and retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val id = Process.create(NotAssigned, "ProcessName", new Date())
        val Some(result) = Process.read(id)
        
        result.id.toString().toInt must equalTo(id)
        result.name must equalTo("ProcessName")
      }
    }

    "be counted by model" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.create(Id(1), "Name1", new Date())
        Model.create(Id(2), "Name2", new Date())

        Process.create(Id(1), "ProcessName1", new Date())
        Process.create(Id(2), "ProcessName2", new Date())
        Process.create(Id(3), "ProcessName3", new Date())

        ModelProcess.create(Id(1), 1, 1)
        ModelProcess.create(Id(2), 2, 2)
        ModelProcess.create(Id(3), 1, 3)

        Process.countByModel(2) must equalTo(1)
        Process.countByModel(1) must equalTo(2)
      }
    }

  }
}