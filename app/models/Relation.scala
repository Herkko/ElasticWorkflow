package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

case class Relation(id: Int, relationTypeId: Int, startPointId: Int, endPointId: Int, value: String, relationId: Int) 

object Relation {
  
}
