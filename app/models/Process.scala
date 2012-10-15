package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class Process(id: Pk[Int], name: String, dateCreated: Date)

/**
 * Handle process inserting to database, updating and deleting. Each process must belong to exactly one model
 * but one model may contain multiple processes.
 *
 */
object Process {

  val parse = {
    get[Pk[Int]]("id") ~
      get[String]("name") ~
      get[Date]("dateCreated") map {
        case id ~ name ~ dateCreated =>
          Process(id, name, dateCreated)
      }
  }

  /**
   * Form tuples of processes and processElement, so that each process is in same tuple with a set of elements
   * which belong to this process.
   */
  val withElements = {
    parse ~ ProcessElement.parse map {
      case process ~ processElement => (process, processElement)
    }
  }
  
  /**
   * Insert new process to database and return its id auto-generated by Anorm.
   */
  def create(process: Process): Int = {
    DB.withConnection { implicit connection =>
      SQL("""insert into processes values ({id}, {name}, {dateCreated})""").on(
        "id" -> process.id,
        "name" -> process.name,
        "dateCreated" -> process.dateCreated).executeInsert()
    } match {
      case Some(long) => long.intValue()
      case None => throw new Exception("Process couldn't be added to database")
    }
  }

  /**
   * Find a process specified by parameter id. //TODO throws exception if process id not found
   */
  def read(id: Int): Process = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from processes
          where processes.id = {id}
		""").on('id -> id).as(parse *).head
    }
  }

  /**
   * Delete process specified by parameter id. If a process is deleted, its relations and elements also have to be 
   * deleted.
   */
  def delete(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("delete from processes where id = {id}").
        on('id -> id).executeUpdate() == 0
    }
  }
  
  /**
   * Return a list of processes which belong to the model specified by parameter id. Each process is in a tuple with
   * a set of elements it contains.
   */
  def findByModelWithElements(id: Int): List[(Process, Seq[ProcessElement])] = {
    DB.withConnection { implicit connection =>
      SQL(""" 
          select processes.*, processElements.* from processes, processElements
		  join modelProcesses on processes.id = modelProcesses.processId
		  join elementTypes on processElements.elementTypeId = elementTypes.id
		  join models on models.id = modelProcesses.modelId
		  where models.id = {id}
		  and processElements.modelProcessId = modelProcesses.id
		""").on('id -> id).as(withElements *).groupBy(_._1).toList.sortBy(_._1.id.toString().toInt).map {
        case (process, processWithElements) => (process, processWithElements.map(_._2))
      }
    }
  }
  
  /**
   * Count how many processes belong to a model specified by parameter id.
   */
  def countByModel(id: Int): Int = DB.withConnection { implicit connection =>
    SQL("""select count(*) from processes
        join modelProcesses on modelProcesses.processId = processes.id
        join models on models.id = modelProcesses.modelId
        where models.id = {id}
        """).on('id -> id).as(scalar[Long].single) match {
      case long => long.intValue()
    }
  }
}