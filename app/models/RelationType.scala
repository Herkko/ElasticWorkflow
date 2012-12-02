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