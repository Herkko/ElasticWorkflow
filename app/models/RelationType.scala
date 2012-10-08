package models

import play.api.Play.current
import play.api.db.DB
import anorm.SQL
import anorm.SqlQuery

case class RelationType(id: Int, relationType: String)

object RelationType {

  def insert(relationType: RelationType): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into relationTypes values ({id}, {relationType})""").on(
        "id" -> relationType.id,
        "relationType" -> relationType.relationType).executeUpdate() == 1
    }
  }
}