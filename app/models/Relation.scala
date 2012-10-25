package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlQuery
import anorm.SqlParser._

case class Relation(id: Pk[Int], relationTypeId: Int, x1: Int, y1: Int, x2: Int, y2: Int, value: String, relationId: Int)

object Relation {

  val parse = {
    get[Pk[Int]]("id") ~
      get[Int]("relationTypeId") ~
      get[Int]("x1") ~
      get[Int]("y1") ~
      get[Int]("x2") ~
      get[Int]("y2") ~
      get[String]("value") ~
      get[Int]("relationId") map {
        case id ~ relationTypeId ~ x1 ~ y1 ~ x2 ~ y2 ~ value ~ relationId =>
          Relation(id, relationTypeId,  x1, y1, x2, y2, value, relationId)
      }
  }

  /**
   * Form tuples of relations and relationTypes, so that each relation has a relationType
   * associated with it. 
   */
  val withTypes = {
    parse ~ RelationType.parse map {
      case relation ~ relationType => (relation, relationType)
    }
  }

  /**
   * Insert new relation to database.
   * @return
   */
  def create(id: Pk[Int], relationTypeId: Int, x1: Int, y1: Int, x2: Int, y2: Int, value: String, relationId: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into relations values ({id}, {relationTypeId}, {x1}, {y1}, {x2}, {y2}, {value}, {relationId})""").on(
        "id" -> id,
        "relationTypeId" -> relationTypeId,
        "x1" -> x1,
        "y1" -> y1,
        "x2" -> x2,
        "y2" -> y2,
        "value" -> value,
        "relationId" -> relationId).executeUpdate() == 1
    }
  }

  /**
   * Return a list of all relations in database.
   */
  def findAll: List[Relation] = DB.withConnection { implicit connection =>
    SQL("""select * from relations""").as(parse *)
  }
  
  /**
   * Return a list of all relations that belong to a model specified by id.
   */
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
  
  /**
   * Delete relation specified by parameter id.
   */
  def delete(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("delete from relations where id = {id}").
        on('id -> id).executeUpdate() == 0
    }
  }
  
  /**
   * Delete relation specified by parameter id.
   */
  def deleteByProcess(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""delete from relations
          where relationId in (
          select relationId from processElements
          where modelProcessId in (select id from modelProcesses where processId in (select id from processes where id = {id})))
        """).on('id -> id).executeUpdate() == 0
    }
  }
}
