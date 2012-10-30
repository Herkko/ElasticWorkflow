package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Gateway(modelProcessId: Int, elementTypeId: Int, relationId: Int, value: String, size: Int, x: Int, y: Int) //(cx: Int, cy: Int)

object Gateway {
/*
  implicit object GatewayFormat extends Format[Gateway] {
    def reads(json: JsValue) = Gateway(
      (json \ "modelProcessId").as[Int],
      (json \ "elementTypeId").as[Int],
      (json \ "relationId").as[Int],
      (json \ "value").as[String],
      (json \ "size").as[Int],
      (json \ "x").as[Int],
      (json \ "y").as[Int])

    def writes(element: Gateway) = JsObject(Seq(
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
          Gateway(modelProcessId, elementTypeId, relationId, value, size, x, y)
      }
  }*/

  def findAll(): List[Element] = DB.withConnection { implicit connection =>
    Element.findType("Gateway")
  }

  def findByModel(id: Int): List[Element] = DB.withConnection { implicit connection =>
    Element.findTypeByModel(id, "Gateway")
  }
}