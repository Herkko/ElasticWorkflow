package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.SqlParser._
import anorm._

case class Model(id: Pk[Int], name: String, dateCreated: Date)

/**
 * Model is a collection of processes, elements and relations.
 */
object Model {

  val parse = {
    get[Pk[Int]]("id") ~
      get[String]("name") ~
      get[Date]("dateCreated") map {
        case id ~ name ~ dateCreated =>
          Model(id, name, dateCreated)
      }
  }

  /**
   * Insert new model to database.
   */
  def create(id: Pk[Int], name: String, dateCreated: Date): Int = {
    DB.withConnection { implicit connection =>
      SQL(""" insert into models values ({id}, {name}, {dateCreated})""").on(
        "id" -> id,
        "name" -> name,
        "dateCreated" -> dateCreated).executeInsert()
    } match {
      case Some(pk) => pk.intValue()
      case None => throw new Exception("Model couldn't be added to database")
    }
  }
  
 /**
   * Find a model with a certain id. //TODO What happens if id doesn't exist?
   */
  def read(id: Int): Model = DB.withConnection { implicit connection =>
    SQL(""" 
        select * from models
	    where models.id = {id}
	   """).on('id -> id).as(parse *).head
  }
  
  def update(name: String) {
    
  } 
  
  def update(id: Int, name: String): Boolean =  {
    DB.withConnection { implicit connection =>
      SQL("""update models 
          set name = {name} where id = {id}""").
        on('id -> id, 'name -> name).executeUpdate() == 0
    }
  }
  
  /**
   * Return list of all the models in database.
   */
  def findAll: List[Model] = DB.withConnection { implicit connection =>
    SQL("""select * from models""").as(parse *) //.sortBy(_.id)
  }

  /**
   * Return true if model with id, specified by parameter id, exists. Return false otherwise.
   */
  def contains(id: Int): Boolean = {
    DB.withConnection { implicit connection =>
      SQL(""" 
          select * from models
		  where models.id = {id}
		 """).on('id -> id).as(parse *).toList.size == 1
    }
  }
 
}