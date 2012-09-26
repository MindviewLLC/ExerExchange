package models

import play.api.Play.current

import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao._
import se.radley.plugin.salat._
import models.mongoContext._
import play.api.libs.json._
import scala.Predef._
import play.api.libs.json.JsObject
import utils.UrlUtils
import com.novus.salat.annotations.raw.Key

trait Content

case class Node(
  name: String,
  urlFriendlyName: String,
  @Key("path") path: Option[String],
  approved: Boolean = false,
  comments: Seq[Comment] = Seq[Comment](),
  content: Content
)

object Node extends ModelCompanion[Node, ObjectId] {

  val dao = new SalatDAO[Node, ObjectId](collection = mongoCollection("nodes")) {}

  implicit object NodeFormat extends Format[Node] {
    def reads(json: JsValue): Node = {
      val name = (json \ "name").as[String]
      Node(
        name = name,
        urlFriendlyName = UrlUtils.getUrlFriendlyName(name),
        path = (json \ "path").asOpt[String],
        content = Topic() // todo: based on parsing json determine what goes here
      )
    }
    def writes(node: Node): JsValue =  JsObject(Seq(
      "name" -> JsString(node.name),
      "url-friendly-name" -> JsString(node.urlFriendlyName),
      "path" -> node.path.map(JsString(_)).getOrElse(JsNull)
      // todo: based on what type content is, serialize accordingly
    ))
  }

  def findAllByPath(path: Option[String]) = dao.find(MongoDBObject("path" -> path))

}