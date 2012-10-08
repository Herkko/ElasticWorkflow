package models

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import anorm.Id
import java.util.Date
import org.h2.jdbc.JdbcSQLException

class ModelSpec extends Specification {

  "The Model class" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.insert(new Model(Id(1), "name", new Date()))
        Model.findAll must have size 1
      }
    }

    "list multiple models" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.insert(new Model(Id(1), "name1", new Date()))
        Model.insert(new Model(Id(2), "name2", new Date()))
        Model.insert(new Model(Id(3), "name3", new Date()))
        Model.findAll must have size 3
      }
    }

    "fail to insert model if id exists" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.insert(new Model(Id(1), "name1", new Date()))
        Model.insert(new Model(Id(1), "name2", new Date())) must throwA[JdbcSQLException]
      }
    }
    
    
  }

}