/**Copyright 2012 University of Helsinki, Daria Antonova, Herkko Virolainen, Panu Klemola
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.*/

package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ProcessElement(
  id: 				Pk[Long] = NotAssigned,
  modelProcessId: 	Long = 0L,
  elementTypeId: 	Int = 2,
  value: 			String = "Unknown ProcessElement",
  width: 			Int = 0,
  height: 			Int = 0, 
  x: 				Int = 0,
  y: 				Int = 0
) extends Table {

  def create(): Long = ProcessElement.create(this)
  def read(): Option[ProcessElement] = ProcessElement.read(id)
  def update() = ProcessElement.update(this)
  def delete() = ProcessElement.delete(id)
  def list: List[ProcessElement] = ProcessElement.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.getOrElse(0L),
    "modelProcessId" -> modelProcessId,
    "elementTypeId" -> elementTypeId,
    "value" -> value,
    "width" -> width,
    "height" -> height,
    "x" -> x,
    "y" -> y)
}

object ProcessElement extends TableCommon[ProcessElement] {

  val table = "processElements"

  val createQuery = """
    insert into processElements(modelProcessId, elementTypeId, value, width, height, x, y) 
    values ({modelProcessId}, {elementTypeId}, {value}, {width}, {height}, {x}, {y})
  """

  val readQuery = """
    select * from processElements where id = {id}
  """

  val updateQuery = """
    update processElements set modelProcessId = {modelProcessId}, 
    					 elementTypeId = {elementTypeId},
    					 value = {value},
    					 width = {width},
     					 height = {height},
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
      get[Int]("width") ~
      get[Int]("height") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case id ~ modelProcessId ~ elementTypeId ~ value ~ width ~ height ~ x ~ y =>
          ProcessElement(id, modelProcessId, elementTypeId, value, width, height,  x, y)
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
      get[Int]("width") ~
      get[Int]("height") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case id ~ modelProcessId ~ elementTypeId ~ width ~ height ~ size ~ x ~ y =>
          ProcessElement(id, modelProcessId, elementTypeId, width, height, size, x, y)
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