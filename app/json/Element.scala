package json

trait Element {
  type T;
  def findAll(): List[T]
  def findByModel(id: Int): List[T]
}

/*
  implicit object ElementFormat extends Format[Element] {
    def reads(json: JsValue) = fromjson(json)
    def writes(element: Element) = tojson(element)
  }

  def tojson[T](element: T)(implicit format: Writes[T]): JsValue = {
    format.writes(element)
  }

  def fromjson[T](json: JsValue)(implicit format: Reads[T]): T = {
    format.reads(json)
  }*/



