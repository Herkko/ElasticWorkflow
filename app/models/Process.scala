package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm.SQL
import anorm.SqlQuery

case class Process(id: Int, name: String, dateCreated: Date) 

object Process {

  def insert(process: Process): Boolean = {
		DB.withConnection { implicit connection =>
			SQL("""insert into processes values ({id}, {name}, {dateCreated})""").on( 
				"id" -> process.id, 
				"name" -> process.name, 
				"dateCreated" -> process.dateCreated
			).executeUpdate() == 1 
	}
  }
}