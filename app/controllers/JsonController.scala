package controllers

//import play.libs.Json.toJson
import models._
import play.api._
import play.api.mvc._
//import com.codahale.jerkson.Json._
//import play.api.libs.json
import play.api.libs.json.Json.toJson
import play.api.libs.json._

/**
 * Control all actions related to showing, creating and deleting json objects.
 */
object JsonController extends Controller {

  implicit object JsonFormat extends Format[JsonObject] {
    def reads(json: JsValue) = JsonObject(
      (json \ "type").as[String],
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(jsonObject: JsonObject) = JsObject(Seq(
      "type" -> JsString(jsonObject.`type`),
      "cx" -> JsNumber(jsonObject.cx),
      "cy" -> JsNumber(jsonObject.cy)))
  }

  /**
   * Find all the elements from the database and create json object of each element, using Jerkson. This method can be
   * accessed by path /json.
   */
  def showAll = Action { implicit request =>
    val jsonElements = JsonObject.findAll
    Ok(toJson(jsonElements))
  }

  /**
   * Find all the elements of the model specified by parameter id. This method can be accessed by path /json/:id.
   */
  def showElementByModel(id: Int) = Action { implicit request =>
    val jsonElements = JsonObject.findByModel(id)
    Ok(toJson(jsonElements))
  }
}