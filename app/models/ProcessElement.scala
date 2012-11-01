package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ProcessElement(modelProcessId: Int, elementTypeId: Int, relationId: Pk[Int], value: String, size: Int, x: Int, y: Int)

/**
 * ProcessElement is an element of the model. It contains such information as what model it belong to, what relations it has,
 * its x and y coordinates.
 */
object ProcessElement {

  val parse = {
    get[Int]("modelProcessId") ~
      get[Int]("elementTypeId") ~
      get[Pk[Int]]("relationId") ~
      get[String]("value") ~
      get[Int]("size") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case modelProcessId ~ elementTypeId ~ relationId ~ value ~ size ~ x ~ y =>
          ProcessElement(modelProcessId, elementTypeId, relationId, value, size, x, y)
      }
  }

  /**
   * Insert new processElement to database and return id generated by Anorm.
   */
  def create(modelProcessId: Int, elementTypeId: Int, relationId: Pk[Int], value: String, size: Int, x: Int, y: Int): Int = {
    DB.withConnection { implicit connection =>
      SQL("""insert into processElements values ({modelProcessId}, {elementTypeId}, {relationId}, {value}, {size}, {x}, {y})""").on(
        "modelProcessId" -> modelProcessId,
        "elementTypeId" -> elementTypeId,
        "relationId" -> relationId,
        "value" -> value,
        "size" -> size,
        "x" -> x,
        "y" -> y).executeInsert() match {
          case Some(pk) => pk.intValue()
          case None => throw new Exception("ProcessElement couldn't be added to database")
        }
    }
  }
  
  def read(id: Int): ProcessElement = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from processElements
          where processElements.relationId = {id}
		""").on('id -> id).as(parse *).head
    }
  }

  def update(relationId: Int, value: String, size: Int, x: Int, y: Int): Boolean =  {
    DB.withConnection { implicit connection =>
      SQL("""update processElements 
          set value = {value}, size = {size}, x = {x}, y = {y}
          where relationId = {relationId}""").
        on('relationId -> relationId,
            'value -> value, 'size -> size, 'x -> x, 'y -> y ).executeUpdate() == 0
    }
  }
 
  /**
   * Delete processElement specified by parameter id.
   */
  def delete(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("delete from processElements where id = {id}").
        on('id -> id).executeUpdate() == 0
    }
  }
  
  /**
   * Delete all processElements that belong to a process.
   */
  def deleteByProcess(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""delete from processElements
          where modelProcessId in (select id from modelProcesses where processId in (select id from processes where id = {id}))
      		""").
        on('id -> id).executeUpdate() == 0
    }
  }
  
  /**
   * Return a list of all processElements in database.
   */
  def findAll: List[ProcessElement] = DB.withConnection { implicit connection =>
    SQL("""select * from processElements""").as(parse *)
  }
  
}
