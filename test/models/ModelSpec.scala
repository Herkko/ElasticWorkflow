package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._

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

  
}