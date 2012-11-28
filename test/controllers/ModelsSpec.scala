package test.controllers

import play.api.test._
import play.api.test.Helpers._
import play.api.Play.current
import play.api.db.DB
import play.api._
import play.api.mvc._

import play.api.libs.json.Json.toJson
import play.api.libs.json._

import anorm.{ NotAssigned }
import java.util.Date

import org.specs2.mutable._

class ModelsSpec extends Specification {

  import models.Model
  import controllers.Models
  import format.ModelFormat._
  
  "The Models controller" >> {
    "User should be able to list models" >> {

      "request /models and receive response in html format and utf8 encoding" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          //val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          val result = Models.list(FakeRequest())
          
          status(result) must equalTo(OK)
          contentType(result) must beSome("text/html")
          charset(result) must beSome("utf-8")
        }
      }

      "request /models when no models exist and see page with only a header" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val result = Models.list(FakeRequest())
          contentAsString(result) must contain("All models:")
          contentAsString(result) must not contain ("Model:")
        }
      }

      "request /models when 3 models exist and see page with a header and a list of 3 models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Model(NotAssigned, "ModelName1", new Date()).create()
          Model(NotAssigned, "ModelName2", new Date()).create()
          Model(NotAssigned, "ModelName3", new Date()).create()

          val Some(result) = routeAndCall(FakeRequest(GET, "/models"))
          contentAsString(result) must contain("All models:")
          contentAsString(result) must contain("Model: 1")
          contentAsString(result) must contain("Model: 2")
          contentAsString(result) must contain("Model: 3")
        }
      }

      "create a new model with browser and then see it listed at /models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val before = Models.create(FakeRequest())
          contentAsString(before) must not contain ("Model: 1")

          val after = Models.list(FakeRequest())
          contentAsString(after) must contain("Model: 1")
        }
      }
    }

    "User should be able to create models" >> {

      "when no models exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Models.create(FakeRequest())
          val result = Models.list(FakeRequest())
          contentAsString(result) must contain("Model: 1")
        }
      }

      "when one other model already exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Models.create(FakeRequest())
          Models.create(FakeRequest())
          
          val result = Models.list(FakeRequest())
          contentAsString(result) must contain("Model: 1")
          contentAsString(result) must contain("Model: 2")
        }
      }

      /*   "have automatically created process in each new model" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(POST, "/models"))
          status(result) should be equalTo (SEE_OTHER)
          redirectLocation(result) must beSome.which(_ == "/models/1/processes/new")
        }
      }*/

      "be able to see model page after creating model" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Models.create(FakeRequest())
          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(result) must contain("Name: Model")
        }
      }
    }

    "User should be able to update model" >> {
      "when model with given id exists" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Models.create(FakeRequest())
          
          val Some(model) = Model.read(1)      
          val model1 = model.copy(name = "I has new name!").update

          val Some(result) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(result) must contain("Name: I has new name!")
        }
      }

     /* "fail when model with given id doesn't exist" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          val Some(result) = routeAndCall(FakeRequest(PUT, "/models/id=1&name=I+has+new+name!"))
          status(result) must equalTo(404)
        }
      }*/
      /*
      "failing to change model doesn't affect other models" in {
        running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
          Models.create(FakeRequest())
          
          val Some(result) = routeAndCall(FakeRequest(PUT, "/models/id=2&name=I+has+new+name!"))
          status(result) must equalTo(404)
          
          val Some(result0) = routeAndCall(FakeRequest(GET, "/models/1"))
          contentAsString(result0) must not contain("I has new name")
        }
      }
    }*/
    }
  }
}