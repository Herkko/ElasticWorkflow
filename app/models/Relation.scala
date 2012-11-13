package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlQuery
import anorm.SqlParser._

case class Relation(id: Pk[Int], startId: Int, endId: Int, relationTypeId: Int, value: String)

object Relation {

  val parse = {
    get[Pk[Int]]("id") ~
      get[Int]("startId") ~
      get[Int]("endId") ~
      get[Int]("relationTypeId") ~
      get[String]("value") map {
        case id ~ startId ~ endId ~ relationTypeId ~ value =>
          Relation(id, startId, endId, relationTypeId, value)
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
  def create(id: Pk[Int], startId: Int, endId: Int, relationTypeId: Int, value: String): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into relations values ({id}, {startId}, {endId}, {relationTypeId}, {value})""").on(
        "id" -> id,
        "startId" -> startId,
        "endId" -> endId,
        "relationTypeId" -> relationTypeId,
        "value" -> value).executeUpdate() == 0
    }
  }
  
  def read(id: Int): Option[Relation] = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from relations
          where relations.id = {id}
		""").on('id -> id).as(parse *) match {
        case Nil => None
        case relation :: xs => Some(relation)
      }
    }
  }


  def update(id: Int, startId: Int, endId: Int, value: String) = {
    DB.withConnection { implicit connection =>
      SQL("""update relations
          set startId = {startId}, endId = {endId}, value = {value}
          where id = {id}""").
        on('id ->id, 'startId -> startId, 'endId -> endId, 'value -> value).executeUpdate() == 0
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
          where startId in (
          select relationId from processElements
          where modelProcessId in (select id from modelProcesses where processId in (select id from processes where id = {id})))
        """).on('id -> id).executeUpdate() == 0
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
          join processElements on processElements.relationId = relations.startId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
		  join elementTypes on processElements.elementTypeId = elementTypes.id
		  where models.id = {id}
		""").on('id -> id).as(parse *)
    }
  }
  
   def getModelId(id: Int): Int = DB.withConnection { implicit connection =>
    SQL("""select models.* from models, processElements, relations
        join modelProcesses on modelProcesses.id = processElements.modelProcessId
        where relations.id = {id}
        and (processElements.relationId = relations.startId or processElements.relationId = relations.endId)
        and modelProcesses.modelId = models.id
        """).on('id -> id).apply().toList match {
      case Nil => throw new Exception("This relation doesn't belong to any model.")
      case x :: xs => x[Int]("id")
    }
  }
}








