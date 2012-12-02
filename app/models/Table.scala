/**Copyright 2012 University of Helsinki, Daria Antonova, Herkko Virolainen, Panu Klemola
*
*Licensed under the Apache License, Version 2.0 (the "License");
*you may not use this file except in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing, software
*distributed under the License is distributed on an "AS IS" BASIS,
*WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*See the License for the specific language governing permissions and
*limitations under the License.*/

package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._

trait Table {
  val id: Pk[Long]

  def create(): Long
  def read(): Option[Table]
  def update(): Long
  def delete(): Int
  def list(): List[Table]

  def toSeq(): Seq[(String, Any)]
}

trait TableCommon[T <: Table] {

  val table: String
  val createQuery: String
  val readQuery: String
  val updateQuery: String
  val deleteQuery: String
  val listQuery: String// = "select * from %s".format(table)

  implicit def toParams[T](params: Seq[(String, T)]) = {
    params.map { param => param._1 -> anorm.toParameterValue(param._2) }
  }

  def parse(as: String = ""): RowParser[T]

  def create(table: T): Long = {
    val pid: Option[Long] = DB.withConnection { implicit connection =>
      SQL(createQuery).on(toParams(table.toSeq): _*).executeInsert()
    }
    pid match {
      case Some(long) => long
      case None => throw new Exception(table.toSeq + "could not be created.")
    }
  }

  def read(id: Long): Option[T] = {
    DB.withConnection { implicit connection =>
      SQL(readQuery)
        .on('id -> id).as(parse(table + '.') *)
    } match {
      case table :: xs => Some(table)
      case Nil => None
    }
  }

  def read(id: Pk[Long]): Option[T] = {
    read(id.toString.toLong)
  }

  def update(model: T): Long = DB.withConnection { implicit connection => {
    SQL(updateQuery)
      .on(toParams(model.toSeq): _*).executeUpdate()
    model.id.get
  }}

  def delete(id: Long): Int = DB.withConnection { implicit connection =>
    SQL(deleteQuery)
      .on('id -> id).executeUpdate()
  }

  def delete(id: Pk[Long]): Int = delete(id.toString.toLong)

  def list(): List[T] = DB.withConnection { implicit connection =>
    SQL(listQuery)
      .as(parse(table + '.') *)
  }

  def contains(id: Long): Boolean = DB.withConnection { implicit connection =>
    SQL(readQuery)
      .on('id -> id).as(parse(table + '.') *).toList.size == 1
  }

  def existsWithName(value: String): Boolean = DB.withConnection { implicit connection => {    
      val existsQuery = """
        select * from %s where name = {value}
      """

      SQL(existsQuery.format(table))
        .on('value -> value).as(parse(table + '.') *).toList.size >= 1
    }
  }
}