package models

import java.util.Date
import play.api.Play.current
import play.api.db.DB
import anorm._

case class ModelProcess(id: Pk[Int], modelId: Int, processId: Int, dateCreated: Date)

object ModelProcess {

  def insert(modelProcess: ModelProcess): Int = {
    DB.withConnection { implicit connection =>
      SQL("""insert into modelProcesses values ({id}, {modelId}, {processId}, {dateCreated})""").on(
        "id" -> modelProcess.id,
        "modelId" -> modelProcess.modelId,
        "processId" -> modelProcess.processId,
        "dateCreated" -> modelProcess.dateCreated).executeInsert()
    } match {
      case Some(long) => long.intValue()
      case None => throw new Exception("Model couldn't be added to database")
    }
  }
}