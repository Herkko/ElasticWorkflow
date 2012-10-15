package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ElementType(id: Int, name: String, elementType: Int, description: String, picture: Int)

/**
 * ElementType is a type of element. 
 * @param description: This parameter is currently used to draw objects with Raphael. Possible values are
 * "rect" or "circle".
 */
object ElementType {

  /**
   * Insert new element type to database.
   */
  def create(elementType: ElementType): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""insert into elementTypes values ({id}, {name}, {elementType}, {description}, {picture})""").on(
        "id" -> elementType.id,
        "name" -> elementType.name,
        "elementType" -> elementType.elementType,
        "description" -> elementType.description,
        "picture" -> elementType.picture).executeUpdate() == 1
    }
  }
}