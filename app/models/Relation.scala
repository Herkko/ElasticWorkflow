package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlQuery
import anorm.SqlParser._

case class Relation(id: Pk[Int], relationTypeId: Int, startPointId: Int, endPointId: Int, value: String, relationId: Int)

object Relation {

  val parse = {
    get[Pk[Int]]("id") ~
      get[Int]("relationTypeId") ~
      get[Int]("startPointId") ~
      get[Int]("endPointId") ~
      get[String]("value") ~
      get[Int]("relationId") map {
        case id ~ relationTypeId ~ startPointId ~ endPointId ~ value ~ relationId =>
          Relation(id, relationTypeId, startPointId, endPointId, value, relationId)
      }
  }

  val withTypes = {
    parse ~ RelationType.parse map {
      case relation ~ relationType => (relation, relationType)
    }
  }

  def insert(relation: Relation): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into relations values ({id}, {relationTypeId}, {startPointId}, {endPointId}, {value}, {relationId})""").on(
        "id" -> relation.id,
        "relationTypeId" -> relation.relationTypeId,
        "startPointId" -> relation.startPointId,
        "endPointId" -> relation.endPointId,
        "value" -> relation.value,
        "relationId" -> relation.relationId).executeUpdate() == 1
    }
  }

  def findAll: List[Relation] = DB.withConnection { implicit connection =>
    SQL("""select * from relations""").as(parse *)
  }
  
  def findByModel(id: Int): List[Relation] = {
    DB.withConnection { implicit connection =>
      SQL(""" 
          select relations.* from relations
          join processElements on processElements.relationId = relations.relationId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
		  join elementTypes on processElements.elementTypeId = elementTypes.id
		  where models.id = {id}
		""").on('id -> id).as(parse *)
    }
  }
  
  def delete(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("delete from relations where id = {id}").
        on('id -> id).executeUpdate() == 0
    }
  }
}
