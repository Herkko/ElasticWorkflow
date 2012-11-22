package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class Process(
  val id: Pk[Long] = NotAssigned,
  val name: String = "Unknown Process",
  val dateCreated: Date = new Date()
) extends Table {

  def create(): Long 			= Process.create(this)
  def read(): Option[Process] 	= Process.read(id)
  def update() 					= Process.update(this)
  def delete() 					= Process.delete(id)
  def list: List[Process] 		= Process.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.map(id => id).getOrElse(0L),
    "name" -> name,
    "dateCreated" -> dateCreated)
}

object Process extends TableCommon[Process] {

  val table = "processes"

  val createQuery = """
    insert into processes(name, dateCreated) values ({name}, {dateCreated})
  """

  val readQuery = """
    select * from processes where id = {id}
  """

  val updateQuery = """
    update processes set name = {name} where id = {id}
  """

  val deleteQuery = """
    delete from processes where id = {id}
  """

  val listQuery = """
    select * from processes
  """

  def parse(as: String = "processes.") = {
    get[Pk[Long]]("id") ~
      get[String]("name") ~
      get[Date]("dateCreated") map {
        case id ~ name ~ dateCreated =>
          Process(id, name, dateCreated)
      }
  }

  def update(id: Int, name: String): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""update processes 
          set name = {name} where id = {id}""").
        on('id -> id, 'name -> name).executeUpdate() == 0
    }
  }
  
  def withElements() = {
    get[Pk[Long]]("processes.id") ~
      get[String]("processes.name") ~
      get[Date]("processes.dateCreated") ~
      ProcessElement.parse() map {
        case id ~ name ~ dateCreated ~ processElement => {
          (Process(id, name, dateCreated), processElement)
        }
      }
  }
   
  def findByModelWithElements(id: Long): List[(Process, Seq[ProcessElement])] = DB.withConnection { 
    implicit connection => {
      val query = """ 
        select processes.*, processElements.* from processes, processElements
	    join modelProcesses on processes.id = modelProcesses.processId
        join models on models.id = modelProcesses.modelId
        join elementTypes on processElements.elementTypeId = elementTypes.id
	    where models.id = {id}
	    and processElements.modelProcessId = modelProcesses.id
      """
         
      SQL(query).on('id -> id).as(withElements *).groupBy(_._1).toList.sortBy(_._1.id.toString.toInt).map {
        case (process, processWithElements) => (process, processWithElements.map(_._2))
      }
    }
  }
 
  def countByModel(id: Long): Long = DB.withConnection { 
    implicit connection => {
      val query = """
        select count(*) from processes
        join modelProcesses on modelProcesses.processId = processes.id
        join models on models.id = modelProcesses.modelId
        where models.id = {id}
        """
      SQL(query).on('id -> id).as(scalar[Long].single)
    }
  }
  
  def getModelId(id: Long): Long = DB.withConnection { 
    implicit connection => {
      val query = """
        select models.* from models
        join modelProcesses on models.id = modelProcesses.modelId
        join processes on modelProcesses.processId = processes.id
        where processes.id = {id}
        """
      
      SQL(query).on('id -> id).apply().toList match {
        case Nil => throw new Exception("This process doesn't belong to any model.")
        case x :: xs => x[Long]("id")
      }
  }
}
}
  
  
  
  
  
  
  
  