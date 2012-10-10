package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class JsonObject(`type`: String, cx: Int, cy: Int)

object JsonObject {

  val parse = {
    get[String]("description") ~
      get[Int]("xCoord") ~
      get[Int]("yCoord") map {
        case jsonType ~ cx ~ cy =>
          JsonObject(jsonType, cx, cy)
      }
  }

  def findAll(): List[JsonObject] = DB.withConnection { implicit connection =>
    SQL("""select elementTypes.description, processElements.xCoord, processElements.yCoord
          from elementTypes, processElements
          where processElements.elementTypeId = elementTypes.id
         """).as(parse *)
  }
}