package service

import models.{ Process, ModelProcess }
import anorm._
import java.util.Date

class ProcessService {

  def create(modelId: Int): Int = {
    val processId: Int = Process.create(Process(NotAssigned, "Process", new Date()))
    ModelProcess.create(ModelProcess(NotAssigned, modelId, processId, new Date()))
  }

  def delete(id: Int) = {
    ModelProcess.delete(id)
    Process.delete(id)
  }

  def countByModel(id: Int) = Process.countByModel(id)
  
  def findByModelWithElements(id: Int) = Process.findByModelWithElements(id)
}