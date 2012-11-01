package controllers

import play.api._
import play.api.mvc._
import scala.concurrent._
//import play.api._
//import play.api.mvc._
import play.api.libs._
import play.api.libs.concurrent._
import play.api.libs.iteratee._

import actors._
import actors.ActorUpdate._
import akka.util.Timeout
import akka.pattern.ask
import play.api.libs.concurrent._ //.execution.defaultContext
import service.ProcessElementService

object ActorUpdates extends Controller {

  val processElementService = new ProcessElementService

  def live = Action {
    AsyncResult {
      implicit val timeout = Timeout(500)
      (ActorUpdate.ref ? (Join())).mapTo[Enumerator[String]].map { chunks =>
        Ok.stream(chunks &> Comet(callback = "parent.message"))
      }.asPromise
    }
  }

  def say(message: String) = Action {
    ActorUpdate.ref ! Message(message)
    Ok("Said " + message)
  }

  def update(id: Int, value: String) = Action {
    processElementService.update(id, value, 0, 0, 0)
    ActorUpdate.ref ! Update()
    Ok("Updated " + id + " to " + value)
  }

  def room() = Action { implicit request =>
    Ok(views.html.modelTest.room())
  }

}