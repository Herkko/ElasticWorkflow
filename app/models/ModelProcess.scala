package models

import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class ModelProcess(
  val id: Pk[Long] = NotAssigned,
  val modelId: Long,
  val processId: Long
) extends Table {

  def create(): Long = ModelProcess.create(this)
  def read(): Option[ModelProcess] = ModelProcess.read(id)
  def update() = ModelProcess.update(this)
  def delete() = ModelProcess.delete(id)
  def list: List[ModelProcess] = ModelProcess.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.map(id => id).getOrElse(0L),
    "modelId" -> modelId,
    "processId" -> processId)
}

/**
 * ModelProcess is a collection of processes, elements and relations.
 */
object ModelProcess extends TableCommon[ModelProcess] {

  val table = "modelProcesses"

  val createQuery = """
    insert into modelProcesses(modelId, processId) values ({modelId}, {processId})
  """

  val readQuery = """
    select * from modelProcesses where id = {id}
  """

  //Doesnt work yet
  val updateQuery = """
    update models set name = {name} where id = {id}
  """

  val deleteQuery = """
    delete from modelProcesses where id = {id}
  """

  val listQuery = """
    select * from modelProcesses
  """

  def parse(as: String = "modelProcesses.") = {
    get[Pk[Long]]("id") ~
      get[Long]("modelId") ~
      get[Long]("processId") map {
        case id ~ modelId ~ processId =>
          ModelProcess(id, modelId, processId)
      }
  }

  def deleteByProcess(id: Long): Boolean = {
    DB.withConnection { implicit connection =>
      SQL("""
          delete from modelProcesses
          where processId in (select id from processes where id = {id})""").
        on('id -> id).executeUpdate() == 0
    }
  }
}