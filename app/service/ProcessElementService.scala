package service

import models.ProcessElement
import anorm._

class ProcessElementService {

  def createSwimlane(modelProcessId: Int, x: Int, y: Int): Int =
    ProcessElement.create(modelProcessId, 1, NotAssigned, "Swimlane", 0, x, y)

  def createStart(modelProcessId: Int, x: Int, y: Int): Int =
    ProcessElement.create(modelProcessId, 2, NotAssigned, "Start", 0, x, y)

  def createEnd(modelProcessId: Int, x: Int, y: Int): Int =
    ProcessElement.create(modelProcessId, 3, NotAssigned, "End", 0, x, y)

  def createActivity(modelProcessId: Int, x: Int, y: Int): Int =
    ProcessElement.create(modelProcessId, 4, NotAssigned, "Activity", 0, x, y)
    
  def createGateway(modelProcessId: Int, x: Int, y: Int): Int =
    ProcessElement.create(modelProcessId, 5, NotAssigned, "Gateway", 0, x, y)

  def update(id: Int, value: String, size: Int, x: Int, y: Int) = ProcessElement.update(id, value, size, x, y)
  
  def deleteByProcess(id: Int) = ProcessElement.deleteByProcess(id)
}