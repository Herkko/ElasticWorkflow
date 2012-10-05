package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.SQL
import anorm.SqlQuery

case class Model(id: Int, name: String, dateCreated: Date) 

object Model {

	val findAllSql: SqlQuery = SQL("select * from models")

	def findAll: List[Model] = DB.withConnection { implicit connection =>
		findAllSql().map ( row =>
		  Model(row[Int]("id"), row[String]("name"), row[Date]("dateCreated"))
		).toList
	}

	def insert(model: Model): Boolean = {
		DB.withConnection { implicit connection =>
			SQL("""insert into models values ({id}, {name}, {dateCreated})""").on( 
				"id" -> model.id, 
				"name" -> model.name, 
				"dateCreated" -> model.dateCreated
			).executeUpdate() == 1 
		}
	}
}