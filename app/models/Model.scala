package models

import java.util.Date

case class Model(id: Int, name: String, dateCreated: Date) 

object Model {

	var models = Set(
		Model(1, "Some business", new Date()),
		Model(2, "Another business", new Date())
	)
	
	def findAll() = this.models.toList.sortBy(_.id)
}