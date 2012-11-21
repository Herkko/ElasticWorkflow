package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._

trait Table {
  val id: Pk[Long]

  def create(): Long
  def read(): Option[Table]
  def update(): Int
  def delete(): Int
  def list(): List[Table]

  def isNew(): Boolean = {
    id == NotAssigned
  }
  def toSeq(): Seq[(String, Any)]
}

trait TableCommon[T <: Table] {

  val table: String
  val createCommand: String
  val readCommand: String
  val updateCommand: String
  val deleteCommand: String
  val listCommand: String

  implicit def toParams[T](params: Seq[(String, T)]) = {
    params.map { param => param._1 -> anorm.toParameterValue(param._2) }
  }
  
  def parse(as: String = ""): RowParser[T]

  def create(table: T): Long = {
    DB.withConnection { implicit connection =>
      SQL(createCommand.format(table))
        .on(toParams(table.toSeq): _*).executeInsert() 
    } match {
      case Some(pk) => pk
      case None => throw new Exception(table.toSeq + "could not be created.")
    }
  }

  def read(id: Long): Option[T] = {
    DB.withConnection { implicit connection =>
      SQL(readCommand.format(table))
        .on('id -> id).as(parse(table + '.') *)
    } match {
      case table :: xs => Some(table)
      case Nil => None
    }
  }

  def read(id: Pk[Long]): Option[T] = {
    read(id.toString.toLong)
  }

  def update(table: T): Int = {
    DB.withConnection { implicit connection =>
      SQL(updateCommand.format(table))
        .on(toParams(table.toSeq): _*).executeUpdate()
    }
  }

  def delete(id: Long): Int = {
    DB.withConnection { implicit connection =>
      SQL(deleteCommand.format(table))
        .on('id -> id).executeUpdate()
    }
  }

  def delete(id: Pk[Long]): Int = {
    delete(id.toString.toLong)
  }

  def list(): List[T] = {
    DB.withConnection { implicit connection =>
      SQL(listCommand.format(table)).as(parse(table + '.') *)
    }
  }

}