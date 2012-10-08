package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlQuery
import anorm.SqlParser._

case class Relation(id: Int, relationTypeId: Int, startPointId: Int, endPointId: Int, value: String, relationId: Int)

object Relation {
  
  val parse = {
    get[Int]("id") ~
      get[Int]("relationTypeId") ~
      get[Int]("startPointId") ~
      get[Int]("endPointId") ~
      get[String]("value") ~
      get[Int]("relationId") map {
        case id ~ relationTypeId ~ startPointId ~ endPointId ~ value ~ relationId =>
          Relation(id, relationTypeId, startPointId, endPointId, value, relationId)
      }
  }
}
