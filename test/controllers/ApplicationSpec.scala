package controllers
import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import models.Model
import anorm.Id
import java.util.Date

class ApplicationSpec extends Specification {

  "The Application controller" should {
    "respond to the index Action" in {
      val result = controllers.Application.index(FakeRequest())

      status(result) must equalTo(OK)
      contentType(result) must beSome("text/html")
      charset(result) must beSome("utf-8")   
    }
  
    "respond to the listModels Action by listing models" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        Model.insert(new Model(Id(1), "First model", new Date()))
        Model.insert(new Model(Id(2), "Second model", new Date()))
        Model.insert(new Model(Id(3), "Third model", new Date()))
        
        val result = controllers.Models.list(FakeRequest())
        
        status(result) must equalTo(OK)
        contentType(result) must beSome("text/html")
        charset(result) must beSome("utf-8")   
        //contentAsString(result) must contain("")
      }
    }
  }
}