package test.controllers

import play.api.test._
import play.api.test.Helpers._
import play.api.Play.current
import play.api.db.DB

import anorm.{ NotAssigned }
import java.util.Date

import org.specs2.mutable._
import controllers.{ Models, Processes }
import models.Process

class ProcessesSpec extends Specification {
  
  
  "The Processes controller" >> {
    
    "User should be able to add processes to models " >> {

     /* "have one process added to each new model automatically" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(POST, "/models"))
          status(result) should be equalTo (SEE_OTHER)
          redirectLocation(result) must beSome.which(_ == "/models/1/processes/new")
        }
      }*/

      "when model has one process automatically generated" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Models.create(FakeRequest())
          val result = Processes.create(1)
         // routeAndCall(FakeRequest(POST, "/models/1/processes"))
          
          val Some(before) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(before) must contain("Model: 1")
          contentAsString(before) must contain("Process: 1")

          routeAndCall(FakeRequest(POST, "/models/1/processes"))
          val Some(after) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(after) must contain("Model: 1")
          contentAsString(after) must contain("Process: 1")
          contentAsString(after) must contain("Process: 2")
        }
      }
    }
    
    "User should be able to update process" >> {
      "when process with given id exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          routeAndCall(FakeRequest(POST, "/models"))
          val Some(process) = Process.read(1)
          val newProc = process.copy(name = "My new name").update
          
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(result) must contain("Name: My new name")
        }
      }
/*
      "fail when process with given id doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(PUT, "/processes/id=1&name=My+new+name"))
          status(result) must equalTo(404)
        }
      }*/
    }
  }
}
//  status(result) should equal (SEE_OTHER)
  //    redirectLocation(result) should equal (Some(routes.Application.index.url))
