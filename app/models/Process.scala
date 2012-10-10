package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class Process(id: Pk[Int], name: String, dateCreated: Date)

object Process {

  val parse = {
    get[Pk[Int]]("id") ~
      get[String]("name") ~
      get[Date]("dateCreated") map {
        case id ~ name ~ dateCreated =>
          Process(id, name, dateCreated)
      }
  }

  val withElements = {
    parse ~ ProcessElement.parse map {
      case process ~ processElement => (process, processElement)
    }
  }

  def findById(id: Int): Process = {
    DB.withConnection { implicit connection =>
      SQL("""
          select * from processes
          where processes.id = {id}
		""").on('id -> id).as(parse *).head
    }
  }

  def findByModel(id: Int): List[(Process, Seq[ProcessElement])] = {
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

  def insert(process: Process): Int = {
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
  
   def delete(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("delete from processes where id = {id}").
        on('id -> id).executeUpdate() == 0
    }
  }
}