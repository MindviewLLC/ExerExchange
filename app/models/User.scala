package models

import play.api.Play.current
import org.bson.types.ObjectId
import com.novus.salat.dao._
import se.radley.plugin.salat._
import models.mongoContext._

case class User(
  id: ObjectId = new ObjectId,
  name: String,
  email: String
)

object User extends ModelCompanion[User, ObjectId] {
  
  val dao = new SalatDAO[User, ObjectId](collection = mongoCollection("users")) {}
  
}