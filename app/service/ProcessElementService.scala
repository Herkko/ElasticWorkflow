package service

import models.ProcessElement
import anorm._

class ProcessElementService {

  def createSwimlane(modelProcessId: Int, x: Int, y: Int): Int = {
    ProcessElement.create(ProcessElement(modelProcessId, 1, NotAssigned, "Swimlane", 0, x, y))
  }
  
  def createStart(modelProcessId: Int, x: Int, y: Int): Int = {
    ProcessElement.create(ProcessElement(modelProcessId, 2, NotAssigned, "Start", 0, x, y))
  }
  
  def createEnd(modelProcessId: Int, x: Int, y: Int): Int = {
    ProcessElement.create(ProcessElement(modelProcessId, 3, NotAssigned, "End", 0, x, y))
  }
  
  def createActivity(modelProcessId: Int, x: Int, y: Int): Int = {
    ProcessElement.create(ProcessElement(modelProcessId, 4, NotAssigned, "Activity", 0, x, y))
  }
}