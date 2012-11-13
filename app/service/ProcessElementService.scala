package service

import models.ProcessElement
import anorm._

class ProcessElementService {

  //add size param here
  def create(modelId: Int, processId: Int, elemType: Int, value: String, x: Int, y: Int): Int = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(modelProcessId, elemType, NotAssigned, value, 0, x, y)
  }
  
  def createSwimlane(modelId: Int, processId: Int, x: Int, y: Int): Int = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(modelProcessId, 1, NotAssigned, "Swimlane", 0, x, y)
  }
  
  def createStart(modelId: Int, processId: Int, x: Int, y: Int): Int = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(modelProcessId, 2, NotAssigned, "Start", 0, x, y)
  }
  
  def createEnd(modelId: Int, processId: Int, x: Int, y: Int): Int = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(modelProcessId, 3, NotAssigned, "End", 0, x, y)
  }

  def createActivity(modelId: Int, processId: Int, x: Int, y: Int): Int = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(modelProcessId, 4, NotAssigned, "Activity", 0, x, y)
  }

  def createGateway(modelId: Int, processId: Int, x: Int, y: Int): Int = {
    val modelProcessId = ProcessElement.getModelProcessId(modelId, processId)
    ProcessElement.create(modelProcessId, 5, NotAssigned, "Gateway", 0, x, y)
  }

  def read(id: Int): Option[ProcessElement] = ProcessElement.read(id)
    
  def update(id: Int, value: String, size: Int, x: Int, y: Int) = ProcessElement.update(id, value, size, x, y)
  
  def deleteByProcess(id: Int) = ProcessElement.deleteByProcess(id)
  
  def getModelId(id: Int) = ProcessElement.getModelId(id)
}