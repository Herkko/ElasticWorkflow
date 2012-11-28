package test.models

import play.api.test._
import play.api.test.Helpers._

import org.specs2.mutable._

import anorm.{ NotAssigned, Id }
import java.util.Date

import models.{ Model, Process, ModelProcess, ProcessElement, Relation }

class RelationSpec extends Specification {

  def createModel(): Unit = {
    Model(NotAssigned, "ModelName1", new Date()).create
    Process(NotAssigned, "ProcessName1", new Date()).create
    ModelProcess(NotAssigned, 1L, 1L).create

    ProcessElement(NotAssigned, 1L, 3, "ProcessElement1", 10, 200, 200).create
    ProcessElement(NotAssigned, 1L, 4, "ProcessElement2", 10, 101, 101).create
    ProcessElement(NotAssigned, 1L, 4, "ProcessElement3", 10, 102, 102).create
  }
  
  "Evolutions" should {
    "be applied without errors" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Relation.list().size must be equalTo 0
      }
      success
    }
  }

  "The Relation class" should {
    "be persisted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        createModel();
        Relation(NotAssigned, 1L, 2L, 1L, "First relation").create
        Relation.list.size must be equalTo 1
      }
    }
  }
  
  "Relation model" should {

    "be able to have two relations from same element" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        createModel();
        Relation(NotAssigned, 1L, 2L, 1L, "First relation").create
        Relation(NotAssigned, 1L, 3L, 1L, "Second relation").create
        
        
        
      }
    }
    
    "be able to have two relations to same element" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
       
      }
    }
  }
  
}