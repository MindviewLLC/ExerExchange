package controllers

import play.api.mvc.{AnyContent, Result, Action, Controller}
import models.Topic
import models.Topic.TopicFormat
import play.api.libs.json.{JsValue, JsArray, Json}

object TopicController extends Controller {

  def getTopLevel(): Action[AnyContent] = get("/")
  
  def get(parentId: String) = Action {
    val topics = Topic.findAllByParentId(parentId)
    println(topics)
    val json = topics.map(Json.toJson(_))
    Ok(JsArray(json.toSeq)).as("application/json")
  }
  
  def createTopLevel(): Action[JsValue] = create("")
  
  def create(parentId: String) = Action(parse.json) { request =>
      val topic = Json.fromJson(request.body)
      println(topic)
      // todo: fail on disallowed topic names (api, assets, null, etc)
      Topic.save(topic)
      Ok(Json.toJson(topic))
  }
  
}
