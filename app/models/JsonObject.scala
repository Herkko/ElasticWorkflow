package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class JsonObject(`type`: String, cx: Int, cy: Int)

/**
 * This is probably a temporary class, needed to show JSON objects with Raphael library. 
 * Example of JsonObject when converted to Json: {"type":"rect","cx":20,"cy":20} 
 */
object JsonObject {

  val parse = {
    get[String]("description") ~
      get[Int]("xCoord") ~
      get[Int]("yCoord") map {
        case jsonType ~ cx ~ cy =>
          JsonObject(jsonType, cx, cy)
      }
  }

  /**
   * Unites columns from the different tables together to return information needed by Raphael.
   * Currently JsonObject consists of type (circle or rect), x and y coordinates. This method
   * returns information about each process element in database, which makes it unable to draw 
   * one model.
   */
  def findAll(): List[JsonObject] = DB.withConnection { implicit connection =>
    SQL("""select elementTypes.description, processElements.xCoord, processElements.yCoord
          from elementTypes, processElements
          where processElements.elementTypeId = elementTypes.id
         """).as(parse *)
  }
  
  /**
   * Return JsonObjects of all elements which belong to a certain model.
   */
  def findByModel(id: Int): List[JsonObject] = DB.withConnection { implicit connection =>
    SQL("""select elementTypes.description, processElements.xCoord, processElements.yCoord
          from elementTypes, processElements
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
          where processElements.elementTypeId = elementTypes.id
          and models.id = {id}
         """).on('id -> id).as(parse *)
  }
}