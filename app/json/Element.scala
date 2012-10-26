package json

import play.api.libs.json.Json.toJson
import play.api.libs.json._
import play.api.Play.current
import play.api.db.DB
import anorm._
import anorm.SqlParser._

trait Element {
   
   implicit object ElementFormat extends Format[Element] {
    def reads(json: JsValue) = null

    def writes(element: Element) = JsObject(null)
  }
   
  def findAll(elementType: String): SqlQuery = DB.withConnection { implicit connection =>
    SQL("""select processElements.xCoord, processElements.yCoord
          from processElements
          join elementTypes on elementTypes.id = processElements.elementTypeId
          and elementTypes.name = {elementType}
         """)
  }
}