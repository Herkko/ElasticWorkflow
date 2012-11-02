package service

import models.Relation
import anorm._

class RelationService {

  def create(start: Int, end: Int, value: String) = Relation.create(NotAssigned, start, end, 1, value)
  
  def read(id: Int) = Relation.read(id)
  
  def update(id: Int, value: String) = Relation.update(id, value)
  
  def delete(id: Int) = Relation.delete(id)
  
  def deleteByProcess(id: Int) = Relation.deleteByProcess(id)
  
  def findByModel(id: Int): List[Relation] = Relation.findByModel(id)
  
  def getModelId(id: Int): Int = Relation.getModelId(id)
 
}
