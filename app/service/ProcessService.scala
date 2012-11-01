package service

import models.{ Process, ModelProcess }
import anorm._
import java.util.Date

class ProcessService {

  def create(modelId: Int): Int = {
    val processId: Int = Process.create(NotAssigned, "Process", new Date())
    ModelProcess.create(NotAssigned, modelId, processId)
    processId
  }
  
  def update(id: Int, name: String) = Process.update(id, name) 
 
  def delete(id: Int) = {
    ModelProcess.delete(id)
    Process.delete(id)
  }

  def countByModel(id: Int) = Process.countByModel(id)
  
  def findByModelWithElements(id: Int) = Process.findByModelWithElements(id)
}