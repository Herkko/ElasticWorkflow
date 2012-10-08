package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.SqlParser._
import anorm._

case class Model(id: Pk[Int], name: String, dateCreated: Date)

object Model {

  val parse = {
    get[Pk[Int]]("id") ~
      get[String]("name") ~
      get[Date]("dateCreated") map {
        case id ~ name ~ dateCreated =>
          Model(id, name, dateCreated)
      }
  }

  def findAll: List[Model] = DB.withConnection { implicit connection =>
    SQL("""select * from models""").as(parse *) //.sortBy(_.id)
  }

  def contains(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL(""" 
          select * from models
		  where models.id = {id}
		 """).on('id -> id).as(parse *).toList.size == 1
    }
  }

  def findById(id: Int): Model = DB.withConnection { implicit connection =>
    SQL(""" 
        select * from models
	    where models.id = {id}
	   """).on('id -> id).as(parse *).head
  }

  def insert(model: Model): Int = {
    DB.withConnection { implicit connection =>
      SQL(""" insert into models values ({id}, {name}, {dateCreated})""").on(
        "id" -> model.id,
        "name" -> model.name,
        "dateCreated" -> model.dateCreated).executeInsert()
    } match {
      case Some(long) => long.intValue()
      case None => throw new Exception("Model couldn't be added to database")
    }
  }
}