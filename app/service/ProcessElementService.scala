package service

import models.ProcessElement
import anorm._

class ProcessElementService {

  //add size param here
  def create(modelId: Long, processId: Long, elemType: Long, value: String, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement(NotAssigned, modelProcessId, elemType, value, 0, x, y))
  }
  
  def createSwimlane(modelId: Long, processId: Long, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement(NotAssigned, modelProcessId, 1, "Swimlane", 0, x, y))
  }
  
  def createStart(modelId: Long, processId: Long, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement( NotAssigned, modelProcessId, 2, "Start", 0, x, y))
  }
  
  def createEnd(modelId: Long, processId: Long, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement( NotAssigned, modelProcessId, 3, "End", 0, x, y))
  }

  def createActivity(modelId: Long, processId: Long, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement( NotAssigned, modelProcessId, 4, "Activity", 0, x, y))
  }

  def createGateway(modelId: Long, processId: Long, x: Int, y: Int): Long = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(new ProcessElement( NotAssigned, modelProcessId, 5, "Gateway", 0, x, y))
  }

  def read(id: Long): Option[ProcessElement] = ProcessElement.read(id)
    
  def update(id: Long, value: String, size: Int, x: Int, y: Int) = ProcessElement.update(id, value, size, x, y)
  
  
  def deleteByProcess(id: Long) = ProcessElement.deleteByProcess(id)
  
  def getModelId(id: Long): Long = ProcessElement.getModelId(id)
}