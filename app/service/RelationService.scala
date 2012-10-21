package service

import models.Relation
import anorm._

class RelationService {

  def create(x1: Int, y1: Int, x2: Int, y2: Int, value: String, relId1: Int, relId2: Int) = {
    Relation.create(Relation(NotAssigned, 1, x1, y1, x2, y2, value, relId1))
    Relation.create(Relation(NotAssigned, 1, x1, y1, x2, y2, value, relId2))
  }
}
