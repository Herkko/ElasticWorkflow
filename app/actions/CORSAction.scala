package app.actions

import play.api.mvc._

object CORSAction {

  type ResultWithHeaders = Result {
    def withHeaders(headers: (String, String)*): Result
  }

  def apply[T](bodyParser: BodyParser[T])(f: Request[T] => ResultWithHeaders): Action[T] = {
    Action(bodyParser) { request =>
      f(request).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }
  
  def apply(f: Request[AnyContent] => ResultWithHeaders): Action[AnyContent] = {
    Action { request =>
      f(request).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def apply(f: => ResultWithHeaders): Action[AnyContent] = {
    this.apply(_ => f)
  }

}