package json

import play.api.libs.json.Json.toJson
import play.api.libs.json._
import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

case class Element(modelProcessId: Int, elementTypeId: Int, relationId: Int, value: String, size: Int, x: Int, y: Int)

object Element {

  implicit object ElementFormat extends Format[Element] {
    def reads(json: JsValue) = Element(
      (json \ "modelProcessId").as[Int],
      (json \ "elementTypeId").as[Int],
      (json \ "relationId").as[Int],
      (json \ "value").as[String],
      (json \ "size").as[Int],
      (json \ "cx").as[Int],
      (json \ "cy").as[Int])

    def writes(element: Element) = JsObject(Seq(
      "modelProcessId" -> JsNumber(element.modelProcessId),
      "elementTypeId" -> JsNumber(element.elementTypeId),
      "relationId" -> JsNumber(element.relationId),
      "value" -> JsString(element.value),
      "size" -> JsNumber(element.size),
      "cx" -> JsNumber(element.x),
      "cy" -> JsNumber(element.y)))
  }
  
    val parse = {
    get[Int]("modelProcessId") ~
      get[Int]("elementTypeId") ~
      get[Int]("relationId") ~
      get[String]("value") ~
      get[Int]("size") ~
      get[Int]("x") ~
      get[Int]("y") map {
        case modelProcessId ~ elementTypeId ~ relationId ~ value ~ size ~ x ~ y =>
          Element(modelProcessId, elementTypeId, relationId, value, size, x, y)
      }
  }
    
  def findAll(): List[Element] = DB.withConnection { implicit connection =>
    SQL("""select *
          from processElements
         """).as(parse *)
  }
  
  def findType(elementType: String): List[Element] = DB.withConnection { implicit connection =>
    SQL("""select *
          from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          and elementTypes.name = {elementType}
         """).on('elementType -> elementType).as(parse *)
  }

   def findTypeByModel(id: Int, elementType: String): List[Element] = DB.withConnection { implicit connection =>
    SQL("""select * from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          join modelProcesses on modelProcesses.id = processElements.modelProcessId
          join models on models.id = modelProcesses.modelId
          join processes on processes.id = modelProcesses.processId
          where models.id = {id}
          and elementTypes.name = {elementType}
         """).on('id -> id, 'elementType -> elementType).as(parse *)
  }
   
  def findTypeById(id: Int, elementType: String): List[Element] = DB.withConnection { implicit connection => 
  	SQL("""select * 
  	    from processElements
  	    join elementTypes on elementTypes.id = processElements.elementTypeId
        where processElements.relationId = {id}
  	    and elementTypes.name = {elementType}
  	    """).on('id -> id, 'elementType -> elementType).as(parse *)
  }
}