package models

import play.api.Play.current
import play.api.db._
import java.util.Date
import anorm.SQL
import anorm.SqlQuery

case class Model(id: Int, name: String, dateCreated: Date) 

object Model {

	val findAllSql: SqlQuery = SQL("select * from Model")

	def findAll: List[Model] = DB.withConnection { implicit connection =>
		findAllSql().map ( row =>
		  Model(row[Int]("id"), row[String]("name"), row[Date]("dateCreated"))
		).toList
	}

}