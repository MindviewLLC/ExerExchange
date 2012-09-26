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
  id: String,
  name: String,
  @Key("parent-id") parentId: String,
  approved: Boolean = false,
  moderators: Option[Vector[User]] = None
)

object Topic extends ModelCompanion[Topic, ObjectId] {

  val dao = new SalatDAO[Topic, ObjectId](collection = mongoCollection("topics")) {}

  implicit object TopicFormat extends Format[Topic] {
    def reads(json: JsValue): Topic = {
      val name = (json \ "name").as[String]
      val parentId = (json \ "parent-id").as[String]
      Topic(
        id = parentId + "/" + UrlUtils.getUrlFriendlyName(name),
        name = name,
        parentId = parentId
      )
    }
    def writes(topic: Topic): JsValue =  JsObject(Seq(
      "id" -> JsString(topic.id.toString),
      "name" -> JsString(topic.name),
      "parent-id" -> JsString(topic.parentId)
    ))
  }
  
  def findAllByParentId(parentId: String) = dao.find(MongoDBObject("topics.parent-id" -> parentId))
  
}