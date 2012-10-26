package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Activity(cx: Int, cy: Int)

object Activity extends Element {

  implicit object ActivityFormat extends Format[Activity] {
    def reads(json: JsValue) = Activity(
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(element: Activity) = JsObject(Seq(
      "cx" -> JsNumber(element.cx),
      "cy" -> JsNumber(element.cy)))
  }

  val parse = {
    get[Int]("xCoord") ~
      get[Int]("yCoord") map {
        case cx ~ cy =>
          Activity(cx, cy)
      }
  }

  def findAll(): List[Activity] = DB.withConnection { implicit connection =>
    SQL("""select processElements.xCoord, processElements.yCoord
          from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          and elementTypes.name = 'Activity'
         """).as(parse *)
  }

  def findByModel(id: Int): List[Activity] = DB.withConnection { implicit connection =>
    SQL("""select processElements.xCoord, processElements.yCoord
          from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
          where models.id = {id}
          and elementTypes.name = 'Activity'
         """).on('id -> id).as(parse *)
  }
}