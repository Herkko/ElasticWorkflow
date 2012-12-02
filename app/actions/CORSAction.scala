/**Copyright 2012 University of Helsinki, Panu Klemola, Herkko Virolainen, Daria Antonova
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.*/

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