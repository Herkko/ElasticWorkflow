/*Copyright 2012 University of Helsinki, Panu Klemola, Herkko Virolainen, Daria Antonova
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
import play.api.db.DB
import java.util.Date
import anorm.SqlParser._
import anorm._

case class Model(
  val id: Pk[Long] = NotAssigned,
  val name: String = "Unknown Model",
  val dateCreated: Date = new Date()) extends Table {

  def create(): Long = Model.create(this)
  def read(): Option[Model] = Model.read(id)
  def update(): Long = Model.update(this)
  def delete(): Int = Model.delete(id)
  def list: List[Model] = Model.list()

  def toSeq(): Seq[(String, Any)] = Seq(
    "id" -> id.map(id => id).getOrElse(0L),
    "name" -> name,
    "dateCreated" -> dateCreated)
}

/**
 * Model is a collection of processes, elements and relations.
 */
object Model extends TableCommon[Model] {

  val table = "models"

  val createQuery = """
    insert into models(name, dateCreated) values ({name}, {dateCreated})
  """

  val readQuery = """
    select * from models where id = {id}
  """

  val updateQuery = """
    update models set name = {name} where id = {id}
  """

  val deleteQuery = """
    delete from models where id = {id}
  """

  val listQuery = """
    select * from models
  """

  def parse(as: String = "models.") = {
    get[Pk[Long]]("id") ~
      get[String]("name") ~
      get[Date]("dateCreated") map {
        case id ~ name ~ dateCreated =>
          Model(id, name, dateCreated)
      }
  }
}