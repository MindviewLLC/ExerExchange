package models

case class Topic(
  moderators: Option[Seq[User]] = None
) extends Content