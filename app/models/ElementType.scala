package models

import play.api.Play.current
import play.api.db.DB
import java.util.Date
import anorm._
import anorm.SqlParser._

case class ElementType(
    val id: 			Pk[Long] = NotAssigned, 
    val name:		 	String = "Empty ElementType name", 
    val elementType: 	Int, 
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
    "elementType" -> elementType,
    "description" -> description,
    "picture" -> picture
 )
}

object ElementType extends TableCommon[ElementType] {

  val table = "elementTypes"

  val createQuery = """
    insert into elementTypes(name, elementType, description, picture) 
    values ({name}, {elementType}, {description}, {picture})
  """

  val readQuery = """
    select * from elementTypes where id = {id}
  """

  //Doesnt work yet
  val updateQuery = """
    update elementTypes set name = {name},
    						elementType = {elementType},
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

    //elementType is useless?
  def parse(as: String = "elementTypes.") = {
    get[Pk[Long]]("id") ~
      get[String]("name") ~
      get[Int]("elementType") ~
      get[String]("description") ~
      get[Int] ("picture") map {
        case id ~ name ~ elementType ~ description ~ picture =>
          ElementType(id, name, elementType, description, picture)
      }
  }
}