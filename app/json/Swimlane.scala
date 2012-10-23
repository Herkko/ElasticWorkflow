package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Swimlane(cx: Int, cy: Int)

object Swimlane {
  
  implicit object SwimlaneFormat extends Format[Swimlane] {
    def reads(json: JsValue) = Swimlane(
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(element: Swimlane) = JsObject(Seq(
      "cx" -> JsNumber(element.cx),
      "cy" -> JsNumber(element.cy)))
  }
  
  val parse = {
      get[Int]("xCoord") ~
      get[Int]("yCoord") map {
        case cx ~ cy =>
          Swimlane(cx, cy)
      }
  }

  def findAll() : List[Swimlane] = DB.withConnection { implicit connection =>
    SQL("""select processElements.xCoord, processElements.yCoord
          from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          and elementTypes.name = 'Swimlane'
         """).as(parse *)
  }

  def findByModel(id: Int) : List[Swimlane] = DB.withConnection { implicit connection =>
    SQL("""select processElements.xCoord, processElements.yCoord from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
          where models.id = {id}
          and elementTypes.name = 'Swimlane'
         """).on('id -> id).as(parse *)
  }
}