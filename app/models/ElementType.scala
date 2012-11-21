package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ElementType(
    val id: 			Pk[Long] = NotAssigned, 
    val name:		 	String = "Empty ElementType name", 
    val description: 	String = "No Description", 
    val picture: 		Int = 0
)
extends Table {

  def create(): Long = ElementType.create(this)
  def read(): Option[ElementType] = ElementType.read(id)
  def update() = ElementType.update(this)
  def delete() = ElementType.delete(id)
  def list: List[ElementType] = ElementType.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.map(id => id).getOrElse(0L),
    "name" -> name,
    "description" -> description,
    "picture" -> picture
 )
}

object ElementType extends TableCommon[ElementType] {

  val table = "elementTypes"

  val createQuery = """
    insert into elementTypes(name, description, picture) 
    values ({name}, {description}, {picture})
  """

  val readQuery = """
    select * from elementTypes where id = {id}
  """

  //Doesnt work yet
  val updateQuery = """
    update elementTypes set name = {name},   						
    						description = {description},
    						picture = {picture}
    where id = {id}
  """

  val deleteQuery = """
    delete from elementTypes where id = {id}
  """

  val listQuery = """
    select * from elementTypes
  """

    //elementType param is useless?
  def parse(as: String = "elementTypes.") = {
    get[Pk[Long]]("id") ~
      get[String]("name") ~
      get[String]("description") ~
      get[Int] ("picture") map {
        case id ~ name ~ description ~ picture =>
          ElementType(id, name, description, picture)
      }
  }
}