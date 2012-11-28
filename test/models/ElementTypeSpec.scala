package test.models

import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable._

import anorm.{ NotAssigned, Id }
import java.util.Date

class ElementTypeSpec extends Specification {

  import models.{ ElementType }

  "Evolutions" should {
    "be applied without errors" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        ElementType.list().size must be equalTo 5
        println(ElementType.list())
      }
      success
    }
  }

  "Element Types " should {
    "exist" in {
      "Swimlane element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Swimlane") must beTrue
        }
      }

      "Start element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Start") must beTrue
        }
      }

      "End element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("End") must beTrue
        }
      }

      "Activity element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Activity") must beTrue
        }
      }

      "Gateway element type" >> {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          ElementType.existsWithName("Gateway") must beTrue
        }
      }
    }
  }
}