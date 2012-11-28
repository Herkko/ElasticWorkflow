package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm._
import java.util.Date

class ElementTypeSpec extends Specification {

  "Evolutions" should {
    "be applied without errors" in {
      evolutionFor("default")
      running(FakeApplication()) {
        DB.withConnection {
          implicit connection =>
            SQL("select count(1) from elementTypes").execute()
        }
      }
      success
    }
  }

  "Element Types " should {
    "exist" in {
      "Swimlane element type exists" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          var rows: anorm.SqlRow = null
          DB.withConnection { implicit connection =>
            rows = SQL("""select count(*) as c from elementTypes where name = 'Swimlane'""").apply().head
          }
          rows[Long]("c") must be equalTo 1
        }
      }
      
      "Start element type exists" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          var rows: anorm.SqlRow = null
          DB.withConnection { implicit connection =>
            rows = SQL("""select count(*) as c from elementTypes where name = 'Start'""").apply().head
          }
          rows[Long]("c") must be equalTo 1
        }
      }
      
      "End element type exists" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          var rows: anorm.SqlRow = null
          DB.withConnection { implicit connection =>
            rows = SQL("""select count(*) as c from elementTypes where name = 'End'""").apply().head
          }
          rows[Long]("c") must be equalTo 1
        }
      }
      
      "Activity element type exists" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          var rows: anorm.SqlRow = null
          DB.withConnection { implicit connection =>
            rows = SQL("""select count(*) as c from elementTypes where name = 'Activity'""").apply().head
          }
          rows[Long]("c") must be equalTo 1
        }
      }
      
      "Gateway element type exists" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          var rows: anorm.SqlRow = null
          DB.withConnection { implicit connection =>
            rows = SQL("""select count(*) as c from elementTypes where name = 'Gateway'""").apply().head
          }
          rows[Long]("c") must be equalTo 1
        }
      }
    }
  }
}
