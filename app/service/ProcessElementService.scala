package service

import models.ProcessElement
import anorm._

class ProcessElementService {

  //add size param here
  def create(modelId: Long, processId: Long, elemType: Long, value: String, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement(NotAssigned, modelProcessId, elemType, value, 0, x, y))
  }

  def read(id: Long): Option[ProcessElement] = ProcessElement.read(id)
    
  def update(id: Long, value: String, size: Int, x: Int, y: Int) = ProcessElement.update(id, value, size, x, y)
   
  def deleteByProcess(id: Long) = ProcessElement.deleteByProcess(id)
  
  def getModelId(id: Long): Long = ProcessElement.getModelId(id)
}