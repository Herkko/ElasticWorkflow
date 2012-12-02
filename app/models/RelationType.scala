package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class RelationType(
    val id: Pk[Long], 
    val relationType: String
)
extends Table {

  def create(): Long = RelationType.create(this)
  def read(): Option[RelationType] = RelationType.read(id)
  def update() = RelationType.update(this)
  def delete() = RelationType.delete(id)
  def list: List[RelationType] = RelationType.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.map(id => id).getOrElse(0L),
    "relationType" -> relationType
 )
}

object RelationType extends TableCommon[RelationType] {

  val table = "relationTypes"

  val createQuery = """
    insert into relationTypes(relationType) values ({relationType})
  """

  val readQuery = """
    select * from relationTypes where id = {id}
  """

  //Doesnt work yet
  val updateQuery = """
    update relationTypes set relationType = {relationType}
    where id = {id}
  """

  val deleteQuery = """
    delete from relationTypes where id = {id}
  """

  val listQuery = """
    select * from relationTypes
  """

  def parse(as: String = "relationTypes.") = {
    get[Pk[Long]]("id") ~
      get[String]("relationType") map {
        case id ~ relationType =>
          RelationType(id, relationType)
      }
  }
}