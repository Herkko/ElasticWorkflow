package service

import models.{Process, ModelProcess}
import anorm._
import java.util.Date

class ProcessService {

  def create(modelId: Int): Int = {
    val processId: Int = Process.create(Process(NotAssigned, "Process", new Date()))
    ModelProcess.create(ModelProcess(NotAssigned, modelId, processId, new Date()))
  }
}