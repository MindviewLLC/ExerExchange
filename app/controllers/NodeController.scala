package controllers

import play.api.mvc.{AnyContent, Result, Action, Controller}
import models.Node
import models.Node.NodeFormat
import play.api.libs.json.{JsObject, JsValue, JsArray, Json}

object NodeController extends Controller {

  def getTopLevel(): Action[AnyContent] = get(None)
  
  def get(path: Option[String]) = Action {
    val nodes = Node.findAllByPath(path)
    val json = nodes.map(Json.toJson(_))
    Ok(JsArray(json.toSeq)).as("application/json")
  }
  
  def createTopLevel(): Action[JsValue] = create(None)
  
  def create(path: Option[String]) = Action(parse.json) { request =>
    val node = Json.fromJson[Node](request.body)
    // todo: fail on disallowed node names ("api", "assets", null, etc)
    Node.save(node)
    Ok(Json.toJson(node))
  }
  
}
