package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Relation(x1: Int, y1: Int, x2: Int, y2: Int);

object Relation {

  implicit object RelFormat extends Format[Relation] {
    def reads(json: JsValue) = Relation(
      (json \ "x1").as[Int],
      (json \ "y1").as[Int],
      (json \ "x2").as[Int],
      (json \ "y2").as[Int])

    def writes(rel: Relation) = JsObject(Seq(
      "x1" -> JsNumber(rel.x1),
      "y1" -> JsNumber(rel.y1),
      "x2" -> JsNumber(rel.x2),
      "y2" -> JsNumber(rel.y2)))
  }

  val parse = {
    get[Int]("x1") ~
      get[Int]("y1") ~
      get[Int]("x2") ~
      get[Int]("y2") map {
        case x1 ~ y1 ~ x2 ~ y2 =>
          Relation(x1, y1, x2, y2)
      }
  }

  def findByModel(id: Int): List[Relation] = {
    DB.withConnection { implicit connection =>
      SQL(""" 
          select relations.x1, relations.y1, relations.x2, relations.y2 from relations
          join processElements on processElements.relationId = relations.relationId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
		  join elementTypes on processElements.elementTypeId = elementTypes.id
		  where models.id = {id}
		""").on('id -> id).as(parse *)
    }
  }

  def findAll: List[Relation] = DB.withConnection { implicit connection =>
    SQL("""select relations.x1, relations.y1, relations.x2, relations.y2 from relations""").as(parse *)
  }
}

