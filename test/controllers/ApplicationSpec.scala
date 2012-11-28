package test.controllers

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import models.Model
import anorm.Id
import java.util.Date

class ApplicationSpec extends Specification {

  import controllers.Application 
  
  "The Application controller" should {
    "respond to the index Action" in {
      val result = Application.index(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("text/html")
      charset(result) must beSome("utf-8")   
    }
  }
}