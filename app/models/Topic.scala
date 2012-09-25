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

case class Topic(
  id: ObjectId = new ObjectId,
  name: String,
  urlFriendlyName: String,
  approved: Boolean = false,
  @Key("parent_topic_id") parentTopic: Option[ObjectId] = None,
  moderators: Option[Vector[User]] = None
)

object Topic extends ModelCompanion[Topic, ObjectId] {
  
  val dao = new SalatDAO[Topic, ObjectId](collection = mongoCollection("topics")) {}

  implicit object TopicFormat extends Format[Topic] {
    def reads(json: JsValue): Topic = Topic(
        name = (json \ "name").as[String],
        urlFriendlyName = (json \ "name").as[String],
        parentTopic = (json \ "parent-id").asOpt[String].map(new ObjectId(_))
    )
    def writes(topic: Topic): JsValue =  JsObject(Seq(
      "id" -> JsString(topic.id.toString),
      "name" -> JsString(topic.name),
      "parent-id" -> topic.parentTopic.map { parentObjectId =>
        JsString(parentObjectId.toString)
      }.getOrElse(JsNull)
    ))
  }
  
  //def children(topic: Topic) = dao.find(MongoDBObject("topics.parent_topic_id" -> topic.id))
  
}