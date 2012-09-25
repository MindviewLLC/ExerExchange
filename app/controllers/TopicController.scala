package controllers

import play.api.mvc.{Action, Controller}
import models.Topic
import models.Topic.TopicFormat
import play.api.libs.json.{JsArray, Json}

object TopicController extends Controller {

  def all = Action {
    val topics = Topic.findAll()
    val json = topics.map(Json.toJson(_))
    Ok(JsArray(json.toSeq)).as("application/json")
  }
  
  def create = Action(parse.json) { request =>
      val topic = Json.fromJson(request.body)
      Topic.save(topic)
      Ok(Json.toJson(topic))
  }
  
}
