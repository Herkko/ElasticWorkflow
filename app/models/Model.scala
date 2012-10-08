package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.ResultSetParser
import anorm.RowParser
import anorm.SQL
import anorm.SqlQuery
import anorm.~
import anorm.SqlParser._
import anorm._

case class Model(id: Pk[Int], name: String, dateCreated: Date) 

object Model {

	val findAllSql: SqlQuery = SQL("select * from models")
    val insertSql: SqlQuery = SQL("insert into models values ({id}, {name}, {dateCreated})")
    
	val modelParser: RowParser[Model] = {
		get[Pk[Int]]("id") ~
		get[String]("name") ~
		get[Date]("dateCreated") map {
		  case id ~ name ~ dateCreated =>
		    Model(id, name, dateCreated)
		}
	}
	
	val modelsParser: ResultSetParser[List[Model]] = {
		modelParser *
	}
	
	def findAll: List[Model] = DB.withConnection { implicit connection =>
	  findAllSql.as(modelsParser)
	}

	def insert(model: Model): Boolean = {
		DB.withConnection { implicit connection =>
		    insertSql.on( 
				"id" -> model.id, 
				"name" -> model.name, 
				"dateCreated" -> model.dateCreated
			).executeUpdate() == 1 
		}
	}
}
