package app.models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.SqlParser._
import anorm._

case class StartPoint extends RoundObject(id: Pk[Int], value: String)


object StartPoint {

  def create(model: StartPoint): Int = {
     
    DB.withConnection { implicit connection =>
      SQL(""" insert into StartPoint values ({id}, {name}, {dateCreated})""").on(
        "id" -> StartPoint.id,
        "name" -> StartPoint.name,
        "dateCreated" -> model.dateCreated).executeInsert()
    } match {
      case Some(long) => long.intValue()
      case None => throw new Exception("Model couldn't be added to database")
    }
  }
    
  
  
  
  
}