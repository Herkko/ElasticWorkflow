package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ProcessElement(modelProcessId: Int, elementTypeId: Int, relationId: Int, value: String, size: Int, xCoord: Int, yCoord: Int)

object ProcessElement {

  val parse = {
    get[Int]("modelProcessId") ~
      get[Int]("elementTypeId") ~
      get[Int]("relationId") ~
      get[String]("value") ~
      get[Int]("size") ~
      get[Int]("xCoord") ~
      get[Int]("yCoord") map {
        case modelProcessId ~ elementTypeId ~ relationId ~ value ~ size ~ xCoord ~ yCoord =>
          ProcessElement(modelProcessId, elementTypeId, relationId, value, size, xCoord, yCoord)
      }
  }

  def insert(processElement: ProcessElement): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into processElements values ({modelProcessId}, {elementTypeId}, {relationId}, {value}, {size}, {xCoord}, {yCoord})""").on(
        "modelProcessId" -> processElement.modelProcessId,
        "elementTypeId" -> processElement.elementTypeId,
        "relationId" -> processElement.relationId,
        "value" -> processElement.value,
        "size" -> processElement.size,
        "xCoord" -> processElement.xCoord,
        "yCoord" -> processElement.yCoord).executeUpdate() == 1
    }
  }
}
