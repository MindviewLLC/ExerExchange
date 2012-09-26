package controllers

import play.api.mvc.{AnyContent, Result, Action, Controller}
import models.Topic
import models.Topic.TopicFormat
import play.api.libs.json.{JsObject, JsValue, JsArray, Json}

object TopicController extends Controller {

  def getTopLevel(): Action[AnyContent] = get(None)
  
  def get(path: Option[String]) = Action {
    println(path)
    val topics = Topic.findAllByPath(path)
    println(topics)
    val json = topics.map(Json.toJson(_))
    Ok(JsArray(json.toSeq)).as("application/json")
  }
  
  def createTopLevel(): Action[JsValue] = create(None)
  
  def create(path: Option[String]) = Action(parse.json) { request =>
    val topic = Json.fromJson(request.body).copy(path = path)
    println(topic)
    // todo: fail on disallowed topic names ("api", "assets", null, etc)
    Topic.save(topic)
    Ok(Json.toJson(topic))
  }
  
}
