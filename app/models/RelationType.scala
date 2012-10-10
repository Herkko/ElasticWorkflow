package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class RelationType(id: Int, relationType: String)

/**
 * RelationType enables using different relation types, such as relation between two elements or
 * relation between two processes.
 */
object RelationType {

  val parse = {
    get[Int]("id") ~
      get[String]("relationType") map {
        case id ~ relationType =>
          RelationType(id, relationType)
      }
  }

  /**
   * Insert new relation type to database.
   */
  def insert(relationType: RelationType): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into relationTypes values ({id}, {relationType})""").on(
        "id" -> relationType.id,
        "relationType" -> relationType.relationType).executeUpdate() == 1
    }
  }
}