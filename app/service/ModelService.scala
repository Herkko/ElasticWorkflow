package service

import models.Model
import anorm._
import java.util.Date

class ModelService {

  def create(): Int = {
    Model.create(Model(NotAssigned, "Model", new Date()))
  }  
}
