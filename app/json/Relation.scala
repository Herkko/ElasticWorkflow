package json

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import play.api.libs.json.Json.toJson
import play.api.libs.json._

case class Relation(id: Int, startId: Int, endId: Int, relationTypeId: Int, value: String) //(startId: Int, endId: Int);

object Relation {

  implicit object RelFormat extends Format[Relation] {
    def reads(json: JsValue) = Relation(
      (json \ "id").as[Int],
      (json \ "startId").as[Int],
      (json \ "endId").as[Int],
      (json \ "relationTypeId").as[Int],
      (json \ "value").as[String])

    def writes(relation: Relation) = JsObject(Seq(
      "id" -> JsNumber(relation.id),
      "startId" -> JsNumber(relation.startId),
      "endId" -> JsNumber(relation.endId),
      "relationTypeId" -> JsNumber(relation.relationTypeId),
      "value" -> JsString(relation.value)))
  }

  val parse = {
    get[Int]("id") ~
      get[Int]("startId") ~
      get[Int]("endId") ~
      get[Int]("relationTypeId") ~
      get[String]("value") map {
        case id ~ startId ~ endId ~ relationTypeId ~ value =>
          Relation(id, startId, endId, relationTypeId, value)
      }
  }

  def findByModel(id: Int): List[Relation] = {
    DB.withConnection { implicit connection =>
      SQL(""" 
          select relations.* from relations
          join processElements on processElements.relationId = relations.startId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
		  join elementTypes on processElements.elementTypeId = elementTypes.id
		  where models.id = {id}
		""").on('id -> id).as(parse *)
    }
  }

  def findAll: List[Relation] = DB.withConnection { implicit connection =>
    SQL("""select * from relations""").as(parse *)
  }
}

