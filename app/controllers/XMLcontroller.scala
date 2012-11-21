package controllers
import play.api._
import play.api.mvc._
import scala.xml.Node
import scala.xml.Text
import scala.xml.XML
import scala.xml.Null
import scala.xml.Attribute

import models.{ Process, ProcessElement, Model, Relation }

object XMLcontroller extends Controller {


  /* def tag(name: String, content: Seq[Node]): Node =
    <xml></xml>.copy(label = name, child = content)

  def toXML(label: String, content: String): Node =
    tag(label, Text(content))

  /* def toXML(root: String, map: Map[_, _]): Node = {
    val children = for ((k, v) <- map) yield {
      v match {
        case m: Map[_, _] => toXML(k.toString, m)
        case a => toXML(k.toString, a.toString)
      }
    }
    tag(root, children.toSeq)
  }*/*/

  def modelToXML(id: Int) = Action {
    val model = Model.read(id)
    val processes = Process.findByModelWithElements(id)
    val relations = Relation.findByModel(id)
    
    model match {     
      case Some(model) => Ok(toXML(model, processes, relations)).as("text/xml")
      case None => Ok("Model not found")
    }
  }

  def toXML(model: Model, seq: List[(Process, Seq[ProcessElement])], relations: List[Relation]): Node = {
    <Model id={ model.id.toString } name={ model.name } dateCreated={ model.dateCreated.toString }>
      {
        for (e <- seq) yield {
          toXML(e._1, e._2)
        }
      }
      {
        for(r <- relations) yield {
          toXML(r)
        }
      }
    </Model>
  }

  def toXML(p: Process, seq: Seq[ProcessElement]): Node = {
    <Process id={ p.id.toString } name={ p.name } dateCreated={ p.dateCreated.toString }>
      {
        for (e <- seq) yield {
          toXML(e)
        }
      }
    </Process>
  }

  def toXML(p: ProcessElement): Node = {
    <ProcessElement>
      <modelProcessId>{ p.modelProcessId }</modelProcessId>
      <elementTypeId>{ p.elementTypeId }</elementTypeId>
      <relationId>{ p.id }</relationId>
      <value>{ p.value }</value>
      <size>{ p.size }</size>
      <x>{ p.x }</x>
      <y>{ p.y }</y>
    </ProcessElement>
  }
  
  def toXML(r: Relation): Node = {
    <Relation>
	  <id>{r.id}</id>
	  <startId>{r.startId}</startId>
	  <endId>{r.endId}</endId>
	  <relationTypeId>{r.relationTypeId}</relationTypeId>
	  <value>{r.value}</value>
	</Relation>
  }
}