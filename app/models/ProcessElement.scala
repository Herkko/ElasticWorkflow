package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ProcessElement(
  id: Pk[Long] = NotAssigned,
  modelProcessId: Long,
  elementTypeId: Int,
  value: String,
  size: Int,
  x: Int,
  y: Int
) extends Table {

  def create(): Long = ProcessElement.create(this)
  def read(): Option[ProcessElement] = ProcessElement.read(id)
  def update() = ProcessElement.update(this)
  def delete() = ProcessElement.delete(id)
  def list: List[ProcessElement] = ProcessElement.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id,
    "modelProcessId" -> modelProcessId,
    "elementTypeId" -> elementTypeId,
    "value" -> value,
    "size" -> size,
    "x" -> x,
    "y" -> y)
}

object ProcessElement extends TableCommon[ProcessElement] {

  val table = "processElements"

  val createQuery = """
    insert into processElements(modelProcessId, elementTypeId, value, size, x, y) 
    values ({modelProcessId}, {elementTypeId}, {value}, {size}, {x}, {y})
  """

  val readQuery = """
    select * from processElements where id = {id}
  """

  val updateQuery = """
    update processElements set modelProcessId = {modelProcessId}, 
    					 elementTypeId = {elementTypeId},
    					 value = {value},
    					 size = {size},
    					 x = {x},
    					 y = {y}
    where id = {id}
  """

  val deleteQuery = """
    delete from processElements where id = {id}
  """

  val listQuery = """
    select * from processElements
  """

  def parse(as: String = "processElements.") = {
    get[Pk[Long]]("id") ~
      get[Long]("modelProcessId") ~
      get[Int]("elementTypeId") ~
      get[String]("value") ~
      get[Int]("size") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case id ~ modelProcessId ~ elementTypeId ~ value ~ size ~ x ~ y =>
          ProcessElement(id, modelProcessId, elementTypeId, value, size, x, y)
      }
  }
  
  def update(id: Long, value: String, size: Int, x: Int, y: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""update processElements 
          set value = {value}, size = {size}, x = {x}, y = {y}
          where id = {id}""").
        on('id -> id,
          'value -> value, 'size -> size, 'x -> x, 'y -> y).executeUpdate() == 0
    }
  }

  def getModelProcessId(modelId: Long, processId: Long): Long = DB.withConnection { 
    implicit connection => {
      val query = """
        select id from modelProcesses
        where modelId = {modelId} and processId = {processId}
        """
      SQL(query).on('modelId -> modelId, 'processId -> processId).as(get[Long]("id") *).head
    }
  }

  def getModelId(id: Long): Long = DB.withConnection {
    implicit connection => {
      val query = """
          select models.* from models, processElements
          join modelProcesses on models.id = modelProcesses.modelId
          where processElements.id = {id}
          and processElements.modelProcessId = modelProcesses.id
        """
      SQL(query).on('id -> id).apply().toList match {
        case Nil => throw new Exception("This element doesn't belong to any model.")
        case x :: xs => x[Long]("id")
      }
    }
  }

  def deleteByProcess(id: Long): Boolean = DB.withConnection { 
    implicit connection => {
      val query = """
        delete from processElements where modelProcessId in 
        (select id from modelProcesses where processId in 
        (select id from processes where id = {id}))
      """
      SQL(query).on('id -> id).executeUpdate() == 0
    }
  }
  
  ///BELOW COPIED FROM OLD ELEMENT CLASS >>>
  
  val parser = {
    get[Pk[Long]]("id") ~
      get[Long]("modelProcessId") ~
      get[Int]("elementTypeId") ~
      get[String]("value") ~
      get[Int]("size") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case id ~ modelProcessId ~ elementTypeId ~ value ~ size ~ x ~ y =>
          ProcessElement(id, modelProcessId, elementTypeId, value, size, x, y)
      }
  }
  
  def findType(elementType: String): List[ProcessElement] = DB.withConnection { implicit connection =>
    SQL("""select processElements.* from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          where elementTypes.name = {elementType}
         """).on('elementType -> elementType).as(parser *)
  }

  def findTypeByModel(id: Long, elementType: String): List[ProcessElement] = DB.withConnection { implicit connection =>
    SQL("""select processElements.* from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
          where models.id = {id}
          and elementTypes.name = {elementType}
         """).on('id -> id, 'elementType -> elementType).as(parser *)
  }

  def findTypeById(id: Long, elementType: String): List[ProcessElement] = DB.withConnection { implicit connection =>
    SQL("""select processElements.* 
  	    from processElements
  	    join elementTypes on elementTypes.id = processElements.elementTypeId
        where processElements.id = {id}
  	    and elementTypes.name = {elementType}
  	    """).on('id -> id, 'elementType -> elementType).as(parser *)
  }
}