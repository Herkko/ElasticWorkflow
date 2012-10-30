package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Swimlane(modelProcessId: Int, elementTypeId: Int, relationId: Int, value: String, size: Int, x: Int, y: Int) //(cx: Int, cy: Int)

object Swimlane {

  /*implicit object SwimlaneFormat extends Format[Swimlane] {
    def reads(json: JsValue) = Swimlane(
      (json \ "modelProcessId").as[Int],
      (json \ "elementTypeId").as[Int],
      (json \ "relationId").as[Int],
      (json \ "value").as[String],
      (json \ "size").as[Int],
      (json \ "x").as[Int],
      (json \ "y").as[Int])

    def writes(element: Swimlane) = JsObject(Seq(
      "modelProcessId" -> JsNumber(element.modelProcessId),
      "elemenTypeId" -> JsNumber(element.elementTypeId),
      "relationId" -> JsNumber(element.relationId),
      "value" -> JsString(element.value),
      "size" -> JsNumber(element.size),
      "x" -> JsNumber(element.x),
      "y" -> JsNumber(element.y)))
  }

  val parse = {
    get[Int]("modelProcessId") ~
      get[Int]("elementTypeId") ~
      get[Int]("relationId") ~
      get[String]("value") ~
      get[Int]("size") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case modelProcessId ~ elementTypeId ~ relationId ~ value ~ size ~ x ~ y =>
          Swimlane(modelProcessId, elementTypeId, relationId, value, size, x, y)
      }
  }*/
  
  def findAll(): List[Element] = DB.withConnection { implicit connection =>
    Element.findType("Swimlane")
  }

  def findByModel(id: Int): List[Element] = DB.withConnection { implicit connection =>
    Element.findTypeByModel(id, "Swimlane")
  }
}