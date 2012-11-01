package app.models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.SqlParser._
import anorm._

case class RoundObject(name: String, coordinateX: Int, coordinateY: Int, radius: Int, dateCreated: Date,)

object RoundObject extends Element {

  
}