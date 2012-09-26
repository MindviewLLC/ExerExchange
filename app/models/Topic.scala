package models

import play.api.Play.current

import org.bson.types.ObjectId
import com.novus.salat.annotations.raw.Key
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao._
import se.radley.plugin.salat._
import models.mongoContext._
import play.api.libs.json._
import scala.Predef._
import play.api.libs.json.JsObject
import utils.UrlUtils

case class Topic(
  name: String,
  urlFriendlyName: String,
  @Key("path") path: Option[String],
  approved: Boolean = false,
  moderators: Option[Vector[User]] = None
)

object Topic extends ModelCompanion[Topic, ObjectId] {

  val dao = new SalatDAO[Topic, ObjectId](collection = mongoCollection("topics")) {}

  implicit object TopicFormat extends Format[Topic] {
    def reads(json: JsValue): Topic = {
      val name = (json \ "name").as[String]
      Topic(
        name = name,
        urlFriendlyName = UrlUtils.getUrlFriendlyName(name),
        path = (json \ "path").asOpt[String]
      )
    }
    def writes(topic: Topic): JsValue =  JsObject(Seq(
      "name" -> JsString(topic.name),
      "url-friendly-name" -> JsString(topic.urlFriendlyName),
      "path" -> topic.path.map(JsString(_)).getOrElse(JsNull)
    ))
  }
  
  def findAllByPath(path: Option[String]) = dao.find(MongoDBObject("path" -> path))
  
}