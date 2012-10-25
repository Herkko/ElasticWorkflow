package service

import models.Model
import anorm._
import java.util.Date

class ModelService {

  def create(): Int = Model.create(NotAssigned, "Model", new Date())

  def read(id: Int) = Model.read(id)

  def findAll() = Model.findAll

  def exists(id: Int): Boolean = Model.contains(id)

}
