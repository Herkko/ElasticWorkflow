package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlQuery
import anorm.SqlParser._

case class Relation(
  val id: Pk[Long],
  val startId: Long,
  val endId: Long,
  val relationTypeId: Int,
  val value: String
) extends Table {

  def create(): Long = Relation.create(this)
  def read(): Option[Relation] = Relation.read(id)
  def update() = Relation.update(this)
  def delete() = Relation.delete(id)
  def list: List[Relation] = Relation.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.map(id => id).getOrElse(0L),
    "startId" -> startId,
    "endId" -> endId,
    "relationTypeId" -> relationTypeId,
    "value" -> value
  )
}

/**
 * Relation is a collection of processes, elements and relations.
 */
object Relation extends TableCommon[Relation] {

  val table = "relations"

  val createQuery = """
    insert into relations(startId, endId, relationTypeId, value)
    values ({startId}, {endId}, {relationTypeId}, {value})
  """

  val readQuery = """
    select * from relations where id = {id}
  """

  //Doesnt work yet
  val updateQuery = """
    update relations set startId = {startId}, 
    				     endId = {endId},
    					 relationTypeId = {relationTypeId},
    					 value = {value}
    where id = {id}
  """

  val deleteQuery = """
    delete from relations where id = {id}
  """

  val listQuery = """
    select * from relations
  """

  def parse(as: String = "relations.") = {
    get[Pk[Long]]("id") ~
      get[Long]("startId") ~
      get[Long]("endId") ~
      get[Int]("relationTypeId") ~
      get[String]("value") map {
        case id ~ startId ~ endId ~ relationTypeId ~ value =>
          Relation(id, startId, endId, relationTypeId, value)
      }
  }

  val parse = {
    get[Pk[Long]]("id") ~
      get[Long]("startId") ~
      get[Long]("endId") ~
      get[Int]("relationTypeId") ~
      get[String]("value") map {
        case id ~ startId ~ endId ~ relationTypeId ~ value =>
          Relation(id, startId, endId, relationTypeId, value)
      }
  }

  def update(id: Int, startId: Int, endId: Int, value: String) = {
    DB.withConnection { implicit connection =>
      SQL("""update relations
          set startId = {startId}, endId = {endId}, value = {value}
          where id = {id}""").
        on('id -> id, 'startId -> startId, 'endId -> endId, 'value -> value).executeUpdate() == 0
    }
  }
  
  def deleteByProcess(id: Long): Boolean = DB.withConnection { 
    implicit connection => {
      val query = """
        delete from relations where startId in
        (select id from processElements where modelProcessId in 
        (select id from modelProcesses where processId in 
        (select id from processes where id = {id})))
        """      
      SQL(query).on('id -> id).executeUpdate() == 0
    }
  }

  def findByModel(id: Long): List[Relation] = DB.withConnection { 
    implicit connection => {
      val query = """ 
          select relations.* from relations
          join processElements on processElements.id = relations.startId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
		  join elementTypes on processElements.elementTypeId = elementTypes.id
		  where models.id = {id}
		"""
      SQL(query).on('id -> id).as(parse *)
    }
  }

  def getModelId(id: Long): Long = DB.withConnection { 
    implicit connection => {
      val query = """
        select models.* from models, processElements, relations
        join modelProcesses on modelProcesses.id = processElements.modelProcessId
        where relations.id = {id}
        and (processElements.id = relations.startId or processElements.id = relations.endId)
        and modelProcesses.modelId = models.id
        """
      SQL(query).on('id -> id).apply().toList match {
          case Nil => throw new Exception("This relation doesn't belong to any model.")
          case x :: xs => x[Long]("id")
        }
     }
  }
}







